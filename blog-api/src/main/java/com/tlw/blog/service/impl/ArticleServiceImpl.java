package com.tlw.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlw.blog.mapper.ArticleMapper;
import com.tlw.blog.mapper.pojo.Category;
import com.tlw.blog.service.*;
import com.tlw.blog.vo.*;
import com.tlw.blog.mapper.pojo.Article;
import com.tlw.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private ThreadService threadService;

    @Override
    public Result listArticlesPage(PageParams pageParams) {
        /**
         * 分页查询
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();

        List<ArticleVo>  articleVoList= copyList(records, true, true, false, false);

        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticles(int limit) {
        //select id, title from ms_article order by view_counts desc limit 5
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);

        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        //select id, title from ms_article order by create_date desc limit 5
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);

        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false, false, false));
    }

    //文章归档
    @Override
    public Result listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return Result.success(archives);
    }


    @Override
    public Result findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true, true, true);
        /**
         * 查看文章后，新增阅读次数
         * 查看玩文章后本应该返回数据了，这时做了一个更新操作（增删改操作加写锁，查询操作加读锁），使得阅读数更新操作需要等待
         * 可以把阅读数更新操作扔到线程池中去执行，和主线程就相关了
         */
        threadService.updateViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag, boolean isAuthor, boolean  isCategory, boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();

        for (Article record: records) {
            articleVoList.add(copy(record, isTag, isAuthor, isCategory, isBody));
        }

        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean  isCategory, boolean isBody) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口都需要标签和作者信息的
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }

        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        if (isCategory) {
            CategoryVo categoryVo = categoryService.findCategoryById(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }

        if (isBody) {
            ArticleBodyVo articleBodyVo = articleBodyService.findArticleBodyById(article.getBodyId());
            articleVo.setBody(articleBodyVo);
        }

        return articleVo;
    }

}

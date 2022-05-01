package com.tlw.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlw.blog.dao.ArticleMapper;
import com.tlw.blog.dao.pojo.*;
import com.tlw.blog.service.*;
import com.tlw.blog.utils.UserThreadLocal;
import com.tlw.blog.vo.*;
import com.tlw.blog.vo.params.ArticleParam;
import com.tlw.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public Result listArticlesPage(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),false));
    }

//    @Override
//    public Result listArticlesPage(PageParams pageParams) {
//        /**
//         * 分页查询
//         */
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        //加入category判断，加载文章列表时要用到
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//
//        //加入tag判断，加载标签文章列表时要用到
//        if (pageParams.getTagId() != null) {
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            articleTagLambdaQueryWrapper.select(ArticleTag::getArticleId);
//            List<ArticleTag> articleTags = articleTagService.findArticleIdsByTagid(pageParams.getTagId());
//            List<Long> articleIdList = new ArrayList<>();
//            for (ArticleTag articleTag:articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0) {
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//        }
//        //排序
//        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//
//        List<ArticleVo>  articleVoList= copyList(records, true, true, false, false);
//
//        return Result.success(articleVoList);
//    }

    @Override
    public Result hotArticles(int limit) {
        //select id, title from ms_article order by view_counts desc limit 5
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        //queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);

        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false));
    }

    @Override
    public Result newArticles(int limit) {
        //select id, title from ms_article order by create_date desc limit 5
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        //queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);

        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false));
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
        ArticleVo articleVo = copy(article, true);
        /**
         * 查看文章后，新增阅读次数
         * 查看玩文章后本应该返回数据了，这时做了一个更新操作（增删改操作加写锁，查询操作加读锁），使得阅读数更新操作需要等待
         * 可以把阅读数更新操作扔到线程池中去执行，和主线程就相关了
         */
        threadService.updateViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    @Transactional
    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1. 将此接口加入到拦截器中
         * 2. 插入Article
         * 3. 插入tags
         * 4. 插入body
         */

        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        articleMapper.insert(article);

        //tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagService.insert(articleTag);
            }
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyService.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();

        for (Article record: records) {
            articleVoList.add(copy(record, isBody));
        }

        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isBody) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //标签
        Long articleId = article.getId();
        articleVo.setTags(tagService.findTagsByArticleId(articleId));

        //作者
        Long authorId = article.getAuthorId();
        articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());

        System.out.println("====>" + article);
        //类别
        CategoryVo categoryVo = categoryService.findCategoryById(article.getCategoryId());
        articleVo.setCategory(categoryVo);

        //并不是所有的接口文章体信息
        if (isBody) {
            ArticleBodyVo articleBodyVo = articleBodyService.findArticleBodyById(article.getBodyId());
            articleVo.setBody(articleBodyVo);
        }

        return articleVo;
    }

}

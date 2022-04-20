package com.tlw.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlw.blog.mapper.ArticleMapper;
import com.tlw.blog.pojo.Article;
import com.tlw.blog.vo.ArticleVo;
import com.tlw.blog.vo.PageParams;
import com.tlw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

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

        List<ArticleVo>  articleVoList= copyList(records);


        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records) {
        return null;
    }

    private
}

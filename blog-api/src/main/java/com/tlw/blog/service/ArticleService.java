package com.tlw.blog.service;

import com.tlw.blog.vo.params.ArticleParam;
import com.tlw.blog.vo.params.PageParams;
import com.tlw.blog.vo.Result;

public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticlesPage(PageParams pageParams);


    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticles(int limit);


    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);


    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}

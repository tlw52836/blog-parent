package com.tlw.blog.controller;

import com.tlw.blog.common.aop.LogAnnotation;
import com.tlw.blog.service.ArticleService;
import com.tlw.blog.vo.params.ArticleParam;
import com.tlw.blog.vo.params.PageParams;
import com.tlw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    //Result是统一结果返回

    @PostMapping
    @LogAnnotation(module = "文章", operation = "文章列表")
    public Result articles(@RequestBody PageParams pageParams) {

        return articleService.listArticlesPage(pageParams);
    }

    /**
     * 最热文章
     * @return
     */
    @PostMapping("/hot")
    public Result hotArticles() {
        int limit = 5;
        return articleService.hotArticles(limit);
    }


    /**
     * 最新文章
     * @return
     */
    @PostMapping("/new")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }



    /**
     * 文章归档
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }


    /**
     * 文章详情
     */
    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

}


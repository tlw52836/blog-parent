package com.tlw.blog.controller;

import com.tlw.blog.service.ArticleService;
import com.tlw.blog.vo.PageParams;
import com.tlw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    //Result是统一结果返回
    @PostMapping
    public Result articles(@RequestBody PageParams pageParams) {

        return articleService.listArticlesPage(pageParams);
    }

}


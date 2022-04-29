package com.tlw.blog.controller;

import com.tlw.blog.service.CategoryService;
import com.tlw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }

    /**
     * 查询所有分类(包括细节)
     * @return
     */
    @GetMapping("/detail")
    public Result categoriesDetail(){
        return categoryService.findAll();
    }
}

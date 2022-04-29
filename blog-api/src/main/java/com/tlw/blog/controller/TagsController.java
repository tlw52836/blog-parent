package com.tlw.blog.controller;

import com.tlw.blog.service.TagService;
import com.tlw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    /**
     * 查询最热标签
     * @return
     */
    @GetMapping("/hot")
    public Result hot(){
        int limit = 6;
        return tagService.hots(limit);
    }

    /**
     * 查询所有标签
     * @return
     */
    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    /**
     * 查询所有标签（包括细节）
     * @return
     */
    @GetMapping("/detail")
    public Result findAllDetail(){
        return tagService.findAll();
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }

}

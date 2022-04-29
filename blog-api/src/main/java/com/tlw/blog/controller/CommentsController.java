package com.tlw.blog.controller;

import com.tlw.blog.service.CommentsService;
import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    /**
     * 据文章id查询评论列表
     * @param articleId
     * @return
     */
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){

        return commentsService.commentsByArticleId(articleId);
    }


    /**
     * 插入评论
     * @param commentParam
     * @return
     */
    @PostMapping("/create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
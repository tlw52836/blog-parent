package com.tlw.blog.service;

import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.params.CommentParam;

public interface CommentsService {


    /**
     * 根据文章id查询评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 插入评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}

package com.tlw.blog.service;

import com.tlw.blog.vo.Result;

public interface CommentsService {


    /**
     * 根据文章id查询评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);
}

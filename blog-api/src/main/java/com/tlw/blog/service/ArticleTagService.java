package com.tlw.blog.service;

import com.tlw.blog.mapper.pojo.ArticleTag;

public interface ArticleTagService {
    /**
     * 插入文章的标签
     * @param articleTag
     */
    void insert(ArticleTag articleTag);
}

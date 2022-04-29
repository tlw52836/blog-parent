package com.tlw.blog.service;

import com.tlw.blog.dao.pojo.ArticleTag;

import java.util.List;

public interface ArticleTagService {
    /**
     * 插入文章的标签
     * @param articleTag
     */
    void insert(ArticleTag articleTag);

    /**
     * 通过标签id查询（文章-标签）对象
     * @param tagId
     * @return
     */
    List<ArticleTag> findArticleIdsByTagid(Long tagId);
}

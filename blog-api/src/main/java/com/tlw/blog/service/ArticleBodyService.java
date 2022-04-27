package com.tlw.blog.service;

import com.tlw.blog.vo.ArticleBodyVo;

public interface ArticleBodyService {
    /**
     * 根据id查询文章内容
     * @return
     */
    ArticleBodyVo findArticleBodyById(Long bodyId);
}

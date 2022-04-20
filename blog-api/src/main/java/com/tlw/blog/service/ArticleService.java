package com.tlw.blog.service;

import com.tlw.blog.vo.PageParams;
import com.tlw.blog.vo.Result;

public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticlesPage(PageParams pageParams);
}

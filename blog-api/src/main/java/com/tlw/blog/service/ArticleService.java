package com.tlw.blog.service;

import com.tlw.blog.vo.PageParams;
import com.tlw.blog.vo.Result;

public interface ArticleService {

    Result listArticlesPage(PageParams pageParams);
}

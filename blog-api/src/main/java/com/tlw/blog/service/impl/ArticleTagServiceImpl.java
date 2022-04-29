package com.tlw.blog.service.impl;

import com.tlw.blog.dao.ArticleTagMapper;
import com.tlw.blog.dao.pojo.ArticleTag;
import com.tlw.blog.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public void insert(ArticleTag articleTag) {
        articleTagMapper.insert(articleTag);
    }
}

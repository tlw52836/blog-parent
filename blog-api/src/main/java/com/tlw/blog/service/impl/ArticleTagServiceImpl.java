package com.tlw.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tlw.blog.dao.ArticleTagMapper;
import com.tlw.blog.dao.pojo.ArticleTag;
import com.tlw.blog.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public void insert(ArticleTag articleTag) {
        articleTagMapper.insert(articleTag);
    }

    @Override
    public List<ArticleTag> findArticleIdsByTagid(Long tagId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getTagId, tagId);

        return articleTagMapper.selectList(queryWrapper);
    }
}

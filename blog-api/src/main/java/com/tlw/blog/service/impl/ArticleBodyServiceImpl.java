package com.tlw.blog.service.impl;

import com.tlw.blog.mapper.ArticleBodyMapper;
import com.tlw.blog.mapper.pojo.ArticleBody;
import com.tlw.blog.service.ArticleBodyService;
import com.tlw.blog.vo.ArticleBodyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {
    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        return articleBodyVo;
    }

    @Override
    public void insert(ArticleBody articleBody) {
        articleBodyMapper.insert(articleBody);
    }
}

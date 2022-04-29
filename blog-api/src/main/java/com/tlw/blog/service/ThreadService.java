package com.tlw.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tlw.blog.dao.ArticleMapper;
import com.tlw.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {


    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article){


        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(articleUpdate,queryWrapper);
        //update article set view_count = 100 where view_count = 99 and id = 11

        try {
            //睡眠5秒 证明不会影响主线程的使用
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

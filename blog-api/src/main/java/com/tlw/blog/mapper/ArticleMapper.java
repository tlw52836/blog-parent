package com.tlw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlw.blog.mapper.dos.Archives;
import com.tlw.blog.mapper.entity.Article;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {
     List<Archives> listArchives();
}

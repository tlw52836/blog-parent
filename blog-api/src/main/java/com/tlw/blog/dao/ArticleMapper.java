package com.tlw.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlw.blog.vo.Archives;
import com.tlw.blog.dao.pojo.Article;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {
     /**
      * 查询归档
      * @return
      */
     List<Archives> listArchives();
}

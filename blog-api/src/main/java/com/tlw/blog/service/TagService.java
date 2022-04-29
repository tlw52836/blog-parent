package com.tlw.blog.service;

import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    /**
     * 根据文章id查询文章标签列表
     * 由于mybatis-plus默认接口无法进行多表查询，因此需要自己配置xml文件
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签
     * @param limit
     * @return
     */
    Result hots(int limit);

    /**
     * 查询所有标签
     * @return
     */
    Result findAll();
}

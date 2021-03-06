package com.tlw.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlw.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询文章标签列表
     * 由于mybatis-plus默认接口无法进行多表查询，因此需要自己配置xml文件
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);


    /**
     * 查询最热标签 前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根据tagId查询Tag对象
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}

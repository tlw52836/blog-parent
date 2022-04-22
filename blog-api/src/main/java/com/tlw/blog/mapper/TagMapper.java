package com.tlw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlw.blog.pojo.Tag;
import com.tlw.blog.vo.TagVo;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询文章标签列表
     * 由于mybatis-plus默认接口无法进行多表查询，因此需要自己配置xml文件
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);
}

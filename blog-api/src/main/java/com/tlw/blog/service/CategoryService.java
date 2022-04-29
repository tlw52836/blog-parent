package com.tlw.blog.service;

import com.tlw.blog.vo.CategoryVo;
import com.tlw.blog.vo.Result;

public interface CategoryService {
    /**
     * 根据id查询种类信息
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 查询所有分类
     * @return
     */
    Result findAll();

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    Result categoriesDetailById(Long id);
}

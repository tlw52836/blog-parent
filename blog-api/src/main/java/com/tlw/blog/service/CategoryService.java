package com.tlw.blog.service;

import com.tlw.blog.vo.CategoryVo;

public interface CategoryService {
    /**
     * 根据id查询种类信息
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);
}

package com.xiaozy.card.service;

import com.xiaozy.card.dataobject.Category;


import java.util.List;

public interface CategoryService {

    /**
     *查询所有分类
     */

   List<Category> findAll();

    /**
     * 新增一个分类
     */
   Category save(Category category);
}

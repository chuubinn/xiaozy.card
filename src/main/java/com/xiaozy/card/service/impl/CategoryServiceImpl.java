package com.xiaozy.card.service.impl;

import com.xiaozy.card.dataobject.Category;
import com.xiaozy.card.repository.CategoryServiceRepository;
import com.xiaozy.card.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryServiceRepository repository;
    /**
     * 查询并遍历所有分类
     */
    @Override
    public List<Category> findAll(){
        List<Category> categoryList = repository.findAll();
        for(Category category: categoryList){
            System.out.println(category);
        }
        return repository.findAll();
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }
}

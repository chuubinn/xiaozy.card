package com.xiaozy.card.service.impl;

import com.xiaozy.card.dataobject.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findAll() {
        List<Category> categoryList = categoryService.findAll();
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    public void save(){
        Category category = new Category();
        category.setCategoryName("test");
        category.setCategoryIcon("test");
        Category result = categoryService.save(category);
        Assert.assertNotNull(result);
    }
}
package com.xiaozy.card.controller;

import com.xiaozy.card.VO.ResultVO;
import com.xiaozy.card.dataobject.Category;
import com.xiaozy.card.service.CategoryService;
import com.xiaozy.card.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO findAll(){
        List<Category> categoryList = categoryService.findAll();
        return ResultVOUtil.success(categoryList);
    }

}

package com.xiaozy.card.controller;

import com.xiaozy.card.dataobject.UserInfo;
import com.xiaozy.card.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void add() {
        UserInfo userInfo =new UserInfo();
        userInfo.setUserName("dog");
        userInfo.setUserPhone("13321321321");
        userInfo.setUserEmail("dog@qq.com");
        userInfo.setSexy(0);
        userInfo.setCity("深圳");
        userInfo.setUserStatus(0);
        userInfo.setUserType(0);
        userService.save(userInfo);
    }
}
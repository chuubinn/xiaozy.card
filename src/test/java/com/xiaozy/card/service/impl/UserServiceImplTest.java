package com.xiaozy.card.service.impl;

import com.xiaozy.card.dataobject.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    private static final String userName = "dashixiong";

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void findByUserName(){
        UserInfo userInfo = userService.findByUserName(userName);
        Assert.assertEquals(userName,userInfo.getUserName());
    }


    @Test
    public void findBySex() {
        List<UserInfo> userInfoList = userService.findBySex("0");
        Assert.assertNotEquals(0,userInfoList.size());
    }

    @Test
    public void findByUserType() {
        List<UserInfo> userInfoList = userService.findByUserType(0);
        Assert.assertNotEquals(0,userInfoList.size());
    }

    @Test
    public void findByUserStatus() {
        List<UserInfo> userInfoList =userService.findByUserStatus(0);
        Assert.assertNotEquals(0,userInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request =new PageRequest(1,10);
        Page<UserInfo> userInfoPage = userService.findAll(request);
        System.out.println(userInfoPage.getTotalElements());
        Assert.assertNotEquals(0,userInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        UserInfo userInfo =new UserInfo();
//        userInfo.setUserId(6);
        userInfo.setUserName("chubin");
        userInfo.setPassword("123456");
        userInfo.setUserPhone("13712345678");
        userInfo.setUserEmail("chubin@qq.com");
        userInfo.setSex("0");
        userInfo.setCity("深圳");
        userInfo.setUserType(1);
        userInfo.setUserStatus(0);
        UserInfo result = userService.save(userInfo);
        Assert.assertNotNull(result);

    }

}
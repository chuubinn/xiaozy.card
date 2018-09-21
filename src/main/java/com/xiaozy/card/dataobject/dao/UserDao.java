package com.xiaozy.card.dataobject.dao;

import com.xiaozy.card.dataobject.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;


public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public int insert(@Param("userName") String userName, @Param("userPhone") String userPhone, @Param("userEmail") String userEmail,
                      @Param("sexy") Integer sexy, @Param("city") String city, @Param("userStatus") Integer userStutas){
        return userMapper.insert(userName,userPhone,userEmail,sexy,city,userStutas);
    }
}

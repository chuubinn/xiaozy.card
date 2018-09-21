package com.xiaozy.card.dataobject.mapper;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {

    @Insert("insert into user_info(user_name,user_phone,user_email,sexy,city,user_status) values(#{user_name, jdbcType=VARCHAR},#{user_phone, jdbcType=VARCHAR},#{user_email, jdbcType=VARCHAR},#{sexy, jdbcType=INTEGER},#{city, jdbcType=VARCHAR},#{user_status,jdbcType=INTEGER})")
    int insert(@Param("userName") String userName, @Param("userPhone") String userPhone,@Param("userEmail") String userEmail,
               @Param("sexy") Integer sexy, @Param("city") String city,@Param("userStatus") Integer userStutas);
}

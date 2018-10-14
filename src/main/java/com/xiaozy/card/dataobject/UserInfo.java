package com.xiaozy.card.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 创建用户对象
 */
@Data
@Entity
@DynamicUpdate
public class UserInfo {

        //用户id
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer userId;

        //用户姓名
        private String userName;

        //用户openid
        private String openid;

        //用户nickname
        private String nickname;

        //用户密码
        private String password;

        //手机号
        private String userPhone;

        //用户邮箱
        private String userEmail;

        //用户性别
        private String sex;

        //用户类型
        private Integer userType;

        //用户地区
        private String city;

        //用户状态
        private Integer userStatus;

        //access_token
        private String accessToken;

        //获取Access_token时间戳
        private String accessTokenTime;

        //jsapi_ticket
        private String jsapiTicket;

        //添加时间
        private Date createTime;

        //修改时间
        private Date updateTime;

        //有效期限
        private String timeOut;


    }


package com.xiaozy.card.common;

//封装查询条件类
import lombok.Data;

@Data
public class Params {

    private  String userName;

    private  Integer sex;

    private Integer userType;

    private Integer userStatus;


}

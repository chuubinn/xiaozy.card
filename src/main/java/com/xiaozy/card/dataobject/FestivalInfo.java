package com.xiaozy.card.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class FestivalInfo {

    //节日id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer FestivalId;

    //节日名称
    private String FestivalName;

    //节日图片
    private String FestivalIcon;

    //节日时间
    private String FestivalTime;

    //节日描述
    private String FestivalDescription;

    //节日备注
    private String Remark;

    //节日所在月份
    private String Month;

    //添加时间
    private Date createTime;

    //修改时间
    private Date updateTime;









}

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
public class Category {

    //分类id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    //分类名称
    private String categoryName;

    //分类图片
    private String categoryIcon;

    //添加时间
    private Date createTime;

    //修改时间
    private Date updateTime;

}

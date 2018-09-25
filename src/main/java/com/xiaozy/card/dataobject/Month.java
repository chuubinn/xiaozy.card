package com.xiaozy.card.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Month {

    //月份id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MonthId;

    //月份名称
    private String MonthName;

    //月份图片
    private String MontIcon;


}


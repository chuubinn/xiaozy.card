package com.xiaozy.card.dataobject;

import java.util.List;

public class PageInfo<T> {

    //当前页
    private int pageNum;

    //每页的数量
    private int pageSize;

    //当前页的数量
    private int size;

    //总记录数
    private long total;

    //总页数
    private int pages;

    //结果集
    private List<UserInfo> userInfoList;

    //第一页
    private int firstPage;

    //前一页
    private int prePage;

    //是否为第一页
    private boolean isFirstPage = false;

    //是否为最后一页
    private boolean isLastPage = false;

    //是否有前一页
    private boolean hasPreviousPage = false;

    //是否有下一页
    private boolean hasNextPage = false;

    //导航页码数
    private int navigatePages;

    //所有导航页号
    private int[] navigatepageNums;
}

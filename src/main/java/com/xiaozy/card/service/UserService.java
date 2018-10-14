package com.xiaozy.card.service;

import com.xiaozy.card.common.Params;
import com.xiaozy.card.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {

    /**
     * 通过用户名查询用户
     */
    UserInfo findByUserName(String userName);

    /**
    *通过用户性别查询
    */
    List<UserInfo> findBySex(String sexy);

    /**
     *通过用户类型查询
     */
    List<UserInfo> findByUserType(Integer userType);

    /**
     * 通过用户状态查询
     */
    List<UserInfo> findByUserStatus(Integer userStatus);

    /**
     * 通过页码查询
     *
     */
    Page<UserInfo> findAll(Pageable pageable);


    /**
     * 保存方法
     */
    UserInfo save(UserInfo userInfo);


    /**
     * 多条件分页查询
     * @param pageIndex
     * @param pageSize
     * @param params
     * @return
     */
    Page<UserInfo> findAll(Integer pageIndex, Integer pageSize, Params params);

}

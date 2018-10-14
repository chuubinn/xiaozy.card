package com.xiaozy.card.repository;

import com.xiaozy.card.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserInfoRepository extends JpaRepository<UserInfo,String>,JpaSpecificationExecutor<UserInfo>{

        /**
         * 用过用户名查找用户
         */
        UserInfo findByUserName(String userName);

        /**
         * 通过用户性别查询
         */
        List<UserInfo> findBySex(String sexy);

        /**
         * 通过用户类型查询
         */
        List<UserInfo> findByUserType(Integer userType);

        /**
         * 通过用户状态查询
         */
        List<UserInfo> findByUserStatus(Integer userStatus);

        /**
         * 通过页码查询
         */
        Page<UserInfo> findAll(Pageable pageable);

        /**
         * 通过页码和条件查询
         * @param spc
         * @param pageable
         * @return
         */
        Page<UserInfo> findAll(Specification<UserInfo> spc, Pageable pageable);


}
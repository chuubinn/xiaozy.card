package com.xiaozy.card.service.impl;

import com.xiaozy.card.common.Params;
import com.xiaozy.card.dataobject.UserInfo;
import com.xiaozy.card.repository.UserInfoRepository;
import com.xiaozy.card.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository repository;

    //通过用户名来查询用户
    @Override
    public UserInfo findByUserName(String userName){
        return repository.findByUserName(userName);
    }

    //通过性别查询
    @Override
    public List<UserInfo> findBySexy(Integer sexy) {
        return repository.findBySexy(sexy);
    }


    //通过用户类型查询
    @Override
    public List<UserInfo> findByUserType(Integer userType) {
        return repository.findByUserType(userType);
    }


    //通过用户状态查询
    @Override
    public List<UserInfo> findByUserStatus(Integer userStatus) {
        return repository.findByUserStatus(userStatus);
    }

    //通过页码查询
    @Override
    public Page<UserInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }



    //保存方法
    @Override
    public UserInfo save(UserInfo userInfo) {
        return repository.save(userInfo);
    }

    //多条件分页查询

    @Override
    public Page<UserInfo> findAll(Integer pageIndex, Integer pageSize, Params params) {
        Pageable pageable =new PageRequest(pageIndex,pageSize);
        Page<UserInfo> userInfoPage = repository.findAll(new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                //根据userName查询
                if (params.getUserName().equals("")){
                    predicateList.add(cb.like(root.get("userName").as(String.class), "%" + params.getUserName() + "%"));
                }
                //根据sexy查询
                if(params.getSexy().equals(0) || params.getSexy().equals(1)||params.getSexy().equals(null)){
                    predicateList.add(cb.equal(root.get("sexy").as(Integer.class),params.getSexy()));
                }
                //根据用户类型查询
                if (params.getUserType().equals(0)||params.getUserType().equals(1)||params.getUserType().equals(2)||params.getUserType().equals(null)){
                    predicateList.add(cb.equal(root.get("userType").as(Integer.class),params.getUserType()) );
                }
                //根据用户状态查询
                if(params.getUserStatus().equals(0) ||params.getUserStatus().equals(1)||params.getUserStatus().equals(null)){
                    predicateList.add(cb.equal(root.get("userType").as(Integer.class),params.getUserStatus()));
                }
                Predicate[] pre = new Predicate[predicateList.size()];
                criteriaQuery.where(predicateList.toArray(pre));
                return cb.and(predicateList.toArray(pre));
            }
        },pageable);
        return userInfoPage;
    }
}

package com.xiaozy.card.repository;

import com.xiaozy.card.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PayServiceRepository extends JpaRepository<UserInfo,Integer>,
                                              JpaSpecificationExecutor<UserInfo> {
    public UserInfo findByOpenid(String openid);

}

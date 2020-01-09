package com.izy.user.service.impl;

import com.izy.user.dataObject.UserInfo;
import com.izy.user.repository.UserInfoRepostory;
import com.izy.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zouyu
 * @description
 * @date 2019/12/30
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    UserInfoRepostory userInfoRepostory;
    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepostory.findByOpenid(openid);
    }
}

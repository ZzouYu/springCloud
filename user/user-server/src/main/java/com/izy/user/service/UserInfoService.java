package com.izy.user.service;

import com.izy.user.dataObject.UserInfo;

public interface UserInfoService {
    UserInfo findByOpenid(String openid);
}

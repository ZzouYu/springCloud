package com.izy.user.repository;

import com.izy.user.dataObject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zouyu
 * @description
 * @date 2019/12/30
 */
public interface UserInfoRepostory extends JpaRepository<UserInfo, String> {
      UserInfo findByOpenid(String openid);
}

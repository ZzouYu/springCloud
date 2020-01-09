package com.izy.user.controller;

import com.izy.user.constant.CookieConstant;
import com.izy.user.constant.RedisConstant;
import com.izy.user.dataObject.UserInfo;
import com.izy.user.enums.ResultEnum;
import com.izy.user.enums.RolesEnum;
import com.izy.user.service.UserInfoService;
import com.izy.user.util.CookieUtil;
import com.izy.user.util.ResultUtil;
import com.izy.user.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zouyu
 * @description
 * @date 2019/12/31
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 买家端
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("buyer")
    public ResultVo buyerLogin(@RequestParam("openid") String openid, HttpServletResponse response){
        //根据openid查询数据库是否有匹配的记录
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if(userInfo == null){
            return ResultUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //判断角色
        if(!RolesEnum.BUYER.getCode().equals(userInfo.getRole())){
            return ResultUtil.error(ResultEnum.ROLE_ERROR);
        }
        //在cookie设置openid
        CookieUtil.setCookie(response, CookieConstant.OPENID,userInfo.getOpenid(),CookieConstant.expire);
        return ResultUtil.success();
    }
    /**
     * 卖家端
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("seller")
    public ResultVo sellerLogin(@RequestParam("openid") String openid, HttpServletRequest request, HttpServletResponse response){
        //判断是否已经登录
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue()));
        if(cookie !=null && StringUtils.isNotEmpty(tokenValue)){
            return ResultUtil.success();
        }
        //根据openid查询数据库是否有匹配的记录
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if(userInfo == null){
            return ResultUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //判断角色
        if(!RolesEnum.SELLER.getCode().equals(userInfo.getRole())){
            return ResultUtil.error(ResultEnum.ROLE_ERROR);
        }
        //redis设置key为uuid,value为openid
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
          stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, token)
                  ,openid,expire, TimeUnit.SECONDS);
        //在cookie设置openid
        CookieUtil.setCookie(response, CookieConstant.TOKEN,token,CookieConstant.expire);
        return ResultUtil.success();
    }
}

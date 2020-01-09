package apigateway.demo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zouyu
 * @description
 * @date 2019/12/31
 */
public class CookieUtil {
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxage
     */
    public static void setCookie(HttpServletResponse response,String name,String value,int maxage){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxage);
        response.addCookie(cookie);
    }
    /**
     * 取cookie
     * @param request
     * @param name

     */
    public static Cookie getCookie(HttpServletRequest request,
                             String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
             for(Cookie cookie : cookies){
                 if(name.equals(cookie.getName())){
                     return cookie;
                 }
             }
        }
        return null;
     }
}

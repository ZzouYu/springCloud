package com.izy.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author zouyu
 * @description
 * @date 2020/1/2
 */
@RestController
@DefaultProperties(defaultFallback="defaultFallback")
public class HystrixController {
    //服务降级，自己抛出异常也会降级
    //@HystrixCommand(fallbackMethod="fallback")
    //超时配置
   /* @HystrixCommand(commandProperties={
          @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
    })*/
//    @HystrixCommand(commandProperties = {
//			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  				//设置熔断
//			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),	//请求数达到后才计算
//			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗
//			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),	//错误率
//	})
    @HystrixCommand
    @GetMapping("getProductInfoList")
    public String getProductInfoList(@RequestParam("number") Integer number){
        if(number%2 == 0){
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        String s = restTemplate.postForObject("http://localhost:7111/product/listForOrder", Arrays.asList("157875196366160022"), String.class);
        return s;
    }
    private String fallback() {
        return "太拥挤了, 请稍后再试~~";
    }
    private String defaultFallback() {
        return "默认提示：太拥挤了, 请稍后再试~~";
    }
}

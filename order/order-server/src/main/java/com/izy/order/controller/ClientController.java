package com.izy.order.controller;

import com.izy.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RefreshScope
public class ClientController {
   /* @Autowired
    private LoadBalancerClient loadBalancerClient;*/
   /* @Autowired
    private RestTemplate restTemplate;*/
   @Autowired
   private ProductClient productClient;
    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        //第一种方式 (直接用restTemplate，url得写死，不好扩展，不支持多应用)
        //RestTemplate restTemplate = new RestTemplate();
        //String response = restTemplate.getForObject("http://127.0.0.1:7111/msg", String.class);

        //第二种方式（利用loadBalancerClient获取应用名称，再使用restTemplate访问）
        /*ServiceInstance product = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s",product.getHost(),product.getPort())+"/msg";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);*/

       //第三种方式(利用 @LoadBalanced注解)
       // String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        
        //第四种方式，通过feign
        String response = productClient.productMsg();
        log.info("Response={}",response);
        return response;
    }

//    测试配置文件是否可以访问
    @Value("${env}")
    private String env;
    @GetMapping(value="env")
    public String getEnv(){
        return env;
    }
}

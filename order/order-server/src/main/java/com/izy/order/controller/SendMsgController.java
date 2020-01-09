package com.izy.order.controller;

import com.izy.order.dto.OrderDTO;
import com.izy.order.message.StreamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SendMsgController {
    @Autowired
    private StreamClient streamClient;
/*
    @GetMapping("sendMsg")
    public void sendMsg(){
        streamClient.output().send(MessageBuilder.withPayload("今天太阳很大").build());
    }*/
        @GetMapping("sendMsg")
         public void sendMsg(){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId("23ewqe");
            log.info("发送消息成功");
            streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
        }
}

package com.izy.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {
   /* @StreamListener(StreamClient.INPUT)
    public void receiverMsg(Object message){
        log.info("StreamReceiver:{}",message);
    }*/
   /*@StreamListener(StreamClient.OUTPUT)
   public void receiverMsg(Object message){
       log.info("StreamReceiver:{}",message);
   }*/
}

package com.izy.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {
    String INPUT = "myInputMessage";
    String OUTPUT = "myOutputMessage";
//    接收
    @Input(StreamClient.INPUT)
    SubscribableChannel input();
//    发送
    @Output(StreamClient.OUTPUT)
    MessageChannel output();
}

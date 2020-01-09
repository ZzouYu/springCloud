package com.izy.order.message;

import com.izy.order.OrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqReceiverTest extends OrderApplicationTests {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Test
    public void sendMessage() {
        amqpTemplate.convertAndSend("myQueue","今天是个好天气");
    }
}

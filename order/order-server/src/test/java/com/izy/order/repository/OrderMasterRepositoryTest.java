package com.izy.order.repository;

import com.izy.order.OrderApplicationTests;
import com.izy.order.dataObject.OrderMaster;
import com.izy.order.enums.OrderStatusEnum;
import com.izy.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {
    @Autowired
    OrderMasterRepository orderMasterRepository;
   @Test
    public void testSave(){
       OrderMaster orderMaster = new OrderMaster();
       orderMaster.setOrderId("1234567");
       orderMaster.setBuyerAddress("北京");
       orderMaster.setBuyerName("zy");
       orderMaster.setBuyerOpenid("110110");
       orderMaster.setBuyerPhone("12354885");
       orderMaster.setOrderAmount(new BigDecimal(2.5));
       orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
       orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
       OrderMaster result = orderMasterRepository.save(orderMaster);
       Assert.assertTrue(result!=null);
   }

}

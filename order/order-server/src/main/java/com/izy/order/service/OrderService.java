package com.izy.order.service;

import com.izy.order.dto.OrderDTO;

public interface OrderService {
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 创建订单
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}

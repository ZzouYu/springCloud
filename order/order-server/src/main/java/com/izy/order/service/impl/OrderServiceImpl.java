package com.izy.order.service.impl;

import com.izy.order.dataObject.OrderDetail;
import com.izy.order.dataObject.OrderMaster;
import com.izy.order.dto.OrderDTO;
import com.izy.order.enums.OrderStatusEnum;
import com.izy.order.enums.PayStatusEnum;
import com.izy.order.enums.ResultEnum;
import com.izy.order.exception.OrderException;
import com.izy.order.repository.OrderDetailRepository;
import com.izy.order.repository.OrderMasterRepository;
import com.izy.order.service.OrderService;
import com.izy.order.utils.KeyUtil;
import com.izy.product.client.ProductClient;
import com.izy.product.common.DecreaseStockInput;
import com.izy.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private ProductClient productClient;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        //1.查询商品信息(调用商品服务)
        List<OrderDetail> orderDetailList=  orderDTO.getOrderDetailList();
        List<String> productIdList = orderDetailList.stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfos = productClient.listForOrder(productIdList);
        //2.计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
         for(OrderDetail  orderDetail: orderDetailList){
             for(ProductInfoOutput productInfo:productInfos){
                 if(orderDetail.getProductId().equals(productInfo.getProductId())){
                     orderAmount = productInfo.getProductPrice()
                             .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                             .add(orderAmount);
                     BeanUtils.copyProperties(productInfo,orderDetail);
                     orderDetail.setOrderId(orderId);
                     orderDetail.setDetailId(KeyUtil.genUniqueKey());
                     //订单详情入库
                     orderDetailRepository.save(orderDetail);
                 }
             }
         }
        //扣库存(调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);
        //4.订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //先查询订单
        Optional<OrderMaster> orderMasterOptional  = orderMasterRepository.findById(orderId);
        if(!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2. 判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3. 修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);
        //4.查询订单详情，因为返回值是OrderDTO
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}

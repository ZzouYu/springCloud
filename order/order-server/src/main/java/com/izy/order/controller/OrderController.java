package com.izy.order.controller;


import com.izy.order.converter.OrderForm2OrderDTOConverter;
import com.izy.order.dto.OrderDTO;
import com.izy.order.enums.ResultEnum;
import com.izy.order.exception.OrderException;
import com.izy.order.form.OrderForm;
import com.izy.order.service.OrderService;
import com.izy.order.utils.ResultUtil;
import com.izy.order.vo.ResultVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
@Api(tags  = "创建订单接口")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 1. 参数检验
     * 2. 查询商品信息(调用商品服务)
     * 3. 计算总价
     * 4. 扣库存(调用商品服务)
     * 5. 订单入库
     */
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                       BindingResult bindingResult){
         if(bindingResult.hasErrors()){
             log.error("【创建订单】参数不正确，orderForm={}",orderForm);
             //抛出一个异常
             throw new OrderException(ResultEnum.PARAMS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
         }
         //将传过来的表单对象转成dto对象
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
         if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
             log.error("【创建订单】购物车信息为空");
             throw new OrderException(ResultEnum.CART_EMPTY);
         }
        OrderDTO result = orderService.create(orderDTO);
        Map<String,String> map =new HashMap<String,String>();
        map.put("orderId",result.getOrderId());
       return ResultUtil.success(map);
    }

    @PostMapping("/finish")
    public ResultVo<OrderDTO> finish(String orderId){
        if(StringUtils.isEmpty(orderId)){
            throw new OrderException(ResultEnum.PARAMS_ERROR);
        }
        OrderDTO orderDTO = orderService.finish(orderId);
        return ResultUtil.success(orderDTO);
    }
}

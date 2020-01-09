package com.izy.order.utils;

import com.izy.order.enums.ResultEnum;
import com.izy.order.vo.ResultVo;

public class ResultUtil {
    public static ResultVo success(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultEnum.PRODUCT_SUCCESS.getCode());
        resultVo.setMessage(ResultEnum.PRODUCT_SUCCESS.getMessage());
        resultVo.setData(data);
        return resultVo;
     }
}

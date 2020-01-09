package com.izy.product.utils;

import com.izy.product.enums.ResultEnum;
import com.izy.product.vo.ResultVo;

public class ResultUtil {
    public static ResultVo success(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultEnum.PRODUCT_SUCCESS.getCode());
        resultVo.setMessage(ResultEnum.PRODUCT_SUCCESS.getMessage());
        resultVo.setData(data);
        return resultVo;
     }
}

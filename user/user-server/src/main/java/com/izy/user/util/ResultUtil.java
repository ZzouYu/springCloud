package com.izy.user.util;

import com.izy.user.enums.ResultEnum;
import com.izy.user.vo.ResultVo;

public class ResultUtil {
    public static ResultVo success(Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultEnum.LOGIN_SUCCESS.getCode());
        resultVo.setMessage(ResultEnum.LOGIN_SUCCESS.getMessage());
        resultVo.setData(data);
        return resultVo;
     }
    public static ResultVo success(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultEnum.LOGIN_SUCCESS.getCode());
        resultVo.setMessage(ResultEnum.LOGIN_SUCCESS.getMessage());
        return resultVo;
    }
    public static ResultVo error(ResultEnum resultEnum){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMessage(resultEnum.getMessage());
        return resultVo;
    }
}

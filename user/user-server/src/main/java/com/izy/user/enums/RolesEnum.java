package com.izy.user.enums;

import lombok.Getter;

@Getter
public enum RolesEnum {
       BUYER(1,"买家"),
       SELLER(2,"卖家"),
    ;
       private Integer code;
       private String message;

    RolesEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

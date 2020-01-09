package com.izy.product.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    UP(0,"正常"),
    DOWN(1,"下架"),
    ;
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

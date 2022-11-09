package com.rosie.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kun.han
 * 返回码常量
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(200, ConstantMsg.SUCCESS),
    FAILURE(0, ConstantMsg.FAILURE),
    ERROR(-1, ConstantMsg.ERROR),
    UNAUTHORIZED(401, ConstantMsg.AUTH_TOKEN_ERROR);

    private final int code;
    private final String desc;

}

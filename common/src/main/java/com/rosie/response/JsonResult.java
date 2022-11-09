package com.rosie.response;

import com.rosie.constant.ResponseCode;
import com.rosie.utils.TraceIdUtil;

import java.time.Instant;


/**
 * @author kun.han
 * 接口统一返回工具类，数据格式为JSON，每个接口默认返回RESULT,DESCRIPTION,SYSTEMTIME,
 * 具体业务信息放在INFO中
 */
public class JsonResult {

    private static <T> BaseResponse<T> getResult(int code, String msg, T t) {
        return new BaseResponse<T>()
                .setDescription(msg)
                .setInfo(t)
                .setResult(code)
                .setSystemTime(Instant.now().toEpochMilli())
                .setTraceId(TraceIdUtil.getTraceId());
    }

    public static <T> BaseResponse<T> successResult(String msg, T t) {
        return getResult(ResponseCode.SUCCESS.getCode(), msg, t);
    }

    public static <T> BaseResponse<T> successResult(String msg) {
        return getResult(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static <T> BaseResponse<T> successResult(T t) {
        return getResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), t);
    }

    public static <T> BaseResponse<T> successResult() {
        return getResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), null);
    }

    public static <T> BaseResponse<T> errorResult(String msg, T t) {
        return getResult(ResponseCode.ERROR.getCode(), msg, t);
    }

    public static <T> BaseResponse<T> errorResult(String msg) {
        return getResult(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static <T> BaseResponse<T> errorResult(T t) {
        return getResult(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc(), t);
    }

    public static <T> BaseResponse<T> errorResult() {
        return getResult(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc(), null);
    }

    public static <T> BaseResponse<T> failureResult(String msg, T t) {
        return getResult(ResponseCode.ERROR.getCode(), msg, t);
    }

    public static <T> BaseResponse<T> failureResult(String msg) {
        return getResult(ResponseCode.FAILURE.getCode(), msg, null);
    }

    public static <T> BaseResponse<T> failureResult(T t) {
        return getResult(ResponseCode.FAILURE.getCode(), ResponseCode.FAILURE.getDesc(), t);
    }

    public static <T> BaseResponse<T> failureResult() {
        return getResult(ResponseCode.FAILURE.getCode(), ResponseCode.FAILURE.getDesc(), null);
    }

    public static <T> BaseResponse<T> authFailureResult(String msg, T t) {
        return getResult(ResponseCode.UNAUTHORIZED.getCode(), msg, t);
    }

    public static <T> BaseResponse<T> authFailureResult(String msg) {
        return getResult(ResponseCode.UNAUTHORIZED.getCode(), msg, null);
    }

    public static <T> BaseResponse<T> authFailureResult(T t) {
        return getResult(ResponseCode.UNAUTHORIZED.getCode(), ResponseCode.UNAUTHORIZED.getDesc(), t);
    }

    public static <T> BaseResponse<T> authFailureResult() {
        return getResult(ResponseCode.UNAUTHORIZED.getCode(), ResponseCode.UNAUTHORIZED.getDesc(), null);
    }
}

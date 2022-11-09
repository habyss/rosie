package com.rosie.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 前后段交互统一返回格式
 */
@Data
public class BaseResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 4709058307894860602L;

    private int result;
    private long systemTime;
    private String traceId;
    private String description;
    private T info;

}

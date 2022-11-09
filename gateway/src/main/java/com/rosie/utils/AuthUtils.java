package com.rosie.utils;

import com.alibaba.fastjson2.JSONObject;
import com.rosie.constant.ConstantMsg;
import com.rosie.response.BaseResponse;
import com.rosie.response.JsonResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kun.han on 2020/6/5 13:05
 */
public class AuthUtils {

    public static Mono<Void> authFailure(ServerWebExchange exchange) {
        // 验证失败状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 返回json格式错误信息
        BaseResponse<?> resultInfo = JsonResult.authFailureResult(ConstantMsg.PARAM_IS_NULL);
        String resultInfoString = JSONObject.toJSONString(resultInfo);
        byte[] bytes = resultInfoString.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    public static Boolean authTokenNormal(String token) {
        String dataToken = "test_token";
        return StringUtils.hasText(token) && dataToken.equals(token);
    }

    /**
     * Decode body map.
     * xxx-www-f
     *
     * @param body the body
     * @return the map
     */
    public static JSONObject decodeBody(String body) {
        Map<String, String> map = Arrays.stream(body.split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
        return JSONObject.parseObject(JSONObject.toJSONString(map));
    }

    /**
     * Gets token by media type.
     * 从body中获取想要的数据
     *
     * @param requestBody the request body
     * @param mediaType   the media type
     * @return the token by media type
     */
    public static String getTokenByMediaType(String requestBody, MediaType mediaType) {
        String token;
        if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
            JSONObject jsonObject = AuthUtils.decodeBody(requestBody);
            token = jsonObject.getString(ConstantMsg.AUTHORIZE_TOKEN);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            token = jsonObject.getString(ConstantMsg.AUTHORIZE_TOKEN);
        }
        return token;
    }
}

package com.rosie.utils;

import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdUtil {

    public final static String TRACE_ID = "traceId";
    public final static String DELIMITER = "-";
    public final static String EMPTY = "";

    /**
     * 重置 traceId.
     * 为保证链路, 请求中有[异步/多线程]执行时,
     * 需先获取当前traceId{@link TraceIdUtil#getTraceId()}, 然后在新线程中设置traceId
     *
     * @param uuid the uuid
     */
    public static void resetTraceId(String uuid) {
        MDC.put(TRACE_ID, uuid);
    }

    /**
     * 重置/初始化 traceId.
     */
    public static void resetTraceId() {
        MDC.put(TRACE_ID, UUID.randomUUID().toString().replaceAll(DELIMITER, EMPTY));
    }

    /**
     * 获取当前 traceId.
     *
     * @return the trace id
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    /**
     * 删除当前 traceId.
     */
    public static void remove() {
        MDC.remove(TRACE_ID);
    }
}
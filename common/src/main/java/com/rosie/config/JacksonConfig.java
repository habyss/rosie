package com.rosie.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.*;
import java.util.Objects;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        // 序列化的通用配置 可默认不做修改
        ObjectMapper objectMapper = new ObjectMapper();
//        // 不显示为null的字段
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // JSON里面包含了实体没有的字段导致反序列化失败。
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        // 过滤对象的null属性.
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        // 忽略transient
//        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
//        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        //LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // LocalDateTime 这里只需要LocalDateTime 如果需要转其他的,相应添加/修改
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        // LocalDate 这里只需要LocalDate 如果需要转其他的,相应添加/修改
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        // LocalTime 这里只需要LocalTime 如果需要转其他的,相应添加/修改
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    /**
     * requestBody 入参 时间戳 -> LocalDateTime
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext context) throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp >= 0) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
            } else {
                return null;
            }
        }
    }
    /**
     * responseBody 返回参数 LocalDateTime -> 时间戳
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.nonNull(localDateTime)) {
                jsonGenerator.writeNumber(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

    /**
     * requestBody 入参 时间戳 -> LocalDate
     */
    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext context) throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp >= 0) {
                return LocalDate.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
            } else {
                return null;
            }
        }
    }

    /**
     * responseBody 返回参数 LocalDate -> 时间戳
     */
    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.nonNull(localDate)) {
                jsonGenerator.writeNumber(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

    /**
     * requestBody 入参 时间戳 -> LocalDate
     */
    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext context) throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp >= 0) {
                return LocalTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
            } else {
                return null;
            }
        }
    }

    /**
     * responseBody 返回参数 LocalDate -> 时间戳
     */
    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (Objects.nonNull(localTime)) {
                jsonGenerator.writeNumber(localTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

}

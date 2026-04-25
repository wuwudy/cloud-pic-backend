package com.f1sh.cloudpicbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@JsonComponent
public class JsonConfig {

    /**
     * 全局配置：Long 类型序列化为 String，避免前端精度丢失
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        SimpleModule module = new SimpleModule();
        // 包装类型 Long
        module.addSerializer(Long.class, ToStringSerializer.instance);
        // 基本类型 long
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);

        objectMapper.registerModule(module);
        return objectMapper;
    }
}

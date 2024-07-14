package com.ssmtest.jsonTool;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerErrorException;

import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * @Description: 实现json的序列化和反序列化,只需要对需要转换的
 * 属性打上@convet主机即可
 * @Author: my
 * @CreateDate: 2020/3/6 9:32
 * @UpdateUser:
 * @UpdateDate: 2020/3/6 9:32
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Component
@Slf4j
public class ConveterObjectAndJson<T> implements AttributeConverter<T,String> {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public String convertToDatabaseColumn(T t) {
        try {
            String jsonString = objectMapper.writeValueAsString(t);
            return jsonString;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new ServerErrorException("9999");
        }
    }

    @Override
    public T convertToEntityAttribute(String s) {
        try {
            if(s.isEmpty()){
                return null;
            }
            T t = objectMapper.readValue(s, new TypeReference<T>() {
            });
            return t;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerErrorException("9999");
        }
    }
}


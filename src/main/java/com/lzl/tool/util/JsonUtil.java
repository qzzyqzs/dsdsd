package com.lzl.tool.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    public static ObjectMapper objectMapper = new ObjectMapper();

    //将对象转换为json字符串
    public static String getJsonString(Object obj){
        String jsonString=null;
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }

    //字符串转换为对象
    public static <T> T getPojo(String jsonString,Class<T> tClass){
        T t=null;
        try {
            t = objectMapper.readValue(jsonString, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}

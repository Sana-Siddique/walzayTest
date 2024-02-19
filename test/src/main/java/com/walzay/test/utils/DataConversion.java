package com.walzay.test.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DataConversion {

    public static Map<String, Object> jsonStringToMap(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, new TypeReference<HashMap<String,Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Map<String, Object> convertToHashMap(T entity) throws IllegalAccessException {
        Map<String, Object> hashMap = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(entity);
            hashMap.put(field.getName(), value);
        }

        return hashMap;
    }
}

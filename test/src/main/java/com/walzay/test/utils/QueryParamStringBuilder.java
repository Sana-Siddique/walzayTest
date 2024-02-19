package com.walzay.test.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class QueryParamStringBuilder {

    public static void main(String[] args) {
        int current = 1;
        String lang = "EN";
        int rowCount = 100;
        boolean includePricingDetails = true;
        String searchPhrase = null;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("current", current);
        paramMap.put("lang", lang);
        paramMap.put("rowCount", rowCount);
        paramMap.put("includePricingDetails", true);
        if (searchPhrase != null) {
            paramMap.put("searchPhrase", searchPhrase);
        }
        String queryString = buildQueryString(paramMap);
        System.out.println("Query string: " + queryString);
    }

    public static String buildQueryString(Map<String, Object> paramMap) {
        StringBuilder queryString = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                String key = URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = URLEncoder.encode(entry.getValue().toString(), "UTF-8");
                queryString.append(key).append("=").append(value);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return queryString.toString();
    }
}

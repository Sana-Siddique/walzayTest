package com.walzay.test.utils;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class GenerateSignature {


    public static void main(String[] args) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("current", 1);
        paramMap.put("lang", "EN");
        paramMap.put("rowCount", 100);
        String token = generateGiftLoveDate();
        System.out.println(token);
        System.out.println(generateSignature("items", HttpMethod.GET, paramMap, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNdW5lcm8iLCJleHAiOjE3MDgzNDUwMjcsInR5cGUiOiJBdXRob3JpemF0aW9uVG9rZW4iLCJjcmVhdGlvbkRhdGUiOjE3MDgyNTg2MjcsInVzZXJJZCI6MTEzLCJ2ZXJzaW9uIjoxfQ.84xz--HIPRHf8ZiYnMQryDjBKslyN6lDgOxW_LfT6Qo",token));

    }


    protected static String generateGiftLoveDate(){
        String giftlovDate = LocalDateTime.now(ZoneId.of("UCT")).format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        return giftlovDate;
    }


    protected static String generateSignature(String requestName, HttpMethod method, Map<String, Object> params, String apiKey, String giftlovDate) {
        StringBuilder concatenatedValues = new StringBuilder();
        String signature = null;
        String signature1 = null;
        String content = null;
        try {
            // Sort parameters by values
            if (params != null) {
                List<Object> sortedParams = new ArrayList<>(params.values());
                Collections.sort(sortedParams, Comparator.comparing(Object::toString));

                for (Object value : sortedParams) {
                    concatenatedValues.append(value.toString());
                }
                String sortedString = concatenatedValues.toString();
                content = String.format("%s%s%s%s%s", requestName, method,sortedString,giftlovDate, apiKey);
            }else
                content = String.format("%s%s%s%s", requestName, method,giftlovDate, apiKey);
            System.out.println("content to be hashed :::: " +content);
            signature = hashString(content, "coding_challenge_1");
        }catch (Exception e){
            e.printStackTrace();
        }
        return signature.toLowerCase();
    }

    private static String hashString(String input, String algorithm) {
        try {
             Mac sha512Hmac;
            String result;
            final byte[] byteKey = algorithm.getBytes("UTF-8");
            sha512Hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA512");
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(input.getBytes("UTF-8"));
            result = bytesToHex(macData);
            return result;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}

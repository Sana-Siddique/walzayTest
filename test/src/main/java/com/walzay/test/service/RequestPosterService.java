package com.walzay.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

@Service
public class RequestPosterService {

    @Value("${giftlov.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public RequestPosterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String postRequest(String requestName,HttpMethod requestMethod,String giftsDate,String signature, String queryParams, HashMap<String, Object> body,String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String,String> responseMap = null;
        String url = null;
        String response = null;
        HttpEntity<String> httpEntity = null;
        try {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        if (apiKey != null)
            headers.set("Authorization",apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept",MediaType.APPLICATION_JSON_VALUE);
       // headers.set("Prefer","code=200, dynamic=true");
        headers.add("X-GIFTLOV-DATE", giftsDate);
        if (signature != null)
            headers.add("signature",signature);
        ResponseEntity<String> responseEntity = null;
        if (body !=null) {
            String bodyStr = objectMapper.writeValueAsString(body);
             httpEntity = new HttpEntity<>(bodyStr, headers);
        }else{
            httpEntity = new HttpEntity<>(headers);
        }

        if (queryParams != null){
            url = apiUrl+"/"+requestName+"?"+queryParams;
        }else{
            url = apiUrl+"/"+requestName;
        }
        responseEntity = restTemplate.exchange(url, requestMethod , httpEntity, String.class);
        response = responseEntity.getBody();
        System.out.println(response);

        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public ResponseEntity<byte[]> postRequest(String requestName,HttpMethod requestMethod,String giftsDate,String signature,String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String,String> responseMap = null;
        String url = null;
        String response = null;
        HttpEntity<String> httpEntity = null;
        ResponseEntity<byte[]> responseEntity = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            HttpHeaders headers = new HttpHeaders();
            if (apiKey != null)
                headers.set("Authorization",apiKey);
            headers.set("Accept","application/json,application/zip");
            headers.add("X-GIFTLOV-DATE", giftsDate);
            if (signature != null)
                headers.add("signature",signature);
                httpEntity = new HttpEntity<>(headers);
                url = apiUrl+"/"+requestName;
            responseEntity = restTemplate.exchange(url, requestMethod , httpEntity, byte[].class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return responseEntity;
    }


}

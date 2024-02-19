package com.walzay.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walzay.test.cache.CacheOperations;
import com.walzay.test.utils.DataConversion;
import com.walzay.test.utils.GenerateSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class SignInServiceImpl extends GenerateSignature implements SignInService{

    @Autowired
    RequestPosterService requestPosterService;
    @Autowired
    CacheOperations cacheOperations;

    @Override
    public ResponseEntity<Map<String,String>> generateToken(String username, String password, BindingResult bindingResult){
        String apiName = "/generateToken";
        String apiResponse = null;
        String token = null;
        Map<String, String> response = new HashMap<>();
        try {

            if (bindingResult.hasErrors()){
                for (FieldError error : bindingResult.getFieldErrors()) {
                    response.put(error.getField(), error.getDefaultMessage());
                }
                return ResponseEntity.badRequest().body(response);
            }else {

                String giftLoveDate = generateGiftLoveDate();
                HashMap<String, Object> body = new HashMap<String, Object>();
                body.put("username", username);
                body.put("password", password);
                apiResponse = requestPosterService.postRequest(apiName, HttpMethod.POST, giftLoveDate, null, null, body, null);
                Map<String, Object> map = DataConversion.jsonStringToMap(apiResponse);
                if (map.containsKey("token")) {
                    // place it in redis
                    token = map.get("token").toString();
                    cacheOperations.putUserToken(username, token);
                    response.put("token",token);
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.ok().body(response);
    }

}

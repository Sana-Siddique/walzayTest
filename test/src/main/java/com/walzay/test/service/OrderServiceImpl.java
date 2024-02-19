package com.walzay.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walzay.test.cache.CacheOperations;
import com.walzay.test.model.OrderPlacementRequest;
import com.walzay.test.utils.DataConversion;
import com.walzay.test.utils.GenerateSignature;
import com.walzay.test.utils.QueryParamStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl extends GenerateSignature implements OrderService{

    @Autowired
    RequestPosterService requestPosterService;
    @Autowired
    CacheOperations cacheOperation;


    @Override
    public ResponseEntity<?> orderPlacement(OrderPlacementRequest orderPlacementRequest, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                for (FieldError error : bindingResult.getFieldErrors()) {
                    response.put(error.getField(), error.getDefaultMessage());
                }
                return ResponseEntity.badRequest().body(response);
            } else {
                String apiName = "placeOrder";
                HashMap<String, Object> body = new HashMap<String, Object>();
                ObjectMapper objectMapper = new ObjectMapper();
                String apiResponse = null;
                HttpMethod requestMethod = HttpMethod.POST;
                String signature = null;
                String giftLoveDate = generateGiftLoveDate();
                body = objectMapper.convertValue(orderPlacementRequest, HashMap.class);
                signature = generateSignature(apiName, requestMethod, body, cacheOperation.getUserToken("coding_challenge_1"), giftLoveDate);
                apiResponse = (String) requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature, null, body, cacheOperation.getUserToken("coding_challenge_1"));
                Map<String, Object> map = DataConversion.jsonStringToMap(apiResponse);
                // if (map != null)
                return ResponseEntity.ok().body(map);
            }
        }catch(Exception exception){
            if (exception.getMessage().contains(String.valueOf(HttpStatus.NOT_FOUND))){
               return ResponseEntity.notFound().build();
            }
            String errorMessage = "An error occurred: " + exception.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @Override
    public ResponseEntity<Map<String,Object>> getOrderStatus(String identifier) {
        String apiName = "orders/"+identifier;
        String apiResponse = null;
        HttpMethod requestMethod = HttpMethod.GET;
        String giftLoveDate = generateGiftLoveDate();
        String signature = null;
        signature = generateSignature(apiName, requestMethod, null, cacheOperation.getUserToken("coding_challenge_1"), giftLoveDate);
        apiResponse = (String) requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature,null,null,cacheOperation.getUserToken("coding_challenge_1"));
        Map<String, Object> map = DataConversion.jsonStringToMap(apiResponse);
        return ResponseEntity.ok().body(map);
    }

    @Override
    public ResponseEntity<Map<String,Object>> getOrder( Map<String, Object> queryParams) {
        String apiName = "orders";
        HttpMethod requestMethod = HttpMethod.GET;
        String apiResponse = null;
        String queryParamsString = null;
        String giftLoveDate = generateGiftLoveDate();
        String signature = null;
        signature = generateSignature(apiName, requestMethod, queryParams, cacheOperation.getUserToken("coding_challenge_1"), giftLoveDate);
        queryParamsString = QueryParamStringBuilder.buildQueryString(queryParams);
        apiResponse = (String) requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature,queryParamsString,null,cacheOperation.getUserToken("coding_challenge_1"));
        Map<String, Object> map = DataConversion.jsonStringToMap(apiResponse);
        return ResponseEntity.ok().body(map);
    }
}

package com.walzay.test.service;

import com.walzay.test.cache.CacheOperations;
import com.walzay.test.model.ItemDisplayRequest;
import com.walzay.test.utils.DataConversion;
import com.walzay.test.utils.GenerateSignature;
import com.walzay.test.utils.QueryParamStringBuilder;
import com.walzay.test.validation.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@Validated
public class CatalogueServiceImpl extends GenerateSignature implements CatalogueService {

    @Autowired
    RequestPosterService requestPosterService;
    @Autowired
    CacheOperations cacheOperations ;
    @Autowired
    private final ItemValidator validator;

    public CatalogueServiceImpl(RequestPosterService requestPosterService, CacheOperations cacheOperations, ItemValidator validator) {
        this.requestPosterService = requestPosterService;
        this.cacheOperations = cacheOperations;
        this.validator = validator;
    }

    @Override
    public  ResponseEntity<?>  displayItems(Map<String, Object> paramMap) {
        Map<String, String> response = new HashMap<>();
            String apiName = "items";
            HttpMethod requestMethod = HttpMethod.GET;
            String apiResponse = null;
            String queryParamsString = null;
            String giftLoveDate = generateGiftLoveDate();
            String signature = null;
            String token = null;
            try {
                signature = generateSignature(apiName, requestMethod, paramMap, cacheOperations.getUserToken("coding_challenge_1"), giftLoveDate);
                queryParamsString = QueryParamStringBuilder.buildQueryString(paramMap);
                apiResponse = (String) requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature, queryParamsString, null, cacheOperations.getUserToken("coding_challenge_1"));
                Map<String, Object> map = DataConversion.jsonStringToMap(apiResponse);
                if (map != null && map.containsKey("items"))
                    return ResponseEntity.ok().body(map.get("items"));
                else
                    return ResponseEntity.notFound().build();
            }catch (Exception exception){
                if (exception.getMessage().contains(String.valueOf(HttpStatus.UNAUTHORIZED))){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kindly generate new token");
                }
                String errorMessage = "An error occurred: " + exception.getMessage();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
            }
    }

    @Override
    public ResponseEntity<?> downloadCatalogueImages() {
        String apiName = "downloadCatalogueImages";
        HttpMethod requestMethod = HttpMethod.GET;
        String giftLoveDate = generateGiftLoveDate();
        String signature = null;
        try{
            signature = generateSignature(apiName, requestMethod, null, cacheOperations.getUserToken("coding_challenge_1"), giftLoveDate);
            return requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature, cacheOperations.getUserToken("coding_challenge_1"));
        }catch (Exception exception){
            if (exception.getMessage().contains(String.valueOf(HttpStatus.UNAUTHORIZED))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kindly generate new token");
            }
            String errorMessage = "An error occurred: " + exception.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @Override
    public Boolean isItemAvailable(String cardIdentifer, int quantity) {
        String apiName = "checkItemAvailability/";
        String apiResponse = null;
        HttpMethod requestMethod = HttpMethod.GET;
        String giftLoveDate = generateGiftLoveDate();
        String signature = null;
        Map<String,Object> queryParam = new HashMap<>();
        queryParam.put("cardIdentifer",cardIdentifer);
        queryParam.put("quantity",quantity);
        signature = generateSignature(apiName, requestMethod, queryParam, cacheOperations.getUserToken("coding_challenge_1"), giftLoveDate);
        apiName = apiName+cardIdentifer+"/"+quantity;
        apiResponse = (String) requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature,null,null,cacheOperations.getUserToken("coding_challenge_1"));
        Map<String, Object> map = DataConversion.jsonStringToMap(apiResponse);
        if (map != null && map.containsKey("available"))
            return Boolean.valueOf(map.get("available").toString());
        else
            return false;
    }
}

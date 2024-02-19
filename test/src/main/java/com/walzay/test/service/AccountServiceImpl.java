package com.walzay.test.service;

import com.walzay.test.model.ChangePasswordRequest;
import com.walzay.test.utils.DataConversion;
import com.walzay.test.utils.GenerateSignature;
import com.walzay.test.utils.QueryParamStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl extends GenerateSignature implements AccountService{

    @Autowired
    RequestPosterService requestPosterService;


    @Override
    public ResponseEntity<byte[]> getCompanyLogo() {
        String apiName = "81529147/logo";
        HttpMethod requestMethod = HttpMethod.GET;
        String giftLoveDate = generateGiftLoveDate();
        String signature = null;
        signature = generateSignature(apiName, requestMethod, null, "esse ullamco", giftLoveDate);
        return requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature,"esse ullamco");
    }

    @Override
    public ArrayList displayUsers(Map<String, Object> queryParams) {
        String apiName = "81529147/queryUsers";
        HttpMethod requestMethod = HttpMethod.GET;
        String apiResponse = null;
        String queryParamsString = null;
        String giftLoveDate = generateGiftLoveDate();
        String signature = null;
        signature = generateSignature(apiName, requestMethod, queryParams, "esse ullamco", giftLoveDate);
        queryParamsString = QueryParamStringBuilder.buildQueryString(queryParams);
        apiResponse = (String) requestPosterService.postRequest(apiName, requestMethod, giftLoveDate, signature,queryParamsString,null,"esse ullamco");
        Map<String, Object> map = DataConversion.jsonStringToMap(apiResponse);
        if (map != null && map.containsKey("users"))
            return (ArrayList) map.get("users");
        else
            return null;
    }

    @Override
    public Boolean changeUserPassword(ChangePasswordRequest changePasswordRequest) {
        String apiName = "81529137/generateToken";
        String apiResponse = null;
        String token = null;
        try {

            String giftLoveDate = generateGiftLoveDate();
            HashMap<String, Object> body = new HashMap<>();
            body.put("oldPassword", changePasswordRequest.getOldPassword());
            body.put("newPassword", changePasswordRequest.getNewPassword());
            apiResponse = requestPosterService.postRequest(apiName, HttpMethod.POST, giftLoveDate, null,null,body,null);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return true;
    }
}

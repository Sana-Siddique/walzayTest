package com.walzay.test.service;

import com.walzay.test.model.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

public interface AccountService {

    ResponseEntity<byte[]> getCompanyLogo();

    ArrayList displayUsers(Map<String, Object> map);

    Boolean changeUserPassword(ChangePasswordRequest changePasswordRequest);
}

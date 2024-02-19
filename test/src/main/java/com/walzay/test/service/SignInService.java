package com.walzay.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface SignInService {

    ResponseEntity<Map<String,String>> generateToken(String username, String password, BindingResult bindingResult);
}

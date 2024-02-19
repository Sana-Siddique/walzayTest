package com.walzay.test.controllers;


import com.walzay.test.model.TokenRequest;
import com.walzay.test.service.SignInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/login")
public class TokenController {

    private final SignInService signInService;

    @Autowired
    public TokenController(SignInService signInService) {
        this.signInService = signInService;
    }

    @PostMapping("/generateToken")
    public ResponseEntity<Map<String,String>> generateToken(@Valid @RequestBody TokenRequest request, BindingResult bindingResult) {
        return signInService.generateToken(request.getUsername(), request.getPassword(),bindingResult);
    }

//    @PostMapping("/checkToken")
//    public ResponseEntity<String> checkToken(@RequestBody TokenRequest request) {
//        return ResponseEntity.status(HttpStatus.OK).body(signInService.generateToken(request.getUsername(), request.getPassword()));
//    }
}

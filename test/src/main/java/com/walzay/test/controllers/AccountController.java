package com.walzay.test.controllers;

import com.walzay.test.model.ChangePasswordRequest;
import com.walzay.test.model.TokenRequest;
import com.walzay.test.service.AccountService;
import com.walzay.test.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/logo")
    public ResponseEntity<byte[]> getLogo() {
        return accountService.getCompanyLogo();
    }

    @GetMapping("/user")
    public ResponseEntity<ArrayList> getUsers(@RequestParam(value = "searchPhrase", required = false) String searchPhrase){
        Map<String, Object> paramMap = new HashMap<>();
        ArrayList response = new ArrayList();

        if (searchPhrase != null) {
            paramMap.put("searchPhrase", searchPhrase);
        }
        response = accountService.displayUsers(paramMap);
        if (response != null)
            return ResponseEntity.status(HttpStatus.OK).body(response);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/changepassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.changeUserPassword(changePasswordRequest));
    }


}

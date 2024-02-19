package com.walzay.test.service;

import com.walzay.test.model.ItemDisplayRequest;
import com.walzay.test.validation.ItemValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Map;

public interface CatalogueService {

    ResponseEntity<?> displayItems(Map<String, Object> paramMap);

    ResponseEntity<?> downloadCatalogueImages();

    Boolean isItemAvailable(String cardIdentifer, int quantity);
}

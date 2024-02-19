package com.walzay.test.service;

import com.walzay.test.model.OrderPlacementRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface OrderService {

    ResponseEntity<?> orderPlacement(OrderPlacementRequest orderPlacementRequest, BindingResult bindingResult);
    ResponseEntity<Map<String,Object>> getOrderStatus(String identifier);
    ResponseEntity<Map<String,Object>> getOrder(Map<String, Object> queryParams);
}

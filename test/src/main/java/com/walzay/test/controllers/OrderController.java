package com.walzay.test.controllers;

import com.walzay.test.model.OrderPlacementRequest;
import com.walzay.test.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("order")
@CrossOrigin(origins = "http://localhost:3000/")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> orderPlacement(@Valid @RequestBody OrderPlacementRequest orderPlacementRequest, BindingResult bindingResult) {
        return orderService.orderPlacement(orderPlacementRequest,bindingResult);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Map<String,Object>> orderStatus(@PathVariable String identifier) {
        return orderService.getOrderStatus(identifier);
    }

    @GetMapping("")
    public ResponseEntity<Map<String,Object>> displayAllOrders(@RequestParam String current, @RequestParam String rowCount,@RequestParam(required = false) String cardItemIds, @RequestParam(required = false) String[] cardValues,
                                                      @RequestParam(required = false) String customerEmail, @RequestParam(required = false) String customerMobile, @RequestParam(required = false) String customerName, @RequestParam(required = false) String fromDate,
                                                      @RequestParam(required = false) String[] productIds, @RequestParam(required = false) String[] programIds, @RequestParam(required = false) String referenceNumber, @RequestParam(required = false) String searchPhrase,
                                                      @RequestParam(required = false) String toDate, @RequestParam(required = false) String[] userIds) {


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("current", current);
        paramMap.put("rowCount", rowCount);
        if (cardItemIds != null) {
            paramMap.put("cardItemIds", cardItemIds);
        }
        if (cardValues != null) {
            paramMap.put("cardValues", cardValues);
        }
        if (customerEmail != null) {
            paramMap.put("customerEmail", customerEmail);
        }
        if (customerMobile != null) {
            paramMap.put("customerMobile", customerMobile);
        }
        if (customerName != null) {
            paramMap.put("customerName", customerName);
        }
        if (fromDate != null) {
            paramMap.put("fromDate", fromDate);
        }
        if (productIds != null) {
            paramMap.put("productIds", productIds);
        }
        if (programIds != null) {
            paramMap.put("programIds", programIds);
        }
        if (referenceNumber != null) {
            paramMap.put("referenceNumber", referenceNumber);
        }
        if (searchPhrase != null) {
            paramMap.put("searchPhrase", searchPhrase);
        }
        if (toDate != null) {
            paramMap.put("toDate", toDate);
        }
        if (userIds != null) {
            paramMap.put("userIds", userIds);
        }
        return orderService.getOrder(paramMap);
    }
}

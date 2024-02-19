package com.walzay.test.controllers;

import com.walzay.test.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ItemController{

    private final CatalogueService catalogueService;

    @Autowired
    public ItemController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping("/items")
    public ResponseEntity<?> getItems(@Validated @RequestParam("current") int current, @RequestParam("lang") String lang, @RequestParam(value = "rowCount",required = false) int rowCount,
                                      @RequestParam(value = "includePricingDetails",required = false) boolean includePricingDetails,
                                      @RequestParam(value = "searchPhrase", required = false) String searchPhrase ) {
        ArrayList response = new ArrayList();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("current", current);
        paramMap.put("lang", lang);
        if (rowCount != 0) {
            paramMap.put("rowCount", rowCount);
        }
        paramMap.put("includePricingDetails", includePricingDetails);
        if (searchPhrase != null) {
            paramMap.put("searchPhrase", searchPhrase);
        }

        // Return the response received from the API
        return catalogueService.displayItems(paramMap);
    }


    @GetMapping("/downloadCatalogueImages")
    public ResponseEntity<?> downloadCatalogue() {
        return catalogueService.downloadCatalogueImages();
    }

    @GetMapping("/checkItemAvailability/{itemId}/{quantity}")
    public String checkItemAvailability(@PathVariable String itemId, @PathVariable int quantity) {
        return catalogueService.isItemAvailable(itemId, quantity).toString();
    }
    }

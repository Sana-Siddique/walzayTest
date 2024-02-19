package com.walzay.test.validation;

import com.walzay.test.model.ItemDisplayRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

@Component
public class ItemValidator implements  Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Map.class.equals(clazz);
    }

    @Override
    public void validate(Object source, Errors errors) {
        Map<String, Object>  itemRequest = (Map<String, Object>) source;
        if (Integer.valueOf(itemRequest.get("current").toString()) < 1) {
            errors.rejectValue("current", "current.invalid", "Current must be greater than or equal to 1");
        }
        if (itemRequest.get("lang") == null || itemRequest.get("lang").toString().isEmpty()) {
            errors.rejectValue("lang", "lang.empty", "Language code must not be empty");
        }
        if (Integer.valueOf(itemRequest.get("rowCount").toString()) < 1) {
            errors.rejectValue("rowCount", "rowCount.invalid", "Row count must be greater than or equal to 1");
        }
        if (itemRequest.containsKey("searchPhrase") && itemRequest.get("searchPhrase") == null || itemRequest.get("searchPhrase").toString().isEmpty()) {
            errors.rejectValue("searchPhrase", "searchPhrase.invalid", "searchPhrase must not be empty");
        }
    }
}

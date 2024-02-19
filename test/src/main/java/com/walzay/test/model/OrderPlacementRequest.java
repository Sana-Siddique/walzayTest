package com.walzay.test.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderPlacementRequest {

    @NotEmpty(message = "Customer name must not be empty")
    private String customerName;

    @NotEmpty(message = "First name must not be empty")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @NotEmpty(message = "Reference number must not be empty")
    private String referenceNo;

    @NotEmpty(message = "Delivery channel must not be empty")
    private String deliveryChannel;

    @Pattern(regexp = "\\+\\d{6,15}", message = "Contact number must be in the format +<country code><number>")
    private String contactNumber;

    @Pattern(regexp = "\\+\\d{6,15}", message = "SMS mobile number must be in the format +<country code><number>")
    private String smsMobileNumber;

    @NotEmpty(message = "Email address must not be empty")
    @Pattern(regexp = ".+@.+\\..+", message = "Email address must be valid")
    private String emailAddress;

    @NotEmpty(message = "Country code must not be empty")
    @Size(min = 2, max = 2, message = "Country code must be 2 characters long")
    private String countryCode;

    @NotEmpty(message = "Language code must not be empty")
    @Size(min = 2, max = 2, message = "Language code must be 2 characters long")
    private String languageCode;

    @NotEmpty(message = "Order date must not be empty")
    private String orderDate;

    @NotNull(message = "Line items must not be null")
    @Valid
    private List<LineItem> lineItems;

    @Data
    public static class LineItem {
        public String cardItemId;
        public int value;
    }

}

package com.walzay.test.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDisplayRequest {

    @Min(value = 1, message = "current must be greater than or equal to 1")
    public int current;
    @NotNull(message = "lang must not be null")
    public String lang;
    @Min(value = 1, message = "rowCount must be greater than or equal to 1")
    @Max(value = 100, message = "rowCount must be less than or equal to 100")
    public int rowCount;
    public Boolean includePricingDetails;
    public String searchPhrase;
}

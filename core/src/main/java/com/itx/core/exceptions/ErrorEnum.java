package com.itx.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    SERVICE_ERROR("SERVICE_ERROR",
            "SERVICE_ERROR",
            "Runtime service error"),
    INVALID_PRODUCT_ERROR("INVALID_PRODUCT_ERROR",
            "Invalid product error",
            "Invalid requested product value"),
    INVALID_BRAND_ERROR("INVALID_BRAND_ERROR",
            "Invalid brand error",
            "Invalid requested brand value"),
    INVALID_APPLICATION_DATE_ERROR("INVALID_APPLICATION_DATE_ERROR",
            "Invalid applicationDate error",
            "Application Date is not valid"),
    PRICE_NOT_FOUND_ERROR("PRICE_NOT_FOUND_ERROR",
            "Price not found error",
            "RequestedPrice was not found for selected brand, product and date");

    private final String code;
    private final String technicalDescription;
    private final String userDescription;
}

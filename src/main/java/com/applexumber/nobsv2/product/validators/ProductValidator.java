package com.applexumber.nobsv2.product.validators;

import com.applexumber.nobsv2.exceptions.ErrorMessages;
import com.applexumber.nobsv2.exceptions.ProductNotValidException;
import com.applexumber.nobsv2.product.model.Product;
import io.micrometer.common.util.StringUtils;

public class ProductValidator {
    private ProductValidator() {}
    public static void execute(Product product){
        if(StringUtils.isEmpty(product.getName())) {
            throw new ProductNotValidException(ErrorMessages.NAME_REQUIRED.getMessage());
        }
        if(product.getDescription().length() < 20) {
            throw new ProductNotValidException(ErrorMessages.DESCRIPTION_LENGHT.getMessage());
        }
        if(product.getPrice() == null || product.getPrice() < 0) {
            throw new ProductNotValidException(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage());
        }
    }
}

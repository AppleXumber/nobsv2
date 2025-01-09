package com.applexumber.nobsv2;

import com.applexumber.nobsv2.product.model.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductValidatorTests {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    public void given_product_has_invalid_name_then_get_product_not_valid_exception() {
        Product product = new Product();
        product.setId(1);
        product.setName("");
        product.setDescription("Product description which is at least 20 chars");
        product.setPrice(9.99);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Name is required", violations.iterator().next().getMessage());
    }

    @Test
    public void given_product_has_short_description_get_product_not_valid_exception() {
        Product product = new Product();
        product.setId(1);
        product.setName("The product name");
        product.setDescription("Too short");
        product.setPrice(9.99);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Description must be at least 20 characters long.", violations.iterator().next().getMessage());
    }

    @Test
    public void given_product_has_negative_price_get_product_not_valid_exception() {
        Product product = new Product();
        product.setId(1);
        product.setName("The product name");
        product.setDescription("Valid description with more than 20 chars");
        product.setPrice(-9.99);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Price must not be negative.", violations.iterator().next().getMessage());
    }

    @Test
    public void given_valid_product_then_pass_validation() {
        Product product = new Product();
        product.setId(1);
        product.setName("The product name");
        product.setDescription("Valid description with more than 20 chars");
        product.setPrice(9.99);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

}

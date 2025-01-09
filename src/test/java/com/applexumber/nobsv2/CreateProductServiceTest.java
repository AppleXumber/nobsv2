package com.applexumber.nobsv2;


import com.applexumber.nobsv2.exceptions.ProductNotValidException;
import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.model.Product;
import com.applexumber.nobsv2.product.model.ProductDTO;
import com.applexumber.nobsv2.product.services.CreateProductService;
import com.applexumber.nobsv2.product.validators.ProductValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private CreateProductService service;

    private Validator validator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void given_valid_product_when_create_return_product_dto() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product Name");
        product.setDescription("Product description which is at least 20 chars");
        product.setPrice(9.99);

        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setName("Product Name");
        savedProduct.setDescription("Product description which is at least 20 chars");
        savedProduct.setPrice(9.99);

        when(repository.save(product)).thenReturn(savedProduct);

        ResponseEntity<ProductDTO> response = service.execute(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(repository, times(1)).save(product);
    }

    @Test
    public void given_product_when_create_then_handle_repository_exception() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product Name");
        product.setDescription("Product description which is at least 20 chars");
        product.setPrice(9.99);

        when(repository.save(product)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.execute(product);
        });

        assertEquals("Database error", exception.getMessage());

        verify(repository, times(1)).save(product);
    }

    @Test
    public void given_product_not_valid_then_return_validation_exception() {
        Product invalidProduct = new Product();
        invalidProduct.setId(1);
        invalidProduct.setName("");
        invalidProduct.setDescription("Product description which is at least 20 chars");
        invalidProduct.setPrice(9.99);

        Set<ConstraintViolation<Product>> violations = validator.validate(invalidProduct);

        assertFalse(violations.isEmpty(), "Product should have validation violations");

    }

}

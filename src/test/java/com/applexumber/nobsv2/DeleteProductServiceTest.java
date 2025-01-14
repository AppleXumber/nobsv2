package com.applexumber.nobsv2;

import com.applexumber.nobsv2.exceptions.ProductNotFoundException;
import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.model.Product;
import com.applexumber.nobsv2.product.services.DeleteProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private DeleteProductService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_existign_product_id_when_execute_then_delete_and_return_no_content() {
        Integer productId = 1;
        Product product = new Product();
        product.setId(productId);

        Mockito.when(repository.findById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Void> response = service.execute(productId);

        Mockito.verify(repository, Mockito.times(1)).deleteById(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void given_non_existing_product_id_when_execute_then_throw_product_not_found_exception() {
        Integer productId = 1;

        Mockito.when(repository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.execute(productId));

        Mockito.verify(repository, Mockito.never()).deleteById(productId);
    }

}

package com.applexumber.nobsv2;

import com.applexumber.nobsv2.exceptions.ProductNotFoundException;
import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.model.Product;
import com.applexumber.nobsv2.product.model.ProductDTO;
import com.applexumber.nobsv2.product.model.UpdateProductCommand;
import com.applexumber.nobsv2.product.services.UpdateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductServiceTest {

    @Mock
    private ProductRepository repository;

    private UpdateProductService service;

    @BeforeEach
    void setup() {
        service = new UpdateProductService(repository);
    }

    @Test
    public void given_valid_product_command_when_execute_then_update_product_and_return_dto() {
        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setName("Old Product");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(20.0);

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(30.0);

        UpdateProductCommand command = new UpdateProductCommand(1, updatedProduct);

        when(repository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<ProductDTO> response = service.execute(command);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProductDTO dto = response.getBody();
        assertNotNull(dto);
        assertEquals("Updated Product", dto.getName());
        assertEquals("Updated Description", dto.getDescription());

        verify(repository).save(updatedProduct);
    }

    @Test
    public void given_nonexistent_product_id_when_execute_then_throw_product_not_found_exception() {
        UpdateProductCommand command = new UpdateProductCommand(999, null);

        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.execute(command));

        verify(repository, never()).save(any());

    }
}

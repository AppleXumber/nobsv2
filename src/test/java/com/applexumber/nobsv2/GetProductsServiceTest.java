package com.applexumber.nobsv2;

import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.model.Product;
import com.applexumber.nobsv2.product.model.ProductDTO;
import com.applexumber.nobsv2.product.services.GetProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetProductsServiceTest {
    @Mock
    private ProductRepository repository;

    private GetProductsService service;

    @BeforeEach
    void setup() {
        service = new GetProductsService(repository);
    }

    @Test
    void given_products_exist_when_execute_then_return_productDTO_list() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(20.0);

        List<Product> products = List.of(product1, product2);

        Mockito.when(repository.findAll()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> response = service.execute(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());

        ProductDTO dto1 = response.getBody().get(0);
        ProductDTO dto2 = response.getBody().get(1);

        assertEquals("Product 1", dto1.getName());
        assertEquals("Description 1", dto1.getDescription());

        assertEquals("Product 2", dto2.getName());
        assertEquals("Description 2", dto2.getDescription());
    }

    @Test
    public void given_no_products_when_execute_then_return_empty_list() {
        Mockito.when(repository.findAll()).thenReturn(List.of());

        ResponseEntity<List<ProductDTO>> response = service.execute(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}

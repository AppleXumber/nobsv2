package com.applexumber.nobsv2;

import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.model.Product;
import com.applexumber.nobsv2.product.model.ProductDTO;
import com.applexumber.nobsv2.product.services.SearchProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchProductServiceTest {
    @Mock
    private ProductRepository repository;

    private SearchProductService service;

    @BeforeEach
    void setup() {
        service = new SearchProductService(repository);
    }

    @Test
    public void given_valid_name_when_execute_then_return_matching_products() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Another Product");
        product2.setDescription("Description 2");
        product2.setPrice(20.0);

        String searchQuery = "Product";

        when(repository.findByNameOrDescriptionContaining(searchQuery)).thenReturn(Arrays.asList(product1, product2));

        ResponseEntity<List<ProductDTO>> response = service.execute(searchQuery);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProductDTO> dtos = response.getBody();
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("Product 1", dtos.get(0).getName());
        assertEquals("Another Product", dtos.get(1).getName());

        verify(repository).findByNameOrDescriptionContaining(searchQuery);

    }

    @Test
    public void given_no_matching_name_when_execute_then_return_empty_list() {
        String searchQuery = "Nonexistent";
        when(repository.findByNameOrDescriptionContaining(searchQuery)).thenReturn(List.of());

        ResponseEntity<List<ProductDTO>> response = service.execute(searchQuery);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProductDTO> dtos = response.getBody();
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());

        verify(repository).findByNameOrDescriptionContaining(searchQuery);

    }
}

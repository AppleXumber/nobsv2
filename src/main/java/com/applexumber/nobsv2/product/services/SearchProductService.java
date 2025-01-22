package com.applexumber.nobsv2.product.services;

import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.Query;
import com.applexumber.nobsv2.product.model.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchProductService implements Query<String, List<ProductDTO>> {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(SearchProductService.class);

    public SearchProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(String name) {
        logger.info("Executing " + getClass() + " input: " + name);
        return ResponseEntity.ok(productRepository.findByNameOrDescriptionContaining(name).stream().map(ProductDTO::new).toList());
    }
}

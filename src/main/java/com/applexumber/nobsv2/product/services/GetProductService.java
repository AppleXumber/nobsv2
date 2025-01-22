package com.applexumber.nobsv2.product.services;

import com.applexumber.nobsv2.exceptions.ProductNotFoundException;
import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.Query;
import com.applexumber.nobsv2.product.model.Product;
import com.applexumber.nobsv2.product.model.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProductService implements Query<Integer, ProductDTO> {
    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetProductService.class);

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable("productCache")
    public ResponseEntity<ProductDTO> execute(Integer input) {
        logger.info("Executing " + getClass() + " input: " + input);
        Optional<Product> productOptional = productRepository.findById(input);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(new ProductDTO(productOptional.get()));
        }

        throw new ProductNotFoundException();
    }
}

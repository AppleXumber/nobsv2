package com.applexumber.nobsv2.product.services;

import com.applexumber.nobsv2.Command;
import com.applexumber.nobsv2.product.ProductRepository;
import com.applexumber.nobsv2.product.model.Product;
import com.applexumber.nobsv2.product.model.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService implements Command<Product, ProductDTO> {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreateProductService.class);

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Product product) {
        logger.info("Executing " + getClass() + " input: " + product.toString());
        // ProductValidator.execute(product);

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(savedProduct));
    }

}

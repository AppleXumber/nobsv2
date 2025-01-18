package com.applexumber.nobsv2.product.headers;

import com.applexumber.nobsv2.product.model.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderController {

    @GetMapping("/header")
    public String getRegionalResponse(@RequestHeader(required = false, defaultValue = "BR ") String region) {
        if(region.equals("US")) return "BALD EAGLE FREEDOM";
        if(region.equals("CA")) return "MAPLE SYRUP";
        if(region.equals("BR")) return "FUTEBOL E SAMBA CARALHO";
        return "COUNTRY NOT SUPPORTED";
    }

    @GetMapping(value = "/header/product", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Product> getProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Produto bãozudo");
        product.setDescription("O melhor produto existente");

        return ResponseEntity.ok(product);
    }

}

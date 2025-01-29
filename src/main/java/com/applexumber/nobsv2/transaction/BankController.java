package com.applexumber.nobsv2.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    public BankController(TransferService service) {
        this.service = service;
    }

    private final TransferService service;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO transferDTO) {
        return service.execute(transferDTO);
    }
}

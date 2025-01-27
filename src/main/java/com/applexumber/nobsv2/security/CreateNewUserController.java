package com.applexumber.nobsv2.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CreateNewUserController {

    private final PasswordEncoder encoder;

    private final CustomUserRepository repository;

    public CreateNewUserController(PasswordEncoder encoder, CustomUserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @PostMapping("/createnewuser")
    public ResponseEntity<String> createNewUser(@RequestBody CustomUser user) {
        Optional<CustomUser> optionalUser = repository.findById(user.getUsername());
        if(optionalUser.isEmpty()) {
            repository.save(new CustomUser(user.getUsername(), encoder.encode(user.getPassword())));
            return ResponseEntity.ok("Success");
        }

        return ResponseEntity.badRequest().body("Failure");
    }
}

package com.example.antifraud.Antifraudsystem.Controller;

import com.example.antifraud.Antifraudsystem.DTO.UserEntityDTO;
import com.example.antifraud.Antifraudsystem.Model.UserEntity;
import com.example.antifraud.Antifraudsystem.Service.UserEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserEntityService userEntityService;

    public AuthController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntityDTO> registerUser(@RequestBody @Valid UserEntity userEntity) {
        return new ResponseEntity<>(userEntityService.registerUser(userEntity), HttpStatus.CREATED);
    }
}

package com.example.antifraud.Antifraudsystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already has this role!")
public class RoleAlreadyExistsException extends RuntimeException {
}

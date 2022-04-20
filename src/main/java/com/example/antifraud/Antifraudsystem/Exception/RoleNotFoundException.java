package com.example.antifraud.Antifraudsystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such role found")
public class RoleNotFoundException extends RuntimeException{
}

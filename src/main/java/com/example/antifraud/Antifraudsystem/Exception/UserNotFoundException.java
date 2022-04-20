package com.example.antifraud.Antifraudsystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User doesn't exist!")
public class UserNotFoundException extends RuntimeException{
}

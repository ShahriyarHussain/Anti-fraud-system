package com.example.antifraud.Antifraudsystem.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

    @NotNull
    private Long amount;

    @NotNull
    private String ip;

    @NotNull
    private String number;

    @NotNull
    private String  region;

    @NotNull
    private String date;
}


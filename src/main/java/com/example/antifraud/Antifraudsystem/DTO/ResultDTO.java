package com.example.antifraud.Antifraudsystem.DTO;

import com.example.antifraud.Antifraudsystem.Enum.TransactionActions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private TransactionActions result;
    private String info;
}

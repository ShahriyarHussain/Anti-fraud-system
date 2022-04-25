package com.example.antifraud.Antifraudsystem.Model;

import com.example.antifraud.Antifraudsystem.DTO.TransactionDTO;
import com.example.antifraud.Antifraudsystem.Enum.Region;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSACTION_HISTORY")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotNull
    private Long amount;

    @NotNull
    private String ip;

    @NotNull
    private String number;

    @NotNull
    private Region region;

    @NotNull
    private LocalDateTime date;

    public Transaction(TransactionDTO transactionDTO) {
        this.amount = transactionDTO.getAmount();
        this.number = transactionDTO.getNumber();
        this.ip = transactionDTO.getIp();
        this.region = Region.valueOf(transactionDTO.getRegion());
        this.date = (LocalDateTime) DateTimeFormatter.ofPattern("yyy-MM-ddTHH:mm:ss").parse(transactionDTO.getDate());
    }
}

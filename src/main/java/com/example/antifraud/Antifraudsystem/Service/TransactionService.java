package com.example.antifraud.Antifraudsystem.Service;

import com.example.antifraud.Antifraudsystem.Enum.TransactionActions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransactionService {

    public ResponseEntity<Map<String, TransactionActions>> checkTransactionValidity(Map<String, Integer> transaction) {
        TransactionActions transactionActions;
        HttpStatus status = HttpStatus.OK;
        Integer amount = transaction.get("amount");

        if (amount == null || amount < 1 ) {
            transactionActions = TransactionActions.PROHIBITED;
            status = HttpStatus.BAD_REQUEST;
        } else if (amount > 1500) {
            transactionActions = TransactionActions.PROHIBITED;
        } else if (amount > 200) {
            transactionActions = TransactionActions.MANUAL_PROCESSING;
        } else {
            transactionActions = TransactionActions.ALLOWED;
        }
        return new ResponseEntity<>(Map.of("result", transactionActions), status);
    }

}

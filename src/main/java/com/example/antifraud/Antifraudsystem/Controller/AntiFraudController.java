package com.example.antifraud.Antifraudsystem.Controller;

import com.example.antifraud.Antifraudsystem.Enum.TransactionActions;
import com.example.antifraud.Antifraudsystem.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/antifraud")
public class AntiFraudController {

    private final TransactionService transactionService;

    public AntiFraudController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Map<String, TransactionActions>> validateTransaction(@RequestBody Map<String, Integer> transaction) {
        return transactionService.checkTransactionValidity(transaction);
    }

    @PostMapping("/suspicious-ip")
    public void addSuspiciousIp() {

    }

    @GetMapping("/suspicious-ip")
    public void getSuspiciousIp() {

    }

    @DeleteMapping("/suspicious-ip")
    public void deleteSuspiciousIp() {

    }

    @PostMapping("/stolencard")
    public void addStolenCard() {

    }

    @GetMapping("/stolencard")
    public void getStolenCard() {

    }

    @DeleteMapping("/stolencard")
    public void deleteStolenCard() {

    }

}

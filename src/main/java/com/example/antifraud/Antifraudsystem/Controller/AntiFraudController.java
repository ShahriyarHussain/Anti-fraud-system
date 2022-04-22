package com.example.antifraud.Antifraudsystem.Controller;

import com.example.antifraud.Antifraudsystem.DTO.ResultDTO;
import com.example.antifraud.Antifraudsystem.DTO.StatusDTO;
import com.example.antifraud.Antifraudsystem.DTO.TransactionDTO;
import com.example.antifraud.Antifraudsystem.Model.StolenCard;
import com.example.antifraud.Antifraudsystem.Model.SuspiciousIP;
import com.example.antifraud.Antifraudsystem.Service.StolenCardService;
import com.example.antifraud.Antifraudsystem.Service.SuspiciousIpService;
import com.example.antifraud.Antifraudsystem.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
public class AntiFraudController {

    private final TransactionService transactionService;
    private final StolenCardService stolenCardService;
    private final SuspiciousIpService suspiciousIpService;


    public AntiFraudController(TransactionService transactionService, StolenCardService stolenCardService,
                               SuspiciousIpService suspiciousIpService) {

        this.transactionService = transactionService;
        this.stolenCardService = stolenCardService;
        this.suspiciousIpService = suspiciousIpService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<ResultDTO> validateTransaction(@RequestBody @Valid TransactionDTO transaction) {
        return transactionService.checkTransactionValidity(transaction);
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<SuspiciousIP> addSuspiciousIp(@RequestBody @Valid SuspiciousIP suspiciousIP) {
        return ResponseEntity.ok(suspiciousIpService.addSuspiciousIP(suspiciousIP));
    }

    @GetMapping("/suspicious-ip")
    public ResponseEntity<List<SuspiciousIP>> getSuspiciousIp() {
        return ResponseEntity.ok(suspiciousIpService.showAllSuspiciousIps());
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<StatusDTO> deleteSuspiciousIp(@PathVariable String ip) {
        return ResponseEntity.ok(suspiciousIpService.deleteSuspiciousIp(ip));
    }

    @PostMapping("/stolencard")
    public ResponseEntity<StolenCard> addStolenCard(@RequestBody @Valid StolenCard stolenCard) {
        return ResponseEntity.ok(stolenCardService.addNewCard(stolenCard));
    }

    @GetMapping("/stolencard")
    public ResponseEntity<List<StolenCard>> getStolenCard() {
        return ResponseEntity.ok(stolenCardService.showAllCards());
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<StatusDTO> deleteStolenCard(@PathVariable String number) {
        return ResponseEntity.ok(stolenCardService.deleteCard(number));
    }
}

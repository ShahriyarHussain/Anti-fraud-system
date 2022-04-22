package com.example.antifraud.Antifraudsystem.Service;

import com.example.antifraud.Antifraudsystem.DTO.ResultDTO;
import com.example.antifraud.Antifraudsystem.DTO.TransactionDTO;
import com.example.antifraud.Antifraudsystem.Enum.TransactionActions;
import com.example.antifraud.Antifraudsystem.Repository.StolenCardRepository;
import com.example.antifraud.Antifraudsystem.Repository.SuspiciousIpRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TransactionService {

    private final StolenCardRepository stolenCardRepository;
    private final SuspiciousIpRepository suspiciousIpRepository;
    private final StolenCardService stolenCardService;

    public TransactionService(StolenCardRepository stolenCardRepository,
                              SuspiciousIpRepository suspiciousIpRepository, StolenCardService stolenCardService) {

        this.stolenCardRepository = stolenCardRepository;
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.stolenCardService = stolenCardService;
    }

    public ResponseEntity<ResultDTO> checkTransactionValidity(TransactionDTO transaction) {
        TransactionActions transactionActions = TransactionActions.PROHIBITED;
        StringBuilder info = new StringBuilder();
        Integer amount = transaction.getAmount();
        HttpStatus status = HttpStatus.OK;
        List<String> errors = new LinkedList<>();

        if (stolenCardService.isCardInvalid(transaction.getNumber())) {
            errors.add("card-number");
            status = HttpStatus.BAD_REQUEST;
        } else if (stolenCardRepository.findStolenCardByNumber(transaction.getNumber()).isPresent()) {
            errors.add("card-number");
        }

        if (suspiciousIpRepository.findSuspiciousIPByIp(transaction.getIp()).isPresent()) {
            errors.add("ip");
        }

        if (amount == null || amount < 1 ) {
            errors.add("amount");
            status = HttpStatus.BAD_REQUEST;
        } else if (amount > 1500) {
            errors.add("amount");
        } else if (amount > 200 && errors.size() < 1) {
            transactionActions = TransactionActions.MANUAL_PROCESSING;
            errors.add("amount");
        } else if (errors.size() < 1){
            transactionActions = TransactionActions.ALLOWED;
            errors.add("none");
        }

        errors.sort((String::compareToIgnoreCase));
        info.append(errors.get(0));
        for (int i = 1; i < errors.size(); i++) {
            info.append(", ").append(errors.get(i));
        }

        return new ResponseEntity<>(new ResultDTO(transactionActions, info.toString()), status);
    }
}

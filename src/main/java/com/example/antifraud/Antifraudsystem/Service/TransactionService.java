package com.example.antifraud.Antifraudsystem.Service;

import com.example.antifraud.Antifraudsystem.DTO.ResultDTO;
import com.example.antifraud.Antifraudsystem.DTO.TransactionDTO;
import com.example.antifraud.Antifraudsystem.Enum.TransactionAction;
import com.example.antifraud.Antifraudsystem.Model.Transaction;
import com.example.antifraud.Antifraudsystem.Repository.StolenCardRepository;
import com.example.antifraud.Antifraudsystem.Repository.SuspiciousIpRepository;
import com.example.antifraud.Antifraudsystem.Repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TransactionService {

    private final Integer MAX_AMOUNT_FOR_ALLOWED = 200;
    private final Integer MAX_AMOUNT_FOR_MANUAL_PROCESSING = 1500;

    private final StolenCardRepository stolenCardRepository;
    private final SuspiciousIpRepository suspiciousIpRepository;
    private final StolenCardService stolenCardService;
    private final TransactionRepository transactionRepository;

    public TransactionService(StolenCardRepository stolenCardRepository,
                              SuspiciousIpRepository suspiciousIpRepository, StolenCardService stolenCardService, TransactionRepository transactionRepository) {

        this.stolenCardRepository = stolenCardRepository;
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.stolenCardService = stolenCardService;
        this.transactionRepository = transactionRepository;
    }

    private HttpStatus status;
    private TransactionAction transactionAction;
    private List<String> errors;


    public ResponseEntity<ResultDTO> checkTransactionValidity(TransactionDTO transaction) {
        transactionRepository.save(new Transaction(transaction));
        status = HttpStatus.OK;
        transactionAction = TransactionAction.PROHIBITED;
        errors = new LinkedList<>();

        addErrorIfCardInvalid(transaction.getNumber());
        addErrorIfIpInvalid(transaction.getIp());
        addErrorIfAmountInvalid(transaction.getAmount());
        return new ResponseEntity<>(new ResultDTO(transactionAction, getErrorInfo()), status);
    }

    private void addErrorIfCardInvalid(String number) {
        if (stolenCardService.isCardInvalid(number)) {
            errors.add("card-number");
            status = HttpStatus.BAD_REQUEST;
        } else if (stolenCardRepository.findStolenCardByNumber(number).isPresent()) {
            errors.add("card-number");
        }
    }

    private void addErrorIfIpInvalid(String ip) {
        if (suspiciousIpRepository.findSuspiciousIPByIp(ip).isPresent()) {
            errors.add("ip");
        }
    }

    private void addErrorIfAmountInvalid(Long amount) {
        if (amount == null || amount < 1 ) {
            errors.add("amount");
            status = HttpStatus.BAD_REQUEST;
        } else if (amount > MAX_AMOUNT_FOR_MANUAL_PROCESSING) {
            errors.add("amount");
        } else if (amount > MAX_AMOUNT_FOR_ALLOWED && errors.size() < 1) {
            transactionAction = TransactionAction.MANUAL_PROCESSING;
            errors.add("amount");
        } else if (errors.size() < 1){
            transactionAction = TransactionAction.ALLOWED;
            errors.add("none");
        }
    }

    private String getErrorInfo() {
        StringBuilder info = new StringBuilder();
        errors.sort((String::compareToIgnoreCase));
        info.append(errors.get(0));
        for (int i = 1; i < errors.size(); i++) {
            info.append(", ").append(errors.get(i));
        }
        return info.toString();
    }
}


package com.example.antifraud.Antifraudsystem.Service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.antifraud.Antifraudsystem.DTO.StatusDTO;
import com.example.antifraud.Antifraudsystem.Exception.BadRequestException;
import com.example.antifraud.Antifraudsystem.Exception.EntityAlreadyExistsException;
import com.example.antifraud.Antifraudsystem.Exception.ForbiddenRequestException;
import com.example.antifraud.Antifraudsystem.Exception.NotFoundException;
import com.example.antifraud.Antifraudsystem.Model.StolenCard;
import com.example.antifraud.Antifraudsystem.Repository.StolenCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StolenCardService {

    private final StolenCardRepository stolenCardRepository;

    public StolenCardService(StolenCardRepository stolenCardRepository) {
        this.stolenCardRepository = stolenCardRepository;
    }

    public StolenCard addNewCard(StolenCard card) throws BadRequestException, EntityAlreadyExistsException {
        if (isCardInvalid(card.getNumber())) {
            throw new BadRequestException();
        }
        if (stolenCardRepository.findStolenCardByNumber(card.getNumber()).isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        stolenCardRepository.save(card);
        return stolenCardRepository.findStolenCardByNumber(card.getNumber()).get();
    }

    public List<StolenCard> showAllCards() {
        return stolenCardRepository.findAll();
    }

    public StatusDTO deleteCard(String number) {
        if (isCardInvalid(number)) {
            throw new BadRequestException();
        }
        Optional<StolenCard> card = stolenCardRepository.findStolenCardByNumber(number);
        if (card.isEmpty()) {
            throw new NotFoundException();
        }
        stolenCardRepository.deleteById(card.get().getId());
        return new StatusDTO(String.format("Card %s successfully removed!", card.get().getNumber()));
    }

    public boolean isCardInvalid(String number) {
        int[] array = convertToIntArray(number);
        int checksum = array[array.length - 1];
        array[array.length - 1] = 0;
        multiplyOddNumbersByTwo(array);
        subtractNineFromNumbersOverNine(array);
        int sum = addAllNumbers(array);
        return (sum + checksum) % 10 != 0;

    }

    private int addAllNumbers(int[] array) {
        int sum = 0;
        for (int i: array) {
            sum += i;
        }
        return sum;
    }

    private void subtractNineFromNumbersOverNine(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 9) {
                array[i] -= 9;
            }
        }
    }

    private void multiplyOddNumbersByTwo(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if ((i+1) % 2 != 0) {
                array[i] *= 2;
            }
        }
    }

    private int[] convertToIntArray(String number) {
        int[] convertedIntArray = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            convertedIntArray[i] = number.charAt(i) - 48;
        }
        return convertedIntArray;
    }
}

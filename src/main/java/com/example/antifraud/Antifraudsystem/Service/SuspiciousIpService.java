package com.example.antifraud.Antifraudsystem.Service;

import com.example.antifraud.Antifraudsystem.DTO.StatusDTO;
import com.example.antifraud.Antifraudsystem.Exception.BadRequestException;
import com.example.antifraud.Antifraudsystem.Exception.EntityAlreadyExistsException;
import com.example.antifraud.Antifraudsystem.Exception.NotFoundException;
import com.example.antifraud.Antifraudsystem.Model.SuspiciousIP;
import com.example.antifraud.Antifraudsystem.Repository.SuspiciousIpRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuspiciousIpService {

    private final SuspiciousIpRepository suspiciousIpRepository;

    public SuspiciousIpService(SuspiciousIpRepository suspiciousIpRepository) {
        this.suspiciousIpRepository = suspiciousIpRepository;
    }

    public SuspiciousIP addSuspiciousIP(SuspiciousIP ip) {
        if (isInvalidIp(ip.getIp())) {
            throw new BadRequestException();
        }
        if (suspiciousIpRepository.findSuspiciousIPByIp(ip.getIp()).isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        suspiciousIpRepository.save(ip);
        return suspiciousIpRepository.findSuspiciousIPByIp(ip.getIp()).get();
    }

    private boolean isInvalidIp(String ip) {
        String[] ipSplitByDot = ip.split("\\.");
        if (ipSplitByDot.length != 4) {
            return true;
        }
        try {
            for (String s : ipSplitByDot) {
                int i = Integer.parseInt(s);
                if (i < 0 || i > 255) {
                    return true;
                }
            }
        } catch (NumberFormatException nfe) {
            System.err.println(nfe.getMessage());
            return true;
        }
        return false;
    }

    public List<SuspiciousIP> showAllSuspiciousIps() {
        return suspiciousIpRepository.findAll();
    }

    public StatusDTO deleteSuspiciousIp(String ip) {
        if (isInvalidIp(ip)) {
            throw new BadRequestException();
        }
        Optional<SuspiciousIP> suspiciousIPOptional = suspiciousIpRepository.findSuspiciousIPByIp(ip);
        if (suspiciousIPOptional.isEmpty()) {
            throw new NotFoundException();
        }
        suspiciousIpRepository.delete(suspiciousIPOptional.get());
        return new StatusDTO(String.format("IP %s successfully removed!", ip));
    }
}
package com.example.antifraud.Antifraudsystem.Service;

import com.example.antifraud.Antifraudsystem.DTO.IpDTO;
import com.example.antifraud.Antifraudsystem.DTO.StatusDTO;
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

    public SuspiciousIP addSuspiciousIP(IpDTO ipDTO) {
        suspiciousIpRepository.save(createNewSuspiciousIPWithProvidedIp(ipDTO));
        Optional<SuspiciousIP> savedIP = suspiciousIpRepository.findSuspiciousIPByIp(ipDTO.getIp());
        assert savedIP.isPresent();
        return savedIP.get();
    }

    public SuspiciousIP createNewSuspiciousIPWithProvidedIp(IpDTO ipDTO) {
        SuspiciousIP suspiciousIP = new SuspiciousIP();
        suspiciousIP.setIp(ipDTO.getIp());
        return suspiciousIP;
    }

    public List<SuspiciousIP> showAllSuspiciousIps() {
        return suspiciousIpRepository.findAll();
    }

    public StatusDTO deleteSuspiciousIp(String ip) {
        Optional<SuspiciousIP> suspiciousIPOptional = suspiciousIpRepository.findSuspiciousIPByIp(ip);
        if (suspiciousIPOptional.isEmpty()) {
            throw new NotFoundException();
        }
        suspiciousIpRepository.delete(suspiciousIPOptional.get());
        return new StatusDTO(String.format("Ip %s successfully removed!", ip));
    }
}
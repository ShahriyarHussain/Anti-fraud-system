package com.example.antifraud.Antifraudsystem.Repository;

import com.example.antifraud.Antifraudsystem.Model.SuspiciousIP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SuspiciousIpRepository extends JpaRepository<SuspiciousIP, Long> {

    Optional<SuspiciousIP> findSuspiciousIPByIp(String ip);

}

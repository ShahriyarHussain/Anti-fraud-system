package com.example.antifraud.Antifraudsystem.Service;

import com.example.antifraud.Antifraudsystem.Model.UserEntity;
import com.example.antifraud.Antifraudsystem.Model.UserEntityDetails;
import com.example.antifraud.Antifraudsystem.Repository.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public UserEntityDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userEntityRepository.findUserEntityByUsernameIgnoreCase(username);
        return userEntity.map(UserEntityDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }
}
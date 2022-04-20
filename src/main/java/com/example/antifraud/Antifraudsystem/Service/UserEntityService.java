package com.example.antifraud.Antifraudsystem.Service;

import com.example.antifraud.Antifraudsystem.DTO.OperationDTO;
import com.example.antifraud.Antifraudsystem.DTO.RoleDTO;
import com.example.antifraud.Antifraudsystem.DTO.UserEntityDTO;
import com.example.antifraud.Antifraudsystem.Enum.UserRoles;
import com.example.antifraud.Antifraudsystem.Exception.*;
import com.example.antifraud.Antifraudsystem.Model.UserEntity;
import com.example.antifraud.Antifraudsystem.Repository.UserEntityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntityService(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntityDTO registerUser(UserEntity user) {
        if (userEntityRepository.findUserEntityByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            throw new UserAlreadyExists();
        }
        setUserRole(user);
        setUserPassword(user);
        userEntityRepository.save(user);
        UserEntity savedUser = userEntityRepository.findUserEntityByUsernameIgnoreCase(user.getUsername()).get();
        return new UserEntityDTO(savedUser);
    }

    public void setUserRole(UserEntity user) {
        if (userEntityRepository.findAll().size() == 0) {
            user.setRole(UserRoles.ADMINISTRATOR);
            user.setAccountNonLocked(true);
        } else {
            user.setRole(UserRoles.MERCHANT);
        }
    }

    public void setUserPassword(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }


    public Map<String, String> deleteUser(String username) {
        Optional<UserEntity> user = userEntityRepository.findUserEntityByUsernameIgnoreCase(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        userEntityRepository.delete(user.get());
        return Map.of("username", username, "status", "Deleted successfully!");
    }


    public UserEntityDTO setAccountRole(RoleDTO role) {
        UserEntity user = userEntityRepository.findUserEntityByUsernameIgnoreCase(role.getUsername())
                .orElseThrow(UserNotFoundException::new);
        UserRoles providedRole = getProvidedRole(role.getRole());
        if (user.getRole() == providedRole) {
            throw new RoleAlreadyExistsException();
        }
        user.setRole(providedRole);
        userEntityRepository.save(user);
        return new UserEntityDTO(user);
    }

    public UserRoles getProvidedRole(String role) throws RoleNotFoundException {
        UserRoles providedRole;
        try {
            providedRole = UserRoles.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException();
        }
        if (providedRole == UserRoles.MERCHANT || providedRole == UserRoles.SUPPORT) {
            return providedRole;
        }
        throw new RoleNotFoundException();
    }


    public Map<String, String> setAccountAccess(OperationDTO operation) {
        UserEntity user = userEntityRepository.findUserEntityByUsernameIgnoreCase(operation.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (user.getRole() == UserRoles.ADMINISTRATOR) {
            throw new BadRequestException();
        }
        userEntityRepository.save(lockOrUnlockUser(operation.getOperation(), user));
        return Map.of("status", String.format(
                "User %s %sed!", operation.getUsername(), operation.getOperation().toLowerCase(Locale.ROOT)));
    }

    public UserEntity lockOrUnlockUser(String operation, UserEntity user) {
        if (operation.equals("LOCK")) {
            user.setAccountNonLocked(false);
        } else if (operation.equals("UNLOCK")) {
            user.setAccountNonLocked(true);
        } else {
            throw new BadRequestException();
        }
        return user;
    }


    public List<UserEntityDTO> showAllUsers() {
        return userEntityRepository.findAll().stream().map(UserEntityDTO::new).collect(Collectors.toList());
    }
}

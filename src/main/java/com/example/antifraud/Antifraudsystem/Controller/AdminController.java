package com.example.antifraud.Antifraudsystem.Controller;

import com.example.antifraud.Antifraudsystem.DTO.OperationDTO;
import com.example.antifraud.Antifraudsystem.DTO.RoleDTO;
import com.example.antifraud.Antifraudsystem.DTO.UserEntityDTO;
import com.example.antifraud.Antifraudsystem.Service.UserEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AdminController {

    private final UserEntityService userEntityService;

    public AdminController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/list")
    public List<UserEntityDTO> showAllUsers() {
        return userEntityService.showAllUsers();
    }

    @PutMapping("/role")
    public UserEntityDTO setAccountRole(@RequestBody RoleDTO roleDTO) {
        return userEntityService.setAccountRole(roleDTO);
    }

    @PutMapping("/access")
    public Map<String, String> setAccountAccess(@RequestBody OperationDTO operation) {
        return userEntityService.setAccountAccess(operation);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        return ResponseEntity.ok(userEntityService.deleteUser(username));
    }
}

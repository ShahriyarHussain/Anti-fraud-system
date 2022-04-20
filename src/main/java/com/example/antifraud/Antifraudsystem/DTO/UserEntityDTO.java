package com.example.antifraud.Antifraudsystem.DTO;

import com.example.antifraud.Antifraudsystem.Enum.UserRoles;
import com.example.antifraud.Antifraudsystem.Model.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserEntityDTO {

    private Long id;
    private String name;
    private String username;
    private UserRoles role;

    public UserEntityDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

}

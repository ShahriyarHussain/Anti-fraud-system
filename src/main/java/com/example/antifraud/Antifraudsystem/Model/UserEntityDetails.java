package com.example.antifraud.Antifraudsystem.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UserEntityDetails implements UserDetails {

    private final String username;
    private final String password;
    private final boolean isAccountNonLocked;
    private final List<GrantedAuthority> grantedAuthorities;

    public UserEntityDetails(UserEntity user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

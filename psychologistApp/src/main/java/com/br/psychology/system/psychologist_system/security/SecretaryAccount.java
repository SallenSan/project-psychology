package com.br.psychology.system.psychologist_system.security;

import com.br.psychology.system.psychologist_system.models.Secretary;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class SecretaryAccount implements UserDetails {

    private Secretary secretary;

    public SecretaryAccount(Secretary secretary) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(secretary.getRole());
        return Arrays.asList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return secretary.getPassword();
    }

    @Override
    public String getUsername() {
        return secretary.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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

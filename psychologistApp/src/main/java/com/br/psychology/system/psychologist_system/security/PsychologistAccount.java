package com.br.psychology.system.psychologist_system.security;

import com.br.psychology.system.psychologist_system.models.Psychologist;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class PsychologistAccount implements UserDetails {
    private Psychologist psychologist;

    public PsychologistAccount(Psychologist psychologist){
        this.psychologist = psychologist;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(psychologist.getRole());
        return Arrays.asList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return psychologist.getPassword();
    }

    @Override
    public String getUsername() {
        return psychologist.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
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

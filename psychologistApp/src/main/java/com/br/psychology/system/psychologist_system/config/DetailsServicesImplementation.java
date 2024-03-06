package com.br.psychology.system.psychologist_system.config;

import com.br.psychology.system.psychologist_system.models.Psychologist;
import com.br.psychology.system.psychologist_system.models.Secretary;
import com.br.psychology.system.psychologist_system.repositories.PsychologistRepository;
import com.br.psychology.system.psychologist_system.repositories.SecretaryRepository;
import com.br.psychology.system.psychologist_system.security.PsychologistAccount;
import com.br.psychology.system.psychologist_system.security.SecretaryAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailsServicesImplementation implements UserDetailsService{

    @Autowired
    private PsychologistRepository psychologistRepository;
    @Autowired
    private SecretaryRepository secretaryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Psychologist psychologist = psychologistRepository.findByEmail(email);
        Secretary secretary = secretaryRepository.findByEmail(email);

        if(psychologist != null){
            return new PsychologistAccount(psychologist);
        } else if (secretary != null) {
            return new SecretaryAccount(secretary);
        }
        throw new UsernameNotFoundException("O e-mail não foi encontrado!");
    }
}


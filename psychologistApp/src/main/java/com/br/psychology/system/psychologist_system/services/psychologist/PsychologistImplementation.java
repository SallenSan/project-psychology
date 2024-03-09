package com.br.psychology.system.psychologist_system.services.psychologist;

import com.br.psychology.system.psychologist_system.models.Psychologist;
import com.br.psychology.system.psychologist_system.repositories.PsychologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PsychologistImplementation implements PsychologistService{

    @Autowired
    private PsychologistRepository psychologistRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Psychologist createUser(Psychologist psychologist) {

        psychologist.setPassword(passwordEncoder.encode(psychologist.getPassword()));
        psychologist.setRole("ROLE_ADMIN");

        return psychologistRepository.save(psychologist);
    }

    @Override
    public boolean checkEmail(String email) {
        return psychologistRepository.existsByEmail(email);
    }
}

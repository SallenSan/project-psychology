package com.br.psychology.system.psychologist_system.services.secretary;

import com.br.psychology.system.psychologist_system.models.Secretary;
import com.br.psychology.system.psychologist_system.repositories.SecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SecretaryImplementation implements SecretaryService {

    @Autowired
    private SecretaryRepository secretaryRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Secretary createUser(Secretary secretary) {
        secretary.setPassword(passwordEncoder.encode(secretary.getPassword()));
        secretary.setRole("ROLE_USER");
        return secretaryRepository.save(secretary);
    }

    @Override
    public boolean checkEmail(String email, Secretary secretary) {
        Secretary existingSecretary = secretaryRepository.findByEmail(email);
        return existingSecretary != null && !existingSecretary.equals(secretary);
    }

    @Override
    public Secretary getSecretaryById(Long id) {
        return secretaryRepository.findById(id).orElse(null);
    }
}

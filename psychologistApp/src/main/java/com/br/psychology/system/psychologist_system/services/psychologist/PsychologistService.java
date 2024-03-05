package com.br.psychology.system.psychologist_system.services.psychologist;

import com.br.psychology.system.psychologist_system.models.Psychologist;

public interface PsychologistService {
    public Psychologist createUser(Psychologist user);
    public boolean checkEmail(String email);
}

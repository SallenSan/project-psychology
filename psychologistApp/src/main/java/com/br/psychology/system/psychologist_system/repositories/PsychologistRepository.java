package com.br.psychology.system.psychologist_system.repositories;

import com.br.psychology.system.psychologist_system.models.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsychologistRepository extends JpaRepository<Psychologist, Long> {

    public boolean existsByEmail(String email);
    public Psychologist findByEmail(String email);
}

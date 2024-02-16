package com.br.psychology.system.psychologist_system.repositories;

import com.br.psychology.system.psychologist_system.models.Appointment;
import com.br.psychology.system.psychologist_system.models.Psychologist;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // Indica que esta interface é um repositório Spring
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  // Método para verificar se já existe um agendamento com a mesma data e hora
  // para um psicólogo diferente do fornecido e com um ID diferente
  boolean existsByDateHourAndPsychologistIdNot(LocalDateTime dateTime, Psychologist psychologist, Long id);
}

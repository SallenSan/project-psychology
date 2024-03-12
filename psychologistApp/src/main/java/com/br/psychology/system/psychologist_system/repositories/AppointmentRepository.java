package com.br.psychology.system.psychologist_system.repositories;

import com.br.psychology.system.psychologist_system.models.Appointment;
import com.br.psychology.system.psychologist_system.models.Patient;
import com.br.psychology.system.psychologist_system.models.Psychologist;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // Indica que esta interface é um repositório Spring
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  List<Appointment> findAllByPatient(Patient patient);
}

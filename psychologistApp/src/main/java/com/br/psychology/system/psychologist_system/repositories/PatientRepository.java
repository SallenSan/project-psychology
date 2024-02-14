package com.br.psychology.system.psychologist_system.repositories;

import com.br.psychology.system.psychologist_system.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Paciente p WHERE UPPER(p.name) LIKE CONCAT('%', UPPER(:name), '%')")
    List<Patient> findByName(@Param("name") String name);
}

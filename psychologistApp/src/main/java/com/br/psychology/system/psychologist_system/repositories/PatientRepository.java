package com.br.psychology.system.psychologist_system.repositories; // Pacote onde está localizada a interface de repositório

import com.br.psychology.system.psychologist_system.models.Patient; // Importação da entidade Patient
import org.springframework.data.jpa.repository.JpaRepository; // Importação do JpaRepository
import org.springframework.data.jpa.repository.Query; // Importação da anotação Query
import org.springframework.data.repository.query.Param; // Importação da anotação Param
import org.springframework.stereotype.Repository; // Importação da anotação Repository

import java.util.List; // Importação da classe List

@Repository // Marca esta interface como um repositório Spring
public interface PatientRepository extends JpaRepository<Patient, Long> { // Interface de repositório que estende JpaRepository

    // Consulta personalizada para buscar pacientes por nome
    @Query("SELECT p FROM Patient p WHERE UPPER(p.name) LIKE CONCAT('%', UPPER(:name), '%')")
    List<Patient> findByName(@Param("name") String name); // Método que retorna uma lista de pacientes com base no nome

}

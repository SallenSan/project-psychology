package com.br.psychology.system.psychologist_system.repositories;

import com.br.psychology.system.psychologist_system.models.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
    public boolean existsByEmail(String email);
    public Secretary findByEmail(String email);


    @Query("SELECT s FROM Secretary s WHERE UPPER(s.name) LIKE CONCAT('%', UPPER(:name), '%')")
    List<Secretary> findByName(String name);
}

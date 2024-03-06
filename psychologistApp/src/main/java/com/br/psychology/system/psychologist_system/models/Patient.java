package com.br.psychology.system.psychologist_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;


public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cpf", length = 11)
    @NotNull
    private String cpf;

    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> consultasAnteriores;



}
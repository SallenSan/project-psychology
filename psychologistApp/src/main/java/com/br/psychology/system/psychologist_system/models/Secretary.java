package com.br.psychology.system.psychologist_system.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Table(name = "secretaries") // Define o nome da tabela no banco de dados
@Entity // Indica que esta classe é uma entidade JPA
public class Secretary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chave primária da entidade

    @Column(nullable = false)
    private String name; // Nome do secretário

    @Column(nullable = false)
    private String email; // E-mail do secretário

    @Column(nullable = false)
    private String password; // Senha do secretário
    private String role;
}

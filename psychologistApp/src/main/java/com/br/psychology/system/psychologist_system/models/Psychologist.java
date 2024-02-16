package com.br.psychology.system.psychologist_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotação do Lombok para gerar automaticamente getters, setters, toString, equals e hashCode
@Entity // Indica que esta classe é uma entidade JPA
@NoArgsConstructor // Cria um construtor padrão sem argumentos
@AllArgsConstructor // Cria um construtor que inicializa todos os campos da classe
public class Psychologist {

    @Id // Indica que esta propriedade é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração de valor para a chave primária
    private Long id; // Chave primária da entidade

    @Column(length = 4, nullable = false) // Define a configuração da coluna no banco de dados
    private int crp; // Número do Conselho Regional de Psicologia

    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private String name; // Nome do psicólogo

    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private String email; // E-mail do psicólogo

    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private int phone; // Número de telefone do psicólogo

    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private String password; // Senha do psicólogo

}

package com.br.psychology.system.psychologist_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Gera automaticamente getters, setters e toString
@AllArgsConstructor // Cria um construtor com todos os argumentos
@NoArgsConstructor // Cria um construtor vazio
@Builder // Auxilia na construção de objetos
@Entity // Indica que essa classe é uma entidade JPA
public class Patient {

    @Id // Indica que esta propriedade é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração de valor para a chave primária
    private Long id; // Chave primária da entidade

    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private String name; // Nome do paciente

    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private String email; // E-mail do paciente

    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private String phone; // Número de telefone do paciente

    private String symptoms; // Sintomas do paciente

}

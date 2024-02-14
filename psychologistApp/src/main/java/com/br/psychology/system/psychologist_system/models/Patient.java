package com.br.psychology.system.psychologist_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //para criar os getters e setters e o tostring
@AllArgsConstructor //para criar construtor com as propriedades da entidade
@NoArgsConstructor //para criar um construtor vazio
@Builder //para ajudar na criação de objetos cliente
@Entity // para informar que essa classe trata-se de uma entidade
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    private String symptoms;
}

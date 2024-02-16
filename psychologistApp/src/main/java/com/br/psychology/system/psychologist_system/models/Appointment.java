package com.br.psychology.system.psychologist_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // Gera automaticamente getters, setters, toString, equals e hashCode
@NoArgsConstructor // Cria um construtor vazio
@AllArgsConstructor // Cria um construtor com todos os argumentos
@Entity // Indica que essa classe é uma entidade JPA
public class Appointment {

    @Id // Indica que esta propriedade é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração de valor para a chave primária
    private Long id; // Chave primária da entidade

    @ManyToOne // Define um relacionamento muitos-para-um com a entidade Psychologist
    @JoinColumn(name = "psychologist_id", nullable = false) // Define a coluna de junção e que não pode ser nula
    private Psychologist psychologist; // O psicólogo associado ao agendamento

    @ManyToOne // Define um relacionamento muitos-para-um com a entidade Patient
    @JoinColumn(name = "patient_id", nullable = false) // Define a coluna de junção e que não pode ser nula
    private Patient patient; // O paciente associado ao agendamento

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") // Formato do padrão de data/hora para a entrada do usuário
    @Column(nullable = false) // Define que este campo não pode ser nulo no banco de dados
    private LocalDateTime dateTime; // Data e hora do agendamento
}

package br.dev.mozart.trabalho2.modelo;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String nome;
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @Column
    private String cep;
    @Column
    private String interesses;
    @Column
    private Boolean fidelidade;
}

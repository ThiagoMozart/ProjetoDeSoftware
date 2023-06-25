package br.dev.mozart.trabalho2.modelo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="exemplar")
@DynamicInsert
@DynamicUpdate
public class Exemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long codigo;
    @ManyToOne
    @JoinColumn
    private Quadrinhos quadrinhos;
    @Column
    private String dataAquisicao;
    @Column
    private String editora;
    @Column
    private String dataPublicacao;
    @Column
    private String estadoConservacao;
    @Column
    private String tipoCapa;
    @Column
    private String edicao;
    @Version
    private Integer versao;
}

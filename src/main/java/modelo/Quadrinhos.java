package modelo;

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
@Table(name="quadrinhos")
@DynamicInsert
@DynamicUpdate
public class Quadrinhos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long codigo;
    @Column
    private String autor;
    @Column
    private String titulo;
    @Column
    private String tema;
    @Column
    private Integer primeiraEdicao;
    @Version
    private Integer versao;
}
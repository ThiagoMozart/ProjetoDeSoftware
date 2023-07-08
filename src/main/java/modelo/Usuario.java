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
@Table(name="usuario")

@NamedQueries({
        @NamedQuery(name = "Usuario.recuperaUmUsuario", query = "select u from Usuario u where u.id = ?1"),
        @NamedQuery(name = "Usuario.recuperaUsuarios", query = "select u from Usuario u order by u.id") })

@DynamicInsert
@DynamicUpdate
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
    @Version
    private Integer versao;
}
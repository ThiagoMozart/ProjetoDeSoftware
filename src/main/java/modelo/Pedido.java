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
@Table(name="pedido")
@DynamicInsert
@DynamicUpdate

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long codigo;
    @Column
    private String dataPedido;
    @Column
    private String dataEntrega;
    @Version
    private Integer versao;
}

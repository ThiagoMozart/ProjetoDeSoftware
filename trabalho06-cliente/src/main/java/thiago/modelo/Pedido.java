package thiago.modelo;

import javax.persistence.*;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column
    private String dataPedido;
    @Column
    private String dataEntrega;
    @Column
    private Integer qtdItens;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    public Pedido(String dataPedido, String dataEntrega, Integer qtdItens){
        this.dataPedido = dataPedido;
        this.dataEntrega = dataEntrega;
        this.qtdItens = qtdItens;
    }
}
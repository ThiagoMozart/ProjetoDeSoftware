package thiago.modelo;

import javax.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Pedido> pedidos;


    public Usuario(String nome, String cpf, List<Pedido> pedidos) {
        this.nome = nome;
        this.cpf = cpf;
        this.pedidos = pedidos;
    }

    public void addPedido(Pedido pedido) {
        if (this.pedidos == null) {
            this.pedidos = new ArrayList<>();
        }
        this.pedidos.add(pedido);
    }
}
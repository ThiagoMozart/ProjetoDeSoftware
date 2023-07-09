package modelo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@NoArgsConstructor
@Entity

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
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("nome asc")
    private List<Pedido> pedidos;


    public void addPedido(Pedido pedido) {
        if (this.pedidos == null) {
            this.pedidos = new ArrayList<>();
        }
        this.pedidos.add(pedido);
    }
}
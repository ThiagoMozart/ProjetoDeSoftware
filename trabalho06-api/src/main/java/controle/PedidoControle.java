package controle;


import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("pedidos/")
public class PedidoControle {

    private final PedidoServico pedidoServico;

    public PedidoControle(PedidoServico pedidoServico) {
        this.pedidoServico = pedidoServico;
    }

    @PutMapping
    public Pedido atualizarPedido(@RequestBody Pedido pedido) {
        return pedidoServico.atualizarPedido(pedido);
    }

    @PostMapping
    public Pedido cadastraPedido(@RequestBody Pedido pedido) {
        return pedidoServico.cadastraPedido(pedido);
    }

    @DeleteMapping("{idPedido}")
    public void removePedido(@PathVariable("idPedido") Long id) {
        pedidoServico.removePedido(id);
    }

    @GetMapping
    public List<Pedido> recuperarFuncoes() {
        return pedidoServico.recuperarFuncoes();
    }

    @GetMapping("{idPedido}")
    private Pedido recuperaPedidoPorId(@PathVariable("idPedido") Long id) {
        return pedidoServico.recuperaPedidosPorId(id);
    }
}
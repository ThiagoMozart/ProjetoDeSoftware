package thiago.servico;

import thiago.modelo.Pedido;
import thiago.excecao.EntidadeNaoEncontradaException;
import thiago.repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServico {

    private final PedidoRepositorio pedidoRepositorio;

    @Autowired
    public PedidoServico(PedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    public Pedido atualizaPedido(Pedido pedido) {
        return pedidoRepositorio.save(pedido);
    }

    public Pedido cadastraPedido(Pedido pedido) {
        return pedidoRepositorio.save(pedido);
    }

    public void removePedido(Long id) {
        recuperaPedidosPorId(id);
        pedidoRepositorio.deleteById(id);
    }

    public List<Pedido> recuperaPedidos() {
        return pedidoRepositorio.findAll(Sort.by("codigo"));
    }

    public Pedido recuperaPedidosPorId(Long id) {
        return pedidoRepositorio.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Pedido de ID " + id + " não encontrado.")
        );
    }
}
package br.dev.mozart.trabalho2.dao;

import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.PedidoNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Pedido;

import java.util.List;

public interface PedidoDAO {
    Long inclui(Pedido umPedido) throws PedidoNaoEncontradoException;
    void altera(Pedido umPedido) throws PedidoNaoEncontradoException, EntidadeDesatualizadaException;
    void exclui(Long id) throws PedidoNaoEncontradoException;
    Pedido recuperaUmPedido(Long numero) throws PedidoNaoEncontradoException;
    List<Pedido> recuperaPedidos();
}

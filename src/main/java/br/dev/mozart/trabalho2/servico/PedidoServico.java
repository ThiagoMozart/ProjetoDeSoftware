package br.dev.mozart.trabalho2.servico;

import br.dev.mozart.trabalho2.dao.PedidoDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.PedidoNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Pedido;
import br.dev.mozart.trabalho2.util.FabricaDeDAOs;
import br.dev.mozart.trabalho2.util.JPAUtil;

import java.util.List;

public class PedidoServico {

    private static PedidoDAO pedidoDAO = FabricaDeDAOs.getDAO(PedidoDAO.class);

    public Long inclui(Pedido umPedido) throws PedidoNaoEncontradoException {
        JPAUtil.beginTransaction();
        Long id = pedidoDAO.inclui(umPedido);
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();

        return id;
    }

    public Pedido alteraPedido(Pedido pedido) throws PedidoNaoEncontradoException {
        JPAUtil.beginTransaction();
        try {
            pedidoDAO.altera(pedido);
        } catch (PedidoNaoEncontradoException | EntidadeDesatualizadaException e) {
            JPAUtil.rollbackTransaction();
            throw new PedidoNaoEncontradoException(e.getMessage());
        }
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
        return pedidoDAO.recuperaUmPedido(pedido.getCodigo());
    }

    public void excluiPedido(Long numero) throws PedidoNaoEncontradoException {

        JPAUtil.beginTransaction();
        pedidoDAO.exclui(numero);
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
    }

    public List<Pedido> recuperaPedidos() {
        return pedidoDAO.recuperaPedidos();
    }
}


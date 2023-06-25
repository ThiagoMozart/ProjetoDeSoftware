package br.dev.mozart.trabalho2.dao.impl;

import br.dev.mozart.trabalho2.dao.PedidoDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.PedidoNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Pedido;
import br.dev.mozart.trabalho2.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;

import java.util.List;

public class JPAPedidoDAO implements PedidoDAO {
    public Long inclui(Pedido umPedido) throws PedidoNaoEncontradoException {
        try {
            EntityManager en = JPAUtil.getEntityManager();
            en.persist(umPedido);

            return umPedido.getCodigo();
        } catch (RuntimeException e) {
            throw new PedidoNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public void altera(Pedido umPedido) throws PedidoNaoEncontradoException, EntidadeDesatualizadaException {
        Pedido pedido = null;
        try {
            EntityManager em = JPAUtil.getEntityManager();
            pedido = em.find(Pedido.class, umPedido.getCodigo(), LockModeType.PESSIMISTIC_WRITE);

            if (pedido == null) {
                throw new PedidoNaoEncontradoException("Pedido não encontrado");
            }
            em.merge(umPedido);
        } catch (OptimisticLockException e) {
            throw new EntidadeDesatualizadaException("Esse pedido já foi atualizado por outra pessoa");
        }
        catch (RuntimeException e) {
            throw new PedidoNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public void exclui(Long id) throws PedidoNaoEncontradoException {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Pedido pedido = em.find(Pedido.class, id);

            if (pedido == null) {
                throw new PedidoNaoEncontradoException("Pedido não encontrado");
            }
            em.remove(pedido);
        } catch (RuntimeException e) {
            throw new PedidoNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public Pedido recuperaUmPedido(Long numero) throws PedidoNaoEncontradoException {
        EntityManager em = JPAUtil.getEntityManager();
        Pedido pedido = em.find(Pedido.class, numero);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException("Pedido não encontrado");
        }
        return pedido;

    }

    @Override
    public List<Pedido> recuperaPedidos() {
        EntityManager em = JPAUtil.getEntityManager();
        return em.createQuery("select p from Pedido p", Pedido.class).getResultList();
    }
}

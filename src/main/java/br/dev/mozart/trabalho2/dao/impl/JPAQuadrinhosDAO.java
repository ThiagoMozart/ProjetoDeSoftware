package br.dev.mozart.trabalho2.dao.impl;

import br.dev.mozart.trabalho2.dao.QuadrinhosDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.QuadrinhosNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Quadrinhos;
import br.dev.mozart.trabalho2.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;

import java.util.List;

public class JPAQuadrinhosDAO implements QuadrinhosDAO {

    @Override
    public Long inclui(Quadrinhos umQuadrinhos) throws QuadrinhosNaoEncontradoException {
        try {
            EntityManager en = JPAUtil.getEntityManager();
            en.persist(umQuadrinhos);
            return umQuadrinhos.getCodigo();
        } catch (RuntimeException e) {
            throw new QuadrinhosNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public void altera(Quadrinhos umQuadrinhos) throws QuadrinhosNaoEncontradoException, EntidadeDesatualizadaException {
        Quadrinhos quadrinhos = null;

        try {
            EntityManager em = JPAUtil.getEntityManager();
            quadrinhos = em.find(Quadrinhos.class, umQuadrinhos.getCodigo());

            if (quadrinhos == null) {
                throw new QuadrinhosNaoEncontradoException("Quadrinhos não encontrado");
            }
            em.merge(umQuadrinhos);
        } catch (OptimisticLockException e) {
            throw new QuadrinhosNaoEncontradoException("Esse quadrinhos já foi atualizado por outra pessoa");
        } catch (RuntimeException e) {
            throw new QuadrinhosNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public void exclui(Long id) throws QuadrinhosNaoEncontradoException {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Quadrinhos quadrinhos = em.find(Quadrinhos.class, id);

            if (quadrinhos == null) {
                throw new QuadrinhosNaoEncontradoException("Quadrinhos não encontrado");
            }
            em.remove(quadrinhos);
        } catch (RuntimeException e) {
            throw new QuadrinhosNaoEncontradoException(e.getMessage());
        }

    }

    @Override
    public Quadrinhos recuperaUmQuadrinho(Long numero) throws QuadrinhosNaoEncontradoException {
        EntityManager em = JPAUtil.getEntityManager();
        Quadrinhos umQuadrinho = em.find(Quadrinhos.class, numero);
        if (umQuadrinho == null) {
            throw new QuadrinhosNaoEncontradoException("Quadrinhos não encontrado");
        }
        return umQuadrinho;
    }

    @Override
    public List<Quadrinhos> recuperaQuadrinhos() {
        EntityManager em = JPAUtil.getEntityManager();
        return em.createQuery("select q from Quadrinhos q", Quadrinhos.class).getResultList();
    }
}

package br.dev.mozart.trabalho2.dao.impl;

import br.dev.mozart.trabalho2.dao.ExemplarDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.ExemplarNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Exemplar;
import br.dev.mozart.trabalho2.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;

import java.util.List;

public class JPAExemplarDAO implements ExemplarDAO {

    @Override
    public Long inclui(Exemplar umExemplar) throws ExemplarNaoEncontradoException {
        try {
            EntityManager en = JPAUtil.getEntityManager();
            en.persist(umExemplar);

            return umExemplar.getCodigo();
        } catch (RuntimeException e) {
            throw new ExemplarNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public void altera(Exemplar umExemplar) throws ExemplarNaoEncontradoException, EntidadeDesatualizadaException {
        Exemplar exemplar = null;

        try{
            EntityManager em = JPAUtil.getEntityManager();
            exemplar = em.find(Exemplar.class, umExemplar.getCodigo(), LockModeType.PESSIMISTIC_WRITE);

            if (exemplar == null) {
                throw new ExemplarNaoEncontradoException("Exemplar não encontrado");
            }
            em.merge(umExemplar);

        } catch (OptimisticLockException e) {
            throw new EntidadeDesatualizadaException("Esse exemplar já foi atualizado por outra pessoa");
        }
        catch (RuntimeException e) {
            throw new ExemplarNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public void exclui(Long id) throws ExemplarNaoEncontradoException {
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Exemplar exemplar = em.find(Exemplar.class, id);

            if (exemplar == null) {
                throw new ExemplarNaoEncontradoException("Exemplar não encontrado");
            }
            em.remove(exemplar);
        } catch (RuntimeException e) {
            throw new ExemplarNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public Exemplar recuperaUmExemplar(Long numero) throws ExemplarNaoEncontradoException {
        EntityManager em = JPAUtil.getEntityManager();
        Exemplar umExemplar = em.find(Exemplar.class, numero);

        if (umExemplar == null) {
            throw new ExemplarNaoEncontradoException("Exemplar não encontrado");
        }
        return umExemplar;
    }

    @Override
    public List<Exemplar> recuperaExemplares() {
        EntityManager en = JPAUtil.getEntityManager();
        return en.createQuery("select e from Exemplar e", Exemplar.class).getResultList();
    }
}

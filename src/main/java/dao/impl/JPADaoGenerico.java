package dao.impl;

import dao.DAOGenerico;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import jakarta.persistence.*;

import java.lang.reflect.Method;
import java.util.List;

public class JPADaoGenerico<T, PK> implements DAOGenerico<T, PK> {

    @PersistenceContext
    protected EntityManager em;
    private final Class<T> tipo;

    public JPADaoGenerico(Class<T> tipo) {
        this.tipo = tipo;
    }

    @Override
    public final T inclui(T umObjeto) {
        try{
            em.persist(umObjeto);
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
        return umObjeto;
    }

    @Override
    public final void altera(T umObjeto) {
        try{
            em.merge(umObjeto);
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public final void exclui(T umObjeto) {
        try{
            if (em.contains(umObjeto)) {
                em.remove(umObjeto);
            }
            else {
                umObjeto = em.merge(umObjeto);
                em.remove(umObjeto);
            }
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public final T getPorId(PK umId) throws ObjetoNaoEncontradoException {
        T umObjeto;

        try{
            umObjeto = em.find(tipo, umId);
            if (umObjeto == null) {
                throw new ObjetoNaoEncontradoException();
            }
            return umObjeto;
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public final T getPorIdComLock(PK umId) throws ObjetoNaoEncontradoException {
        T umObjeto;

        try{
            umObjeto = em.find(tipo, umId, LockModeType.PESSIMISTIC_WRITE);
            if (umObjeto == null) {
                throw new ObjetoNaoEncontradoException();
            }
            return umObjeto;
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final T busca(Method metodo, Object[] args) throws InfraestruturaException {
        T umObjeto;

        try {
            String nomeDaBusca = tipo.getSimpleName() + "." + metodo.getName();
            Query query = em.createNamedQuery(nomeDaBusca);

            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i+1, args[i]);
                }
            }
            umObjeto = (T) query.getSingleResult();

            return umObjeto;
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final List<T> buscaLista(Method metodo, Object[] args) {
        try {
            String nomeDaBusca = tipo.getSimpleName() + "." + metodo.getName();
            Query query = em.createNamedQuery(nomeDaBusca);

            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i+1, args[i]);
                }
            }
            return (List<T>) query.getResultList();
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JPADaoGenerico<?, ?> that = (JPADaoGenerico<?, ?>) o;

        return tipo.equals(that.tipo);
    }

    @Override
    public final int hashCode() {
        return tipo.hashCode();
    }
}

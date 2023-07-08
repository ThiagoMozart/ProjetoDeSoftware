package dao.impl;

import anotacao.RecuperaObjeto;
import dao.DAOGenerico;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import jakarta.persistence.*;

import java.lang.reflect.Method;
import java.util.List;

public class JPADaoGenerico<T, PK> implements DAOGenerico<T, PK> {

    @PersistenceContext
    public EntityManager em;
    private Class<T> tipo;

    public JPADaoGenerico(Class<T> tipo) {
        this.tipo = tipo;
    }

    @Override
    public T inclui(T umObjeto) {
        try{
            em.persist(umObjeto);
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
        return umObjeto;
    }

    @Override
    public void altera(T umObjeto) {
        try{
            em.merge(umObjeto);
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public void exclui(T umObjeto) {
        try{
            em.remove(umObjeto);
        } catch (RuntimeException e) {
            throw new InfraestruturaException(e);
        }
    }

    @Override
    public T getPorId(PK umId) throws ObjetoNaoEncontradoException {
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
    public T getPorIdComLock(PK umId) throws ObjetoNaoEncontradoException {
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
    @RecuperaObjeto
    public final T busca(Method metodo, Object[] args) throws InfraestruturaException {
        T umObjeto;

        try {
            String nomeDaBusca = tipo.getSimpleName() + "." + metodo.getName();
            Query query = em.createNamedQuery(nomeDaBusca);

            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    (query).setParameter(i+1, args[i]);
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
                    ((query).setParameter(i+1, args[i]);
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

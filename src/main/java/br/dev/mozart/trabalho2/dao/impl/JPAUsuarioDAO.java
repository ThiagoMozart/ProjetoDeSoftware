package br.dev.mozart.trabalho2.dao.impl;

import br.dev.mozart.trabalho2.dao.UsuarioDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.UsuarioNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Usuario;
import br.dev.mozart.trabalho2.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;

import java.util.List;

public class JPAUsuarioDAO implements UsuarioDAO {
    public Long inclui(Usuario umUsuario) throws UsuarioNaoEncontradoException {

        try {
            EntityManager em = JPAUtil.getEntityManager();
            em.persist(umUsuario);

            return umUsuario.getId();
        } catch (RuntimeException e) {
            throw new UsuarioNaoEncontradoException(e.getMessage());
        }
    }

    public Usuario recuperaUmUsuario(Long numero) throws UsuarioNaoEncontradoException {

        EntityManager em = JPAUtil.getEntityManager();
        Usuario umUsuario = em.find(Usuario.class, numero);

        // Características no método find():
        // 1. É genérico: não requer um cast.
        // 2. Retorna null caso a linha não seja encontrada no banco.

        if (umUsuario == null) {
            throw new UsuarioNaoEncontradoException("usuário não encontrado");
        }
        return umUsuario;

    }

    public void altera(Usuario umUsuario) throws UsuarioNaoEncontradoException, EntidadeDesatualizadaException {

        Usuario usuario = null;

        try {
            EntityManager em = JPAUtil.getEntityManager();
            usuario = em.find(Usuario.class, umUsuario.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (usuario == null) {
                throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
            }
            // O merge entre nada e tudo é tudo. Ao tentar alterar um produto deletado ele será re-inserido
            // no banco de dados.
            em.merge(umUsuario);

        } catch (OptimisticLockException e) {
            throw new EntidadeDesatualizadaException("Esse usuário já foi atualizado por outra pessoa");
        }
        catch (RuntimeException e) {
            throw new UsuarioNaoEncontradoException(e.getMessage());
        }
    }

    public void exclui(Long numero) throws UsuarioNaoEncontradoException {

        try {
            EntityManager em = JPAUtil.getEntityManager();

            Usuario usuario = em.find(Usuario.class, numero);

            if (usuario == null) {
                throw new UsuarioNaoEncontradoException("Usuário não encontrado");
            }

            em.remove(usuario);

        } catch (RuntimeException e) {
            throw new UsuarioNaoEncontradoException(e.getMessage());
        }
    }

    public List<Usuario> recuperaUsuarios() {

        EntityManager em = JPAUtil.getEntityManager();
            // Retorna um List vazio caso a tabela correspondente esteja vazia.

        return em
                .createQuery("select u from Usuario u order by u.id", Usuario.class)
                .getResultList();
    }
}

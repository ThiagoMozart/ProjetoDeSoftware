package br.dev.mozart.trabalho2.dao.impl;

import br.dev.mozart.trabalho2.dao.UsuarioDAO;
import br.dev.mozart.trabalho2.excecao.UsuarioNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Usuario;
import br.dev.mozart.trabalho2.util.FabricaDeEntityManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;

import java.util.List;

public class JPAUsuarioDAO implements UsuarioDAO {
    public Long inclui(Usuario umUsuario) {

        EntityTransaction tx = null;
        try (EntityManager em = FabricaDeEntityManager.criarEntityManager()) {
            // transiente - Objeto cujo valor do atributo identificador é null
            // persistente - Objeto que está sendo monitorado pelo entity manager
            // destacado - Objeto cujo valor do atributo identificador é diferente de null
            //             e não está sendo monitorado pelo entity manager.
            tx = em.getTransaction();
            tx.begin();
            // 1. Executar o comando insert
            // 2. Atribui ao id do produto o seu valor
            // 3. Acrescenta o objeto umProduto à lista de objetos monitorados do entity manager
            em.persist(umUsuario);
            // System.out.println(umProduto.getId());
            // umProduto.setNome("abc");
            tx.commit();
            return umUsuario.getId();
        } catch (RuntimeException e) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (RuntimeException ignored) {
                }
            }
            throw e;
        }
    }

    public Usuario recuperaUmUsuario(Long numero) throws UsuarioNaoEncontradoException {

        try (EntityManager em = FabricaDeEntityManager.criarEntityManager()) {
            Usuario umUsuario = em.find(Usuario.class, numero);

            // Características no método find():
            // 1. É genérico: não requer um cast.
            // 2. Retorna null caso a linha não seja encontrada no banco.

            if (umUsuario == null) {
                throw new UsuarioNaoEncontradoException("Produto não encontrado");
            }
            return umUsuario;
        }
    }

    public void altera(Usuario umUsuario) throws UsuarioNaoEncontradoException {
        EntityTransaction tx = null;
        Usuario usuario = null;
        try (EntityManager em = FabricaDeEntityManager.criarEntityManager()) {
            tx = em.getTransaction();
            tx.begin();

            usuario = em.find(Usuario.class, umUsuario.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (usuario == null) {
                tx.rollback();
                throw new UsuarioNaoEncontradoException("Produto não encontrado.");
            }
            // O merge entre nada e tudo é tudo. Ao tentar alterar um produto deletado ele será re-inserido
            // no banco de dados.
            em.merge(umUsuario);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void exclui(Long numero) throws UsuarioNaoEncontradoException {

        EntityTransaction tx = null;
        try (EntityManager em = FabricaDeEntityManager.criarEntityManager()) {
            tx = em.getTransaction();
            tx.begin();

            Usuario usuario = em.find(Usuario.class, numero);

            if (usuario == null) {
                tx.rollback();
                throw new UsuarioNaoEncontradoException("Produto não encontrado");
            }

            em.remove(usuario);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<Usuario> recuperaUsuarios() {

        try (EntityManager em = FabricaDeEntityManager.criarEntityManager()) {

            // Retorna um List vazio caso a tabela correspondente esteja vazia.

            return em
                    .createQuery("select u from Usuario u order by u.id", Usuario.class)
                    .getResultList();
        }
    }
}

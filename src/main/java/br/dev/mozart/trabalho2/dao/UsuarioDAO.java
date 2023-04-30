package br.dev.mozart.trabalho2.dao;

import br.dev.mozart.trabalho2.excecao.UsuarioNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Long inclui(Usuario umUsuario);
    void altera(Usuario umUsuario) throws UsuarioNaoEncontradoException;
    void exclui(Long id) throws UsuarioNaoEncontradoException;
    Usuario recuperaUmUsuario(Long numero) throws UsuarioNaoEncontradoException;
    List<Usuario> recuperaUsuarios();
}

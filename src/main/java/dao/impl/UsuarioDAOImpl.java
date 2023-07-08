package dao.impl;

import dao.UsuarioDAO;
import modelo.Usuario;
import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import excecao.ObjetoNaoEncontradoException;

import java.util.List;

public class UsuarioDAOImpl extends JPADaoGenerico<Usuario, Long> implements UsuarioDAO {
    @RecuperaObjeto
    Usuario recuperaUmUsuario(Long id) throws ObjetoNaoEncontradoException;
    @RecuperaLista
    List<Usuario> recuperaUsuarios();


}

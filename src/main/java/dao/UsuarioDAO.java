package dao;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import excecao.ObjetoNaoEncontradoException;
import modelo.Usuario;

import java.util.List;

public interface UsuarioDAO extends DAOGenerico<Usuario, Long> {
    @RecuperaObjeto
    Usuario recuperaUmUsuario(Long id) throws ObjetoNaoEncontradoException;

    @RecuperaObjeto
    Usuario recuperaUmUsuarioEPedido(Long id) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Usuario> recuperaUsuarios();
}

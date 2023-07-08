package dao;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import excecao.ObjetoNaoEncontradoException;
import modelo.Usuario;

import java.util.List;

public interface UsuarioDAO extends DaoGenerico<Usuario, Long> {
    @RecuperaObjeto
    Usuario recuperaUmUsuario(Long id) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    Usuario recuperaUmUsuarioEPedido(Long id) throws ObjetoNaoEncontradoException;

    @RecuperaLista
    List<Usuario> recuperaUsuarios();


}

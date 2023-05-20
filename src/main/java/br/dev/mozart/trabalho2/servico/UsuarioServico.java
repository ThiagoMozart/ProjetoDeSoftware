package br.dev.mozart.trabalho2.servico;
import br.dev.mozart.trabalho2.dao.UsuarioDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.UsuarioNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Usuario;
import br.dev.mozart.trabalho2.util.FabricaDeDAOs;
import br.dev.mozart.trabalho2.util.JPAUtil;

import java.util.List;

public class UsuarioServico {

    private static UsuarioDAO usuarioDAO = FabricaDeDAOs.getDAO(UsuarioDAO.class);

    public long inclui(Usuario umUsuario) throws UsuarioNaoEncontradoException {
        
        JPAUtil.beginTransaction();
        long id = usuarioDAO.inclui(umUsuario);
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
        
        return id;
    }
    
    public Usuario recuperaUsuario(Long id) throws UsuarioNaoEncontradoException {
        return usuarioDAO.recuperaUmUsuario(id);
    }
    
    public Usuario alteraUsuario(Usuario usuario) throws UsuarioNaoEncontradoException{
        JPAUtil.beginTransaction();
        
        try {
            usuarioDAO.altera(usuario);
        }
        catch(UsuarioNaoEncontradoException | EntidadeDesatualizadaException e){
            JPAUtil.rollbackTransaction();
            throw new UsuarioNaoEncontradoException(e.getMessage());
        }
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
        return usuarioDAO.recuperaUmUsuario(usuario.getId());
    }
    
    public void excluiUsuario(Long id) throws UsuarioNaoEncontradoException {
        
        JPAUtil.beginTransaction();
        usuarioDAO.exclui(id);
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
    }

    public List<Usuario> recuperaUsuarios() {
        return usuarioDAO.recuperaUsuarios();
    }
}


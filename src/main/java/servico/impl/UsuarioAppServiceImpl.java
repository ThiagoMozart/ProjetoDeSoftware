package servico.impl;


import dao.UsuarioDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.UsuarioNaoEncontradoException;
import modelo.Usuario;
import servico.UsuarioAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class UsuarioAppServiceImpl implements UsuarioAppService {

	private final UsuarioDAO usuarioDAO;

	@Autowired
	public UsuarioAppService(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	@Override
	@Transactional
	public long inclui(Usuario umUsuario) {
		return usuarioDAO.inclui(umUsuario);
	}

	@Override
	@Transactional(rollbackFor = { UsuarioNaoEncontradoException.class })
	public void altera(Usuario umUsuario) throws UsuarioNaoEncontradoException {
		try {
			usuarioDAO.altera(umUsuario);
		}
		catch (ObjetoNaoEncontradoException e) {
			throw new UsuarioNaoEncontradoException("Usuario nao encontrado");
		}
	}
	@Override
	public void exclui(Usuario umUsuario) throws UsuarioNaoEncontradoException {
		try {
			usuarioDAO.exclui(umUsuario);
		}
		catch (ObjetoNaoEncontradoException e) {
			throw new UsuarioNaoEncontradoException("Usuario nao encontrado");
		}
	}
	@Override
	public Usuario recuperaUmUsuario(Long id) throws UsuarioNaoEncontradoException {
		try {
			return usuarioDAO.recuperaUmUsuario(id);
		}
		catch (ObjetoNaoEncontradoException e) {
			throw new UsuarioNaoEncontradoException("Produto nao encontrado");
		}
	}

	@Override
	public Usuario recuperaUmUsuarionEPedido(Long id) throws UsuarioNaoEncontradoException {
		try {
			return usuarioDAO.recuperaUmUsuarioEPedido(id);
		} catch (ObjetoNaoEncontradoException e) {
			throw new UsuarioNaoEncontradoException("Usuario não encontrado");
		}
	}

	@Override
	public List<Usuario> recuperaUsuarios() {
		return usuarioDAO.recuperaUsuarios();
	}

}
package servico;

import modelo.Usuario;
import modelo.Pedido;
import excecao.EntidadeNaoEncontradaException;
import repositorio.UsuarioRepositorio;
import repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServico {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PedidoRepositorio pedidoRepositorio;

    @Autowired
    public UsuarioServico(UsuarioRepositorio usuarioRepositorio, PedidoRepositorio pedidoRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.pedidoRepositorio = pedidoRepositorio;
    }

    public List<Usuario> recuperaUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Usuario cadastraUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public Usuario recuperaUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Usuario de ID " + id + " não encontrado."));
    }

    public Usuario atualizaUsuario(Usuario usuario) {
        Usuario umUsuario = recuperaUsuarioPorId(usuario.getId());
        List<Pedido> newPedidos = usuario.getPedidos();
        List<Pedido> oldPedidos = umUsuario.getPedidos();

        for (Pedido pedido : newPedidos) {
            if (!oldPedidos.contains(pedido)) {
                pedidoRepositorio.findById(pedido.getId()).orElseThrow(()-> new EntidadeNaoEncontradaException(
                        "Pedido de ID " + pedido.getId() + " não encontrado."
                ));
            }
        }

        return usuarioRepositorio.save(usuario);
    }

    public void removeUsuario(Long id) {
        recuperaUsuarioPorId(id);
        pedidoRepositorio.deleteById(id);
    }

    public List<Usuario> recuperaUsuarioPorPedido(Long id) {
        return usuarioRepositorio.findByPedidoId(id);
    }
}
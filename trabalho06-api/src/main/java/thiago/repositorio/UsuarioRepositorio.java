package thiago.repositorio;

import thiago.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.id IN (SELECT p.usuario.id FROM Pedido p WHERE p.codigo = :id)")
    List<Usuario> findByPedidoId(Long id);
}
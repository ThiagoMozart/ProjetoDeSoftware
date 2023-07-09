package thiago;

import thiago.modelo.Pedido;
import thiago.modelo.Usuario;
import thiago.repositorio.UsuarioRepositorio;
import thiago.repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RESTfulAPI implements CommandLineRunner {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    public static void main(String[] args) {
        SpringApplication.run(RESTfulAPI.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        pedidoRepositorio.deleteAll();
        usuarioRepositorio.deleteAll();


        Usuario usuario1 = new Usuario("Roberto", "16839928139");
        usuarioRepositorio.save(usuario1);

        Usuario usuario2 = new Usuario("Jonhy", "13213292681");
        usuarioRepositorio.save(usuario2);


        Pedido livro = new Pedido("24-07-2014", "26-07-2014", 3);
        livro.setUsuario(usuario1);
        pedidoRepositorio.save(livro);

        Pedido hq = new Pedido("13-01-2014", "16-02-2014", 5);
        hq.setUsuario(usuario1);
        pedidoRepositorio.save(hq);

        Pedido quadrinho = new Pedido("28-02-2013", "02-03-2013", 10);
        quadrinho.setUsuario(usuario2);
        pedidoRepositorio.save(quadrinho);
    }
}
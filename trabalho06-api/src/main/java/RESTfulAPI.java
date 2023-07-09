package java;

import modelo.Pedido;
import modelo.Usuario;
import repositorio.UsuarioRepositorio;
import repositorio.PedidoRepositorio;
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
        usuarioRepositorio.deleteAll();
        pedidoRepositorio.deleteAll();

        Pedido livro = new Pedido("24/07/2014", "26/07/2014", 3);
        pedidoRepositorio.save(livro);
        Pedido hq = new Pedido("13/01/2014", "16/02/2014", 5);
        pedidoRepositorio.save(hq);
        Pedido quadrinho = new Pedido("28/02/2013", "02/03/2013", 10);
        pedidoRepositorio.save(quadrinho);


        Usuario usuario1 = new Usuario("Roberto", "16839928139", "2410230", "acao");
        usuario1.addPedido(livro);
        usuario1.addPedido(hq);
        usuarioRepositorio.save(usuario1);

        Usuario usuario2 = new Usuario("Jonhy", "321329268139", "2320270", "terror");
        usuario2.addPedido(quadrinho);
        usuarioRepositorio.save(usuario2);
    }
}
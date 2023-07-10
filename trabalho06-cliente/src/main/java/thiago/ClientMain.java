package thiago;

import thiago.exception.EntidadeNaoEncontradaException;
import thiago.exception.ViolacaoDeConstraintException;
import thiago.modelo.Pedido;
import thiago.modelo.Usuario;
import thiago.exception.ErrorHandler;
import corejava.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ClientMain {

    private static final Logger logger = LoggerFactory.getLogger(ClientMain.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        restTemplate.setErrorHandler(new ErrorHandler());

        logger.info("Iniciando a execução da aplicação cliente.");

        long id;
        String nome;
        String cpf;
        String dataPedido;
        String dataEntrega;
        int qtdItens;
        Usuario usuario;
        Pedido pedido;

        boolean continua = true;
        while (continua) {
            System.out.println("\nO que você deseja fazer?");
            System.out.println("\n1. Cadastrar um usuario");
            System.out.println("2. Alterar um usuario");
            System.out.println("3. Remover um usuario");
            System.out.println("4. Recuperar um usuario");
            System.out.println("5. Listar todos os usuarios");
            System.out.println("6. Listar todos os usuarios por pedidos");
            System.out.println("7. Cadastrar um pedido");
            System.out.println("8. Alterar um pedido");
            System.out.println("9. Remover um pedido");
            System.out.println("10. Recuperar um pedido");
            System.out.println("11. Listar todos os pedidos");
            System.out.println("12. Sair");


            int opcao = Console.readInt("\nDigite um número entre 1 e 12:");

            switch (opcao) {
                case 1 -> {
                    nome = Console.readLine("\nInforme o nome do usuario: ");
                    cpf = Console.readLine("Informe o CPF: ");
                    List<Pedido> pedidos = new ArrayList<>();

                    String add = Console.readLine("Deseja adicionar pedidos? (s/n): ");
                    while (add.equals("s")) {
                        id = Console.readInt("\nInforme o ID do pedido: ");
                        try {
                            // faz o método Get para o servidor, passando  o id do pedido e a classe
                            ResponseEntity<Pedido> res = restTemplate.exchange(
                                    "http://localhost:8080/pedidos/{id}",
                                    HttpMethod.GET,
                                    null,
                                    Pedido.class,
                                    id
                            );
                            pedido = res.getBody();
                            pedidos.add(pedido);
                        }
                        catch (EntidadeNaoEncontradaException e) {
                            System.out.println("\nPedido não encontrado!");
                            break;
                        }
                        add = Console.readLine("\nDeseja continuar adicionando mais pedidos? (s/n): ");
                    }

                    usuario = new Usuario(nome, cpf, pedidos);

                    try {

                        ResponseEntity<Usuario> res = restTemplate.postForEntity(
                                "http://localhost:8080/usuarios/",
                                usuario,
                                Usuario.class
                        );
                        usuario = res.getBody();
                        if (usuario == null) {
                            System.out.println("\nErro ao cadastrar o usuario!");
                            break;
                        }

                        System.out.println("\nUsuario com id " + usuario.getId() + " cadastrado com sucesso!");
                        System.out.println(usuario);
                    } catch (ViolacaoDeConstraintException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 2 -> {
                    try {
                        usuario = recuperarObjeto(
                                "Informe o id do usuario que você deseja alterar: ",
                                "http://localhost:8080/usuarios/{id}",
                                Usuario.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(usuario);

                    System.out.println("\nO que você deseja alterar?");
                    System.out.println("\n1. Nome");
                    System.out.println("2. Pedidos");

                    int opcaoAlteracao = Console.readInt("\nDigite o número 1 ou 2:");

                    if (opcaoAlteracao == 1) {
                        nome = Console.readLine("Digite o novo nome: ");
                        usuario.setNome(nome);
                    } else if (opcaoAlteracao == 2) {
                        List<Pedido> pedidos = new ArrayList<>();

                        String add = Console.readLine("Deseja adicionar pedidos? (s/n): ");
                        while (add.equals("s")) {
                            id = Console.readInt("\nInforme o ID do pedido: ");
                            try {
                                ResponseEntity<Pedido> res = restTemplate.exchange(
                                        "http://localhost:8080/pedidos/{id}",
                                        HttpMethod.GET,
                                        null,
                                        Pedido.class,
                                        id
                                );
                                pedido = res.getBody();
                                pedidos.add(pedido);
                            }
                            catch (EntidadeNaoEncontradaException e) {
                                System.out.println("\nPedido não encontrado!");
                                break;
                            }
                            add = Console.readLine("\nDeseja continuar adicionando mais pedidos? (s/n): ");
                        }
                        usuario.setPedidos(pedidos);
                    } else {
                        System.out.println("\nOpção inválida");
                        break;
                    }

                    try {
                        restTemplate.put("http://localhost:8080/usuarios/", usuario);

                        System.out.println("\nUsuario de ID " + usuario.getId() + " alterado com sucesso!");
                        System.out.println(usuario);
                    } catch (ViolacaoDeConstraintException | EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        usuario = recuperarObjeto(
                                "Informe o ID do usuario que você deseja remover: ",
                                "http://localhost:8080/usuarios/{id}",
                                Usuario.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(usuario);

                    String resp = Console.readLine("\nConfirma a remoção do usuario?");

                    if (resp.equals("s")) {
                        try {
                            restTemplate.delete("http://localhost:8080/usuarios/{id}", usuario.getId());

                            System.out.println("\nUsuario de ID " + usuario.getId() + " removido com sucesso!");
                        } catch (EntidadeNaoEncontradaException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Usuario não removido.");
                    }
                }
                case 4 -> {
                    try {
                        usuario = recuperarObjeto(
                                "Informe o ID do usuario que você deseja recuperar: ",
                                "http://localhost:8080/usuarios/{id}", Usuario.class);
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(usuario);
                }
                case 5 -> {
                    ResponseEntity<Usuario[]> res = restTemplate.exchange(
                            "http://localhost:8080/usuarios/",
                            HttpMethod.GET,
                            null,
                            Usuario[].class
                    );
                    Usuario[] usuarios = res.getBody();

                    if (usuarios != null) {
                        for (Usuario umUsuario : usuarios) {
                            System.out.println(umUsuario);
                        }
                    }
                }
                case 6 -> {
                    id = Console.readInt("\nInforme o ID do Pedido: ");

                    ResponseEntity<Usuario[]> res = restTemplate.exchange(
                            "http://localhost:8080/usuarios/pedido/{idPedido}",
                            HttpMethod.GET,
                            null,
                            Usuario[].class,
                            id
                    );
                    Usuario[] usuarios = res.getBody();

                    if (usuarios == null || usuarios.length == 0) {
                        System.out.println("\nNenhum usuario foi encontrado com este pedido.");
                        break;
                    }

                    for (Usuario umUsuario : usuarios) {
                        System.out.println(umUsuario);
                    }
                }
                case 7 -> {
                    dataPedido = Console.readLine("Informe a data do pedido: ");
                    dataEntrega = Console.readLine("Informe a data de entrega: ");
                    qtdItens = Console.readInt("Informe a quantidade de itens: ");

                    pedido = new Pedido(dataPedido, dataEntrega, qtdItens);

                    try {
                        ResponseEntity<Pedido> res = restTemplate.postForEntity(
                                "http://localhost:8080/pedidos/",
                                pedido,
                                Pedido.class
                        );
                        pedido = res.getBody();
                        if (pedido == null) {
                            System.out.println("\nErro ao cadastrar o pedido!");
                            break;
                        }

                        System.out.println("\nPedido com id " + pedido.getCodigo() + " cadastrado com sucesso!");
                        System.out.println(pedido);
                    } catch (ViolacaoDeConstraintException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 8 -> {
                    try {
                        pedido = recuperarObjeto(
                                "Informe o id do pedido que você deseja alterar: ",
                                "http://localhost:8080/pedidos/{id}",
                                Pedido.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(pedido);

                    System.out.println("\nO que você deseja alterar?");
                    System.out.println("\n1. Data de entrega");
                    System.out.println("2. Quantidade de itens");

                    int opcaoAlteracao = Console.readInt("\nDigite o número 1 ou 2:");

                    if (opcaoAlteracao == 1) {
                        dataEntrega = Console.readLine("Digite a nova data de entrega: ");
                        pedido.setDataEntrega(dataEntrega);
                    } else if (opcaoAlteracao == 2) {
                        qtdItens = Console.readInt("Digite o novo número de itens no pedido: ");
                        pedido.setQtdItens(qtdItens);
                    } else {
                        System.out.println("\nOpção inválida");
                        break;
                    }

                    try {
                        restTemplate.put("http://localhost:8080/pedidos/", pedido);

                        System.out.println("\nPedido de ID " + pedido.getCodigo() + " alterado com sucesso!");
                        System.out.println(pedido);
                    } catch (ViolacaoDeConstraintException | EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 9 -> {
                    try {
                        pedido = recuperarObjeto(
                                "Informe o ID do pedido que você deseja remover: ",
                                "http://localhost:8080/pedidos/{id}",
                                Pedido.class
                        );
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(pedido);

                    String resp = Console.readLine("\nConfirma a remoção do Pedido?");

                    if (resp.equals("s")) {
                        try {
                            restTemplate.delete("http://localhost:8080/pedidos/{id}", pedido.getCodigo());

                            System.out.println("\nPedido de ID " + pedido.getCodigo() + " removido com sucesso!");
                        } catch (EntidadeNaoEncontradaException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Pedido não removido.");
                    }
                }
                case 10 -> {
                    try {
                        pedido = recuperarObjeto(
                                "Informe o ID do pedido que você deseja recuperar: ",
                                "http://localhost:8080/pedidos/{id}", Pedido.class);
                    }
                    catch (EntidadeNaoEncontradaException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(pedido);
                }
                case 11 -> {
                    ResponseEntity<Pedido[]> res = restTemplate.exchange(
                            "http://localhost:8080/pedidos/",
                            HttpMethod.GET,
                            null,
                            Pedido[].class
                    );
                    Pedido[] pedidos = res.getBody();

                    if (pedidos != null) {
                        for (Pedido umPedido : pedidos) {
                            System.out.println(umPedido);
                        }
                    }
                }
                case 12 -> continua = false;
                default -> System.out.println("\nOpção inválida!");
            }
        }
    }

    private static <T> T recuperarObjeto(String msg, String url, Class<T> classe) {
        int id = Console.readInt('\n' + msg);
        return restTemplate.getForObject(url, classe, id);
    }
}
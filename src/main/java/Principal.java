import corejava.Console;
import excecao.UsuarioNaoEncontradoException;
import modelo.Usuario;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import servico.UsuarioAppService;


public class Principal {

    public static void main(String[] args) {
        long id;
        String cpf;
        String nome;
        Usuario umUsuario;

        ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
        UsuarioAppService usuarioAppService = (UsuarioAppService) fabrica.getBean("usuarioAppService");

        boolean continua = true;
        while (continua) {
            System.out.println("\nO que você deseja fazer?");
            System.out.println("\n1. Cadastrar um usuario");
            System.out.println("2. Alterar um usuario");
            System.out.println("3. Remover um usuario");
            System.out.println("4. Listar usuarios");
            System.out.println("5. Recuperar um usuario");
            System.out.println("6. Recuperar um usuario e Pedidos");
            System.out.println("7. Sair");

            int opcao = Console.readInt("\nDigite um número entre 1 e 7:");

            switch (opcao) {
                case 1 -> {
                    nome = Console.readLine("\nInforme o nome do usuario: ");
                    cpf = Console.readLine("Informe o CPF do usuario: ");

                    umUsuario = new Usuario();
                    umUsuario.setNome(nome);
                    umUsuario.setCpf(cpf);

                    id = usuarioAppService.inclui(umUsuario);

                    System.out.println("\nUsuario tem o id " + id + " incluído com sucesso!");
                }
                case 2 -> {
                    id = Console.readInt("\nDigite o id do usuario que você deseja alterar: ");

                    try {
                        umUsuario = usuarioAppService.recuperaUmUsuario(id);
                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(umUsuario);

                    System.out.println("\nO que você deseja alterar?");
                    System.out.println("\n1. Nome");
                    System.out.println("2. CPF");

                    boolean esperandoOpcao = true;
                    while (esperandoOpcao) {
                        int opcaoAlteracao = Console.readInt("\nDigite um número de 1 a 2:");

                        switch (opcaoAlteracao) {
                            case 1 -> {
                                esperandoOpcao = false;
                                nome = Console.readLine("Digite o novo nome: ");
                                umUsuario.setNome(nome);

                                try {
                                    usuarioAppService.altera(umUsuario);
                                    System.out.println("Alteração de nome efetuada com sucesso!");
                                } catch (UsuarioNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            case 2 -> {
                                cpf = Console.readLine("Digite o novo CPF: ");
                                umUsuario.setCpf(cpf);

                                esperandoOpcao = false;
                                try {
                                    usuarioAppService.altera(umUsuario);
                                    System.out.println("\nAlteração de descrição efetuada com sucesso!");
                                } catch (UsuarioNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            default -> System.out.println("Opção inválida!");
                        }
                    }
                }
                case 3 -> {
                    id = Console.readInt("\nDigite o id da usuario que você deseja remover: ");

                    try {
                        umUsuario = usuarioAppService.recuperaUmUsuario(id);
                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println(umUsuario);

                    String resp = Console.readLine("Confirma a remoção do usuario? (s/n)");

                    if (resp.equals("s")) {
                        try {
                            usuarioAppService.exclui(umUsuario);
                            System.out.println("\nUsuario removido com sucesso!");
                        } catch (UsuarioNaoEncontradoException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("\nUsuario não removido.");
                    }

                }
                case 4 -> usuarioAppService.recuperaUsuarios().forEach(System.out::println);
                case 5 -> {
                    id = Console.readInt("\nDigite o id do usuario que você quer recuperar: ");

                    try {
                        umUsuario = usuarioAppService.recuperaUmUsuario(id);
                        System.out.println(umUsuario);
                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 6 -> {
                    id = Console.readInt("\nDigite o id do usuario que você quer recuperar: ");

                    try {
                        umUsuario = usuarioAppService.recuperaUmUsuarioEPedido(id);
                        System.out.println("Usuario:");
                        System.out.println(umUsuario);
                        System.out.println("Pedido:");
                        umUsuario.getPedidos().forEach(System.out::println);
                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 7 -> continua = false;
                default -> System.out.println("\nOpção inválida!");
            }
        }
    }
}
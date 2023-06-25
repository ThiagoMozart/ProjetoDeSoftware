package br.dev.mozart.trabalho2;

import br.dev.mozart.trabalho2.excecao.EntidadeExeception;
import br.dev.mozart.trabalho2.excecao.ExemplarNaoEncontradoException;
import br.dev.mozart.trabalho2.excecao.QuadrinhosNaoEncontradoException;
import br.dev.mozart.trabalho2.excecao.UsuarioNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Exemplar;
import br.dev.mozart.trabalho2.modelo.Quadrinhos;
import br.dev.mozart.trabalho2.modelo.Usuario;
import br.dev.mozart.trabalho2.servico.ExemplarServico;
import br.dev.mozart.trabalho2.servico.UsuarioServico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.error("Mensagem de log emitida utilizando o LOG4J");
        // fatal - error - warning - info - debug

        String primeiroNome;
        String segundoNome;
        String nome;
        String cpf;
        String cep;
        String rua;
        String numero;
        String interesses;
        boolean fidelidade;
        Usuario umUsuario;
        Exemplar umExemplar;

        UsuarioServico usuarioServico = new UsuarioServico();
        ExemplarServico exemplarServico = new ExemplarServico();

        Scanner scanner = new Scanner(System.in);

        boolean continua = true;

        while (continua) {
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um usuário");
            System.out.println("2. Alterar um usuário");
            System.out.println("3. Remover um usuário");
            System.out.println("4. Listar todos os usuários");

            System.out.println("5. Cadastrar um exemplar");
            System.out.println("6. Alterar um exemplar");
            System.out.println("7. Remover um exemplar");
            System.out.println("8. Listar todos os exemplares");
            System.out.println("9. Sair");

            System.out.println('\n' + "Digite um número entre 1 e 9:");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.println('\n' + "Informe o primeiro nome do usuário: ");
                    primeiroNome = scanner.next();

                    System.out.println('\n' + "Informe o segundo nome do usuário: ");
                    segundoNome = scanner.next();

                    nome = primeiroNome + " " + segundoNome;

                    System.out.println('\n' + "Informe apenas os números de CPF: ");
                    cpf = scanner.next();

                    System.out.println('\n' + "Informe o nome da rua: ");
                    rua = scanner.next();

                    System.out.println('\n' + "Informe o número da rua: ");
                    numero = scanner.next();

                    cep = rua + numero;

                    System.out.println('\n' + "Informe o seus interesses em HQs: ");
                    interesses = scanner.next();

                    fidelidade = true;

                    umUsuario = new Usuario();
                    umUsuario.setNome(nome);
                    umUsuario.setCpf(cpf);
                    umUsuario.setInteresses(interesses);
                    umUsuario.setFidelidade(fidelidade);
                    umUsuario.setCep(cep);

                    try {
                        usuarioServico.inclui(umUsuario);
                    }catch (UsuarioNaoEncontradoException e){
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' + "ID do usuário" +
                            umUsuario.getId() + " incluído com sucesso!");
                }

                case 2 -> {
                    System.out.println('\n' + "Digite o número do usuário que você deseja alterar: ");
                    Long resposta = scanner.nextLong();

                    try {
                        umUsuario = usuarioServico.recuperaUsuario(resposta);
                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "Número = " + umUsuario.getId()   +
                            '\n' + "Nome = " + umUsuario.getNome() +
                            '\n' + "CPF = " + umUsuario.getCpf()  +
                            '\n' + "CEP = " + umUsuario.getCep()  +
                            '\n' + "Interesses = " + umUsuario.getInteresses());

                    System.out.println('\n' + "O que você deseja alterar?");
                    System.out.println('\n' + "1. Nome");
                    System.out.println('\n' + "2. CPF");
                    System.out.println('\n' + "3. CEP");
                    System.out.println('\n' + "4. Interesses");

                    System.out.println('\n' + "Digite um número de 1 a 4:");
                    int opcaoAlteracao = scanner.nextInt();

                    switch (opcaoAlteracao) {
                        case 1 -> {
                            System.out.println("Digite o novo nome: ");
                            String novoNome = scanner.next();
                            umUsuario.setNome(novoNome);
                            try {
                                usuarioServico.alteraUsuario(umUsuario);

                                System.out.println('\n' +
                                        "Alteração de nome efetuada com sucesso!");
                            } catch (UsuarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            }
                        }

                        case 2 -> {
                            System.out.println("Digite o novo CPF:");
                            String novoCpf = scanner.next();
                            umUsuario.setCpf(novoCpf);
                            try {
                                usuarioServico.alteraUsuario(umUsuario);

                                System.out.println('\n' +
                                        "Alteração de CPF efetuada " +
                                        "com sucesso!");
                            } catch (UsuarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            }
                        }

                        case 3 -> {
                            System.out.println("Digite o novo CEP:");
                            String novoCep = scanner.next();
                            umUsuario.setCep(novoCep);
                            try {
                                usuarioServico.alteraUsuario(umUsuario);

                                System.out.println('\n' +
                                        "Alteração de CEP efetuada " +
                                        "com sucesso!");
                            } catch (UsuarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            }
                        }

                        case 4 -> {
                            System.out.println("Digite o novo interesse:");
                            String novoInteresses = scanner.next();
                            umUsuario.setCpf(novoInteresses);
                            try {
                                usuarioServico.alteraUsuario(umUsuario);

                                System.out.println('\n' +
                                        "Alteração de interesses efetuada " +
                                        "com sucesso!");
                            } catch (UsuarioNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            }
                        }

                        default -> System.out.println('\n' + "Opção inválida!");
                    }
                }

                case 3 -> {
                    System.out.println("Digite o ID do usuário que você deseja remover: ");
                    Long resposta = scanner.nextLong();

                    try {
                        umUsuario = usuarioServico.recuperaUsuario(resposta);
                    } catch (UsuarioNaoEncontradoException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' +
                            "ID = " + umUsuario.getId() +
                            '\n' + "Nome = " + umUsuario.getNome() +
                            '\n' + "CPF  = " + umUsuario.getCpf());

                    System.out.println("Confirme a remoção do usuário: (S/N)");
                    String resp = scanner.next();

                    if (resp.equals("S")) {
                        try {
                            usuarioServico.excluiUsuario(umUsuario.getId());
                            System.out.println('\n' +
                                    "Usuário removido com sucesso!");
                        } catch (UsuarioNaoEncontradoException e) {
                            System.out.println('\n' + e.getMessage());
                        }
                    } else {
                        System.out.println('\n' + "Usuário não removido.");
                    }
                }

                case 4 -> {
                    List<Usuario> usuarios = usuarioServico.recuperaUsuarios();

//                  Utilizando um consumer. Consumer é uma interface funcional. Ela recebe um
//                  argumento e não retorna nada. Para que um valor seja aceito pelo Consumer
//                  deve ser executado o método accept.


//                  Utilizando method reference. Method references são expressões que possuem
//                  o mesmo tratamento de expressões lambda, mas em vez de prover um corpo  à
//                  expressão lambda, eles (os method references) referenciam um método existente
//                  pelo nome.

                    for (Usuario usuario : usuarios) {
                        System.out.println('\n' +
                                "ID = " + usuario.getId()   +
                                '\n' + "Nome = " + usuario.getNome() +
                                '\n' + "CPF  = " + usuario.getCpf()  +
                                '\n' + "CEP = " + usuario.getCep()  +
                                '\n' + "Interesses = " + usuario.getInteresses());
                    }
                }

                case 5 -> {
                    System.out.println('\n' + "O quadrinho existe? (S/N) ");
                    String resp = scanner.next();

                    Quadrinhos quadrinhos = new Quadrinhos();
                    if (resp.equals("S")){
                        System.out.println('\n' + "Informe o código do quadrinho: ");
                        Long codigo = scanner.nextLong();
                        quadrinhos.setCodigo(codigo);
                    }
                    else{
                        System.out.println('\n' + "Informe o nome do quadrinho: ");
                        String tituloQuadrinho = scanner.next();
                        quadrinhos.setTitulo(tituloQuadrinho);

                        System.out.println('\n' + "Informe o nome do autor: ");
                        String nomeAutor = scanner.next();
                        quadrinhos.setAutor(nomeAutor);

                        System.out.println('\n' + "Informe o tema: ");
                        String tema = scanner.next();
                        quadrinhos.setTema(tema);

                        System.out.println('\n' + "Informe o número da Primeira Edição: ");
                        int numeroEdicao = scanner.nextInt();
                        quadrinhos.setPrimeiraEdicao(numeroEdicao);
                    }

                    System.out.println('\n' + "Informe a data de aquisição: ");
                    String dataAquisicao = scanner.next();

                    System.out.println('\n' + "Informe a editora: ");
                    String editora = scanner.next();

                    System.out.println('\n' + "Informe a data de publicação: ");
                    String dataDePublicacao = scanner.next();

                    System.out.println('\n' + "Informe o estado de conservação: ");
                    String estadoConservacao = scanner.next();

                    System.out.println('\n' + "Informe o tipo de capa do Exemplar: ");
                    String tipoCapa = scanner.next();

                    System.out.println('\n' + "Informe a edição: ");
                    String edicao = scanner.next();

                    umExemplar = new Exemplar();
                    umExemplar.setQuadrinhos(quadrinhos);
                    umExemplar.setDataAquisicao(dataAquisicao);
                    umExemplar.setEditora(editora);
                    umExemplar.setDataPublicacao(dataDePublicacao);
                    umExemplar.setEstadoConservacao(estadoConservacao);
                    umExemplar.setTipoCapa(tipoCapa);
                    umExemplar.setEdicao(edicao);

                    try {
                        exemplarServico.inclui(umExemplar);
                    }catch (ExemplarNaoEncontradoException e){
                        System.out.println('\n' + e.getMessage());
                        break;
                    } catch (QuadrinhosNaoEncontradoException | EntidadeExeception e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println('\n' + "ID do Exemplar" +
                            umExemplar.getCodigo() + " incluído com sucesso!");
                }
                case 8 -> {
                    List<Exemplar> exemplares = exemplarServico.recuperaExemplares();

                    for (Exemplar exemplar : exemplares) {
                        System.out.println('\n' +
                                "ID = " + exemplar.getCodigo()   +
                                '\n' + "Quadrinho = " + exemplar.getQuadrinhos() +
                                '\n' + "Data de Aquisição  = " + exemplar.getDataAquisicao()  +
                                '\n' + "Editora = " + exemplar.getEditora()  +
                                '\n' + "Data de Publicação = " + exemplar.getDataPublicacao()  +
                                '\n' + "Estado de Conservação = " + exemplar.getEstadoConservacao()  +
                                '\n' + "Tipo de Capa = " + exemplar.getTipoCapa()  +
                                '\n' + "Edição = " + exemplar.getEdicao());
                    }
                }

                case 9 -> {
                    continua = false;
                }
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}

import java.util.List;

import corejava.Console;
import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;
import servico.ProdutoAppService;
import servico.controle.FabricaDeServico;
import util.Util;

public class Principal {

    public static void main(String[] args) {

        String nome;
        double lanceMinimo;
        String dataCadastro;
        Produto umProduto;

        // Entra na fábrica de serviço e pega o serviço de ProdutoAppService
        ProdutoAppService produtoAppService = FabricaDeServico.getServico(ProdutoAppService.class);

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "O que voc� deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um produto");
            System.out.println("2. Alterar um produto");
            System.out.println("3. Remover um produto");
            System.out.println("4. Listar todos os produtos");
            System.out.println("5. Sair");

            int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 5:");

            switch (opcao) {
                case 1: {
                    nome = Console.readLine('\n' + "Informe o nome do produto: ");
                    lanceMinimo = Console.readDouble("Informe o valor do lance m�nimo: ");
                    dataCadastro = Console.readLine("Informe a data de cadastramento do produto: ");

                    umProduto = new Produto(nome, lanceMinimo, Util.strToDate(dataCadastro));

                    long numero = produtoAppService.inclui(umProduto);

                    System.out.println('\n' + "Produto n�mero " + numero + " inclu�do com sucesso!");

                    break;
                }

                case 2: {
                    int resposta = Console.readInt('\n' + "Digite o n�mero do produto que voc� deseja alterar: ");

                    try {
                        umProduto = produtoAppService.recuperaUmProduto((long) resposta);
                    } catch (ProdutoNaoEncontradoException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' + "N�mero = " + umProduto.getId() + "    Nome = " + umProduto.getNome()
                            + "    Lance M�nimo = " + umProduto.getLanceMinimo());

                    System.out.println('\n' + "O que voc� deseja alterar?");
                    System.out.println('\n' + "1. Nome");
                    System.out.println("2. Lance M�nimo");

                    int opcaoAlteracao = Console.readInt('\n' + "Digite um n�mero de 1 a 2:");

                    switch (opcaoAlteracao) {
                        case 1:
                            String novoNome = Console.readLine("Digite o novo nome: ");

                            umProduto.setNome(novoNome);

                            try {
                                produtoAppService.altera(umProduto);


                                System.out.println('\n' + "Altera��o de nome efetuada com sucesso!");
                            } catch (ProdutoNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            }

                            break;

                        case 2:
                            double novoLanceMinimo = Console.readDouble("Digite o novo lance m�nimo: ");

                            umProduto.setLanceMinimo(novoLanceMinimo);

                            try {
                                produtoAppService.altera(umProduto);

                                System.out.println('\n' + "Altera��o de descri��o efetuada " + "com sucesso!");
                            } catch (ProdutoNaoEncontradoException e) {
                                System.out.println('\n' + e.getMessage());
                            }

                            break;

                        default:
                            System.out.println('\n' + "Op��o inv�lida!");
                    }

                    break;
                }

                case 3: {
                    int resposta = Console.readInt('\n' + "Digite o n�mero do produto que voc� deseja remover: ");

                    try {
                        umProduto = produtoAppService.recuperaUmProduto((long) resposta);
                    } catch (ProdutoNaoEncontradoException e) {
                        System.out.println('\n' + e.getMessage());
                        break;
                    }

                    System.out.println('\n' + "N�mero = " + umProduto.getId() + "    Nome = " + umProduto.getNome());

                    String resp = Console.readLine('\n' + "Confirma a remo��o do produto?");

                    if (resp.equals("s")) {
                        try {
                            produtoAppService.exclui(umProduto.getId());

                            System.out.println('\n' + "Produto removido com sucesso!");
                        } catch (ProdutoNaoEncontradoException e) {
                            System.out.println('\n' + e.getMessage());
                        }
                    } else {
                        System.out.println('\n' + "Produto n�o removido.");
                    }

                    break;
                }

                case 4: {
                    List<Produto> produtos = produtoAppService.recuperaProdutos();

                    for (Produto produto : produtos) {
                        System.out.println(
                                '\n' + "Id = " + produto.getId() + "  Nome = " + produto.getNome() + "  Lance m�nimo = "
                                        + produto.getLanceMinimo() + "  Data Cadastro = " + produto.getDataCadastroMasc());
                    }

                    break;
                }

                case 5: {
                    continua = false;
                    break;
                }

                default:
                    System.out.println('\n' + "Op��o inv�lida!");
            }
        }
    }
}

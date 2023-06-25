package br.dev.mozart.trabalho2.excecao;

public class PedidoNaoEncontradoException extends Exception {
    public PedidoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}

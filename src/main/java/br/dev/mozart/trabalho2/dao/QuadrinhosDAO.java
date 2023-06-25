package br.dev.mozart.trabalho2.dao;

import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.QuadrinhosNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Quadrinhos;

import java.util.List;

public interface QuadrinhosDAO {
    Long inclui(Quadrinhos umQuadrinhos) throws QuadrinhosNaoEncontradoException;
    void altera(Quadrinhos umQuadrinhos) throws QuadrinhosNaoEncontradoException, EntidadeDesatualizadaException;
    void exclui(Long id) throws QuadrinhosNaoEncontradoException;
    Quadrinhos recuperaUmQuadrinho(Long numero) throws QuadrinhosNaoEncontradoException;
    List<Quadrinhos> recuperaQuadrinhos();
}

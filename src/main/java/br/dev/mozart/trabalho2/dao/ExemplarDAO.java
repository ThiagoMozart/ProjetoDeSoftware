package br.dev.mozart.trabalho2.dao;

import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.ExemplarNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Exemplar;

import java.util.List;

public interface ExemplarDAO {
    Long inclui(Exemplar umExemplar) throws ExemplarNaoEncontradoException;
    void altera(Exemplar umExemplar) throws ExemplarNaoEncontradoException, EntidadeDesatualizadaException;
    void exclui(Long id) throws ExemplarNaoEncontradoException;
    Exemplar recuperaUmExemplar(Long numero) throws ExemplarNaoEncontradoException;
    List<Exemplar> recuperaExemplares();

}

package br.dev.mozart.trabalho2.servico;

import br.dev.mozart.trabalho2.dao.ExemplarDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.EntidadeExeception;
import br.dev.mozart.trabalho2.excecao.ExemplarNaoEncontradoException;
import br.dev.mozart.trabalho2.excecao.QuadrinhosNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Exemplar;
import br.dev.mozart.trabalho2.modelo.Quadrinhos;
import br.dev.mozart.trabalho2.util.FabricaDeDAOs;
import br.dev.mozart.trabalho2.util.JPAUtil;

import java.util.List;


public class ExemplarServico {

    private static final QuadrinhosServico quadrinhosServico = new QuadrinhosServico();
    private static final ExemplarDAO exemplarDAO = FabricaDeDAOs.getDAO(ExemplarDAO.class);
    public Exemplar inclui(Exemplar exemplar) throws ExemplarNaoEncontradoException, EntidadeExeception, QuadrinhosNaoEncontradoException {
        JPAUtil.beginTransaction();

        Long codigo = exemplar.getQuadrinhos() != null ? exemplar.getQuadrinhos().getCodigo() : null;
        if (codigo == null || codigo == 0) {
            Quadrinhos quadrinhos = exemplar.getQuadrinhos();
            codigo = quadrinhosServico.inclui(quadrinhos);
            quadrinhos.setCodigo(codigo);
        }

        Long exemplarCodigo = exemplarDAO.inclui(exemplar);

        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();

        exemplar.setCodigo(exemplarCodigo);
        return null;
    }

    public Exemplar recuperaExemplar(Long codigo) throws ExemplarNaoEncontradoException {
        return exemplarDAO.recuperaUmExemplar(codigo);
    }

    public Exemplar alteraExemplar(Exemplar exemplar) throws ExemplarNaoEncontradoException{
        JPAUtil.beginTransaction();

        try {
            exemplarDAO.altera(exemplar);
        }
        catch(ExemplarNaoEncontradoException | EntidadeDesatualizadaException e){
            JPAUtil.rollbackTransaction();
            throw new ExemplarNaoEncontradoException(e.getMessage());
        }
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
        return exemplarDAO.recuperaUmExemplar(exemplar.getCodigo());
    }

    public void excluiExemplar(Long codigo) throws ExemplarNaoEncontradoException {

        JPAUtil.beginTransaction();
        exemplarDAO.exclui(codigo);
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
    }

    public List<Exemplar> recuperaExemplares() {
        return exemplarDAO.recuperaExemplares();
    }
}

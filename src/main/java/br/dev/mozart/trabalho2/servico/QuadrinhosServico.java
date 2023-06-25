package br.dev.mozart.trabalho2.servico;

import br.dev.mozart.trabalho2.dao.QuadrinhosDAO;
import br.dev.mozart.trabalho2.excecao.EntidadeDesatualizadaException;
import br.dev.mozart.trabalho2.excecao.QuadrinhosNaoEncontradoException;
import br.dev.mozart.trabalho2.modelo.Quadrinhos;
import br.dev.mozart.trabalho2.util.FabricaDeDAOs;
import br.dev.mozart.trabalho2.util.JPAUtil;

import java.util.List;

public class QuadrinhosServico {

    private static QuadrinhosDAO quadrinhosDAO = FabricaDeDAOs.getDAO(QuadrinhosDAO.class);

    public long inclui(Quadrinhos umQuadrinho) throws QuadrinhosNaoEncontradoException {
        JPAUtil.beginTransaction();
        long id = quadrinhosDAO.inclui(umQuadrinho);
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();

        return id;
    }

    public Quadrinhos alteraQuadrinhos(Quadrinhos quadrinho) throws QuadrinhosNaoEncontradoException {
        JPAUtil.beginTransaction();

        try {
            quadrinhosDAO.altera(quadrinho);
        } catch (QuadrinhosNaoEncontradoException | EntidadeDesatualizadaException e) {
            JPAUtil.rollbackTransaction();
            throw new QuadrinhosNaoEncontradoException(e.getMessage());
        }
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
        return quadrinhosDAO.recuperaUmQuadrinho(quadrinho.getCodigo());
    }

    public void excluiQuadrinho(Long codigo) throws QuadrinhosNaoEncontradoException {

        JPAUtil.beginTransaction();
        quadrinhosDAO.exclui(codigo);
        JPAUtil.commitTransaction();
        JPAUtil.closeEntityManager();
    }

    public List<Quadrinhos> recuperaQuadrinhos() {
        return quadrinhosDAO.recuperaQuadrinhos();
    }
}


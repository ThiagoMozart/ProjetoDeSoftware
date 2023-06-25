package br.dev.mozart.trabalho2.util;

import br.dev.mozart.trabalho2.dao.ExemplarDAO;
import br.dev.mozart.trabalho2.dao.UsuarioDAO;
import br.dev.mozart.trabalho2.dao.impl.JPAExemplarDAO;
import br.dev.mozart.trabalho2.dao.impl.JPAUsuarioDAO;

public class FabricaDeDAOs {
    @SuppressWarnings("unchecked")
    public static <T> T getDAO(Class<T> clazz) {
        if (clazz.equals(UsuarioDAO.class)){
            return (T) new JPAUsuarioDAO();
        }
        if (clazz.equals(ExemplarDAO.class)){
            return (T) new JPAExemplarDAO();
        }
        return null;
    }
}

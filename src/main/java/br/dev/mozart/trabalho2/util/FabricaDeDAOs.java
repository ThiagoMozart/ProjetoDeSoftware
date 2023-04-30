package br.dev.mozart.trabalho2.util;

import br.dev.mozart.trabalho2.dao.UsuarioDAO;
import br.dev.mozart.trabalho2.dao.impl.JPAUsuarioDAO;

public class FabricaDeDAOs {
    @SuppressWarnings("unchecked")
    public static <T> T getDAO(Class<T> clazz) {
        if (clazz.equals(UsuarioDAO.class)){
            return (T) new JPAUsuarioDAO();
        }
        return null;
    }
}

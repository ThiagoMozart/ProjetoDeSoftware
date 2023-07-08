package dao.controle;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import dao.impl.JPADaoGenerico;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class InterceptadorDeDAO implements MethodInterceptor {
    /*
     * Parametros:
     *
     * objeto - "this", o objeto "enhanced", isto ?, o proxy.
     *
     * metodo - o m?todo interceptado, isto ?, um m?todo da interface ProdutoDAO,
     * LanceDAO, etc.
     *
     * args - um array de args; tipos primitivos s?o empacotados. Cont?m os
     * argumentos que o m?todo interceptado recebeu.
     *
     * metodoProxy - utilizado para executar um m?todo super. Veja o coment?rio
     * abaixo.
     *
     * MethodProxy - Classes geradas pela classe Enhancer passam este objeto para o
     * objeto MethodInterceptor registrado quando um m?todo interceptado ?
     * executado. Ele pode ser utilizado para invocar o m?todo original, ou chamar o
     * mesmo m?todo sobre um objeto diferente do mesmo tipo.
     *
     */

    public Object intercept(Object objeto, Method metodo, Object[] args, MethodProxy metodoProxy) throws Throwable {

        System.out.println("Método interceptado do DAO: " + metodo.getName() +
        " da classe " + metodo.getDeclaringClass().getName());

        JPADaoGenerico<? ,?> daoGenerico = (JPADaoGenerico<?, ?>) objeto;

        if (metodo.isAnnotationPresent(RecuperaObjeto.class)) {
            return daoGenerico.busca(metodo, args);
        }
        else if (metodo.isAnnotationPresent(RecuperaLista.class)) {
            return daoGenerico.buscaLista(metodo, args);
        }
        else {
            throw new RuntimeException("Executando o método: " + metodo.getName() +
                    " da classe/interface " + metodo.getDeclaringClass().getName() +
                    " que não é final e nem está anotado.");
        }
    }
}

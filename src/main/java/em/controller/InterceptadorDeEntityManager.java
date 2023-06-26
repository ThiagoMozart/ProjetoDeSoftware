package em.controller;

import anotacao.PersistenceContext;
import em.impl.ProxyEntityManagerImpl;
import jakarta.persistence.EntityManager;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import util.JPAUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InterceptadorDeEntityManager implements MethodInterceptor {

    /*
     * Parametros:
     *
     * objeto - "this", o objeto "enhanced", isto �, o proxy.
     *
     * metodo - o m�todo interceptado, isto �, um m�todo da interface ProdutoDAO,
     * LanceDAO, etc.
     *
     * args - um array de args; tipos primitivos s�o empacotados. Cont�m os
     * argumentos que o m�todo interceptado recebeu.
     *
     * metodoProxy - utilizado para executar um m�todo super. Veja o coment�rio
     * abaixo.
     *
     * MethodProxy - Classes geradas pela classe Enhancer passam este objeto para o
     * objeto MethodInterceptor registrado quando um m�todo interceptado �
     * executado. Ele pode ser utilizado para invocar o m�todo original, ou chamar o
     * mesmo m�todo sobre um objeto diferente do mesmo tipo.
     *
     */

    public Object intercept(Object objeto, Method metodo, Object[] args, MethodProxy metodoOriginal) throws Throwable {
        try {
            // Pegando o EntityManager
            EntityManager entityManager = JPAUtil.getEntityManager();

            // Pegando a lista de cmapos que foi declarado na classe ProxyEntityManagerImpl
            Field[] campos = ProxyEntityManagerImpl.class.getDeclaredFields();

            // Percorrendo a lista de campos
            for (Field campo : campos) {
                // Verificando se o campo tem a anotação PersistenceContext
                if (campo.isAnnotationPresent(PersistenceContext.class)) {

                    // Permitindo o acesso ao campo mesmo que ele seja privado ou protegido
                    campo.setAccessible(true);
                    try {

                        // Definindo o entityManager no campo que possui a anotação PersistenceContext da instância do ProxyEntityManagerImpl
                        campo.set(objeto, entityManager);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            // Invocando o método original
            return metodoOriginal.invokeSuper(objeto, args);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

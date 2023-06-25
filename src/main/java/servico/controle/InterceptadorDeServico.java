package servico.controle;

import java.lang.reflect.Method;

import anotacao.Transactional;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import util.JPAUtil;

public class InterceptadorDeServico implements MethodInterceptor {
	/*
	 * Parametros:
	 * 
	 * objeto - "this", o objeto "enhanced", isto �, o proxy.
	 * 
	 * metodo - o m�todo interceptado, isto �, um m�todo da interface ProdutoAppService,
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
			// se o método tiver uma anotação @Transactional, precisa iniciar uma transação antes de executar o método
			if (metodo.isAnnotationPresent(Transactional.class)) {
				JPAUtil.beginTransaction();
			}

			System.out.println(
					"M�todo interceptado: " + metodo.getName() + " da classe " + metodo.getDeclaringClass().getName()
			);

			// executa o método original do objeto interceptado
			Object obj = metodoOriginal.invokeSuper(objeto, args);

			// se o método tiver uma anotação @Transactional, precisa finalizar a transação depois de executar o método
			if (metodo.isAnnotationPresent(Transactional.class)) {
				JPAUtil.commitTransaction();
			}

			//retorna o resultado da execução do método do objeto interceptado
			return obj;
		}
		catch (RuntimeException e) {

			// se o método tiver uma anotação @Transactional, é necessário fazer um rollback da transação em caso de erro na execução
			if (metodo.isAnnotationPresent(Transactional.class)) {
				JPAUtil.rollbackTransaction();
			}
			throw e;
		}
		catch (Exception e) {
			// caso tenha uma exceção que não seja RunTime Exception e o método tenha anotação @transactional
			// verifica se a anotação indica qual exceção deve ser tratada com rollback
			if (metodo.isAnnotationPresent(Transactional.class)) {

				// pega da anotação a lista de classes de erro que devem ser tratadas com rollback
				Class<?>[] classes = metodo.getAnnotation(Transactional.class).rollbackFor();
				boolean achou = false;

				// verifica se a exceção que ocorreu está na lista de exceções que devem ser tratadas com rollback
				// se estiver, faz rollback, senão, faz commit
				for (Class<?> classe : classes) {

					// compara se a classe da iteração atual é do mesmo tipo da exceção que ocorreu
					// Se for marca que deve ser feito o rollback
					if (classe.isInstance(e)) {
						achou = true;
						break;
					}
				}
				if (achou) {
					JPAUtil.rollbackTransaction();
				}
				else {
					JPAUtil.commitTransaction();
				}
			}
			throw e;
		}
		finally {
			JPAUtil.closeEntityManager();
		}
	}
}

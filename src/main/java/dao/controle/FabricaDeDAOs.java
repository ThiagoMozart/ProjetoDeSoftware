package dao.controle;

import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

import java.util.Set;

public class FabricaDeDAOs {
	// retorna a implementação de um Dao, recebendo como parâmetro a interface do Dao (uma classe genérica)
	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> tipo) {
		// consegue acesso ao package (caminho) dao.impl, onde estão implementadas os DAOs
		Reflections reflections = new Reflections("dao.impl");
		
		// carrega todas as classes que estão no package dao.impl, que implementam a interface do tipo passado (T)
		Set<Class<? extends T>> classes = reflections.getSubTypesOf(tipo);
		
		// verifica se possui mais de uma classe implamentando a interface
		// se possuir, não tem como saber qual é a classe certa, então dá erro
		if (classes.size() > 1) {
			throw new RuntimeException("Somente uma classe pode implementar " + tipo.getName());
		}
		
		// pega a classe que está implemenando a interface
		Class<?> classe = classes.iterator().next();

		// Enhancer.create cria uma instância Proxy da implementação da classe passada como parâmetro, com o interceptador de Dao
		// e no retorno faço o cast do objeto para o tipo T
		return (T)Enhancer.create(classe, new InterceptadorDeDao());
	}
}

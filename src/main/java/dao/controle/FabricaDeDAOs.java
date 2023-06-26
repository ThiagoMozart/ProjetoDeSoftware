package dao.controle;

import anotacao.Autowired;
import em.controller.FabricaDeEntityManager;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

public class FabricaDeDAOs {
	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> tipo) {
		// consegue acesso ao package (caminho) dao.impl, onde estão implementadas os DAOs
		Reflections reflections = new Reflections("dao.impl");

		// carrega todas as classes que estão no package dao.impl, que implementam a interface do tipo passado (T)
		Set<Class<? extends T>> classes = reflections.getSubTypesOf(tipo);

		// verifica se possui mais de uma classe implamentando a interface
		if (classes.size() > 1) {
			throw new RuntimeException("Somente uma classe pode implementar " + tipo.getName());
		}

		// pega a classe que está implemenando a interface
		Class<?> classe = classes.iterator().next();

		try {
			// pega o construtor da classe que está implementando a interface e cria uma nova instância dessa classe
			T instance = (T)classe.getDeclaredConstructor().newInstance();

			// pega todos os campos da classe que está implementando a interface
			Field[] campos = instance.getClass().getDeclaredFields();

			// percorre todos os campos
			for (Field campo : campos) {

				// verifica se o campo possui a anotação Autowired
				if (campo.isAnnotationPresent(Autowired.class)) {

					// permite o acesso ao campo mesmo que ele seja privado ou protegido
					campo.setAccessible(true);

					// define o Proxy de EntityManager no campo que possui a anotação Autowired da instância do DAO
					campo.set(instance, FabricaDeEntityManager.getEntityManager());
				}
			}
			return instance;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

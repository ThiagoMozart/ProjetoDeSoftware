package servico.controle;

import java.lang.reflect.Field;
import java.util.Set;

import org.reflections.Reflections;

import anotacao.Autowired;
import dao.controle.FabricaDeDAOs;
import net.sf.cglib.proxy.Enhancer;

public class FabricaDeServico {
	// retorna a implementação de um Serviço, recebendo como parâmetro a interface do Serviço (uma classe genérica)
	@SuppressWarnings("unchecked")

	public static <T> T getServico(Class<T> tipo) {
		// consegue acesso ao package (caminho) servico.impl, onde estão implementadas os serviços
		Reflections reflections = new Reflections("servico.impl");

		// carrega todas as classes que estão no package servico.impl, que implementam a interface do tipo passado (T)
		Set<Class<? extends T>> classes = reflections.getSubTypesOf(tipo);

		// verifica se possui mais de uma classe implamentando a interface
		// se possuir, não tem como saber qual é a classe certa, então dá erro
		if (classes.size() > 1) {
			throw new RuntimeException("Somente uma classe pode implementar " + tipo.getName());
		}

		// pega a classe que está implemenando a interface
		Class<?> classe = classes.iterator().next();

		// Enhancer.create cria uma instância Proxy da implementação da classe passada como parâmetro, com o interceptador de Serviço
		// e casta o objeto para o tipo T
		T servico = (T)(Enhancer.create(classe, new InterceptadorDeServico()));

		// Como no serviço o dao é fixo, ele só é injetado uma vez, é necessário percorrer todos os campos do serviço implementado
		// para encontrar o campo com a anotação @Autowired e então injetar a implementação do Dao nesse campo

		// Lista todos os atributos(campos) da classe
		Field[] campos = classe.getDeclaredFields();

		// percorre esses atributos(campos) da classe
		for (Field campo : campos) {

			// Verifica se o atributo(Campo) possui a anotação @Autowired
			if (campo.isAnnotationPresent(Autowired.class)) {

				// permite o acesso ao atributo(Campo) mesmo que ele seja privado ou protegido
				campo.setAccessible(true);

				try {
					// define a implementação do Dao no atributo(Campo) que possui a anotação @Autowired da instância do serviço
					campo.set(servico, FabricaDeDAOs.getDAO(campo.getType()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}

		return servico;
	}

}
package em.controller;

import jakarta.persistence.EntityManager;
import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

import java.util.Set;

public class FabricaDeEntityManager {
    // retorna a implementação do Entity Manager Proxy com o interceptador
    public static EntityManager getEntityManager() {
        // consegue acesso ao package (caminho) em.impl, onde está o Proxy do EntityManager
        Reflections reflections = new Reflections("em.impl");

        // carrerga todas as classes que estão no package em.impl, que implementam a interface do EntityManager
        Set<Class<? extends EntityManager>> classes = reflections.getSubTypesOf(EntityManager.class);

        // verifica se possui mais de uma classe implamentando a interface
        if (classes.size() > 1) {
            throw new RuntimeException("Somente uma classe pode implementar " + EntityManager.class.getName());
        }

        // pega a classe que está implemenando a interface
        Class<?> classe = classes.iterator().next();

        // cria uma instância da implementação da classe passada como parâmetro, com o interceptador de EntityManager
        return (EntityManager) Enhancer.create(classe, new InterceptadorDeEntityManager());
    }
}

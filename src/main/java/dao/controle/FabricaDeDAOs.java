package dao.controle;

import net.sf.cglib.proxy.Enhancer;
import dao.impl.UsuarioDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FabricaDeDAOs {
	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> tipo) {
		return (T)Enhancer.create(tipo, new InterceptadorDeDAO());
	}
	@Bean
	public static UsuarioDAOImpl getUsuarioDao() {
		return getDAO(UsuarioDAOImpl.class);
	}
}

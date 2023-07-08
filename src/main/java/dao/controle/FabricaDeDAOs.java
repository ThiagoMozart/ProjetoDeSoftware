package dao.controle;

import net.sf.cglib.proxy.Enhancer;
import dao.impl.UsuarioDAOImpl;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FabricaDeDAOs {
	@Bean
	public static UsuarioDAOImpl getUsuarioDao() {
		return getDao(dao.impl.UsuarioDAOImpl.class);
	}
	public static <T> T getDAO(Class<T> tipo) {
		return tipo.cast(Enhancer.create(tipo, new InterceptadorDeDAO()));
	}
}

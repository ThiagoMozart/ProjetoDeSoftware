package dao.controle;

import net.sf.cglib.proxy.Enhancer;
import dao.impl.UsuarioDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration que indica que esta classe possui um ou mais métodos anotados com @Bean
//e os métodos anotados por @Bean são os métodos responsáveis por criar os objetos do tipo DAO
@Configuration
public class FabricaDeDAOs {

	// O método getDao(dao.impl.UsuarioDaoImpl) irá mandar executar o construtor de UsuarioDaoImpl,
	// e o construtor manda executar o Construtor de JPADaoGenerico,
	// mas que irá receber como argumento o Usuario.class
	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> tipo) {
		return (T)Enhancer.create(tipo, new InterceptadorDeDAO());
	}
	@Bean
	public static UsuarioDAOImpl getUsuarioDao() {
		return getDAO(UsuarioDAOImpl.class);
	}
}

package dao.controle;

import net.sf.cglib.proxy.Enhancer;
import dao.impl.UsuarioDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration que indica que esta classe possui um ou mais m�todos anotados com @Bean
//e os m�todos anotados por @Bean s�o os m�todos respons�veis por criar os objetos do tipo DAO
@Configuration
public class FabricaDeDAOs {

	// O m�todo getDao(dao.impl.UsuarioDaoImpl) ir� mandar executar o construtor de UsuarioDaoImpl,
	// e o construtor manda executar o Construtor de JPADaoGenerico,
	// mas que ir� receber como argumento o Usuario.class
	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> tipo) {
		return (T)Enhancer.create(tipo, new InterceptadorDeDAO());
	}
	@Bean
	public static UsuarioDAOImpl getUsuarioDao() {
		return getDAO(UsuarioDAOImpl.class);
	}
}

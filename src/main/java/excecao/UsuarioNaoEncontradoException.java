package excecao;

public class UsuarioNaoEncontradoException extends Exception {
	private final static long serialVersionUID = 1;
	public UsuarioNaoEncontradoException(String msg) {
		super(msg);
	}

}
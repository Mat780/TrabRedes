package excecoes;

public class ExcecaoPartidaCheia extends RuntimeException {

	private static final long serialVersionUID = -3095627361666373893L;

	public ExcecaoPartidaCheia() {
		super("A partida já possui 4 jogadores");
	}
}

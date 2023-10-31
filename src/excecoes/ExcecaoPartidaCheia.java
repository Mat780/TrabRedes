package excecoes;

// Excecao para o caso de mais de 4 pessoas queiram jogar 1 unica partida.
public class ExcecaoPartidaCheia extends RuntimeException {

	private static final long serialVersionUID = -3095627361666373893L;

	public ExcecaoPartidaCheia() {
		super("A partida já possui 4 jogadores");
	}
}

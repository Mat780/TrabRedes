package excecoes;

public class ExcecaoPartidaCheia extends RuntimeException {
	public ExcecaoPartidaCheia() {
		super("A partida já possui 4 jogadores");
	}
}

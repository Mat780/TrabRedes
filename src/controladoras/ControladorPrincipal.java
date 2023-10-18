package controladoras;

import java.io.IOException;
import java.util.ArrayList;

import main.Cliente;
import main.Partida;
import main.Usuario;
import telas.JanelaLogin;
import telas.JanelaPrincipal;
import telas.Painel;
import telas.PainelJogo;

public class ControladorPrincipal {
	
	private static JanelaPrincipal janela;
	private static JanelaLogin janelaLogin;
	
	private static boolean[] entradas = {false, false};

	public static void entrarLogin() {
		if(entradas[0] == false) {
			janelaLogin = new JanelaLogin();
			entradas[0] = true;
		} else {
			throw new IllegalAccessError("Erro: entrarLogin() foi chamado 2x");
		}	
	}
	
	public static void entrarJanela(Usuario usuario, Cliente cliente) {		
		if(entradas[1] == false) {
			janelaLogin.dispose();
			janela = new JanelaPrincipal(usuario, cliente);
			entradas[1] = true;
			
		} else {
			throw new IllegalAccessError("Erro: entrarLogin() foi chamado 2x");
		}
		
	}
	
	public static void atualizarInfoCastelo(Partida partida) {
		ArrayList<Painel> vetorPainel = janela.getVetorPainel();
		
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelJogo) {
				PainelJogo painelJogo = (PainelJogo) vetorPainel.get(i);
				painelJogo.atualizarInfoCastelo(partida);
			}
		}
	}
	
	public static void atualizarPartidaParaTodos(Partida partida) {
		try {
			janela.atualizarPartidaParaTodos(partida);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void atualizarPartidaMim(Partida partida) {
		try {
			janela.atualizarPartidaParaMim(partida);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void cancelarPartida() {
		
		try {
			janela.cancelarPartida();
			
		} catch (IOException e) {
			// É possivel de acontecer, mas não é um erro
			e.printStackTrace(); // Debug
		}
		
		janela.retornaPainelOnline();
		
	}
	
	public static void trocaPainel(Painel painel) {
		janela.trocaPainel(painel);
	}
}

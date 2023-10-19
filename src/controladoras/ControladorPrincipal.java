package controladoras;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Cliente;
import main.ClientePeer;
import main.Partida;
import main.ServidorPeer;
import main.Usuario;
import telas.JanelaLogin;
import telas.JanelaPrincipal;
import telas.Painel;
import telas.PainelJogo;

public class ControladorPrincipal {
	
	private static JanelaPrincipal janela;
	private static JanelaLogin janelaLogin;
	
	public static ClientePeer clientePeer;
	public static ServidorPeer servidorPeer;
	
	private static Usuario usr;
	
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
			usr = usuario;
			
			try {
				clientePeer = new ClientePeer();
				servidorPeer = new ServidorPeer(usuario);
				servidorPeer.start();
				
				cliente.updateIp(servidorPeer.getIp());
				cliente.updatePort(servidorPeer.getPort());

				usr.setIP(servidorPeer.getIp());
				usr.setPort(servidorPeer.getPort());
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			janela = new JanelaPrincipal(usr, cliente);
			entradas[1] = true;
			
		} else {
			throw new IllegalAccessError("Erro: entrarLogin() foi chamado 2x");
		}
		
	}
	
	public static boolean conectarPeerToPeer(Usuario usuario) {
		System.out.println("IP: " + usr.getIP() + ":" + usr.getPort());
		System.out.println("IP: " + usuario.getIP() + ":" + usuario.getPort());
		if (usr.getIP().equals(usuario.getIP()) && usr.getPort().equals(usuario.getPort())) {
			JOptionPane.showMessageDialog(null, "Você não pode conectar-se consigo mesmo", "Erro: Conexão com si mesmo", JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		
		return clientePeer.adicionarConexao(usuario);
	}
	
	public static void criarHub(boolean convidado, Usuario rival) {
		
		Partida partida;
		
		if (convidado) {
			partida = new Partida(rival, usr);
			
		} else {
			partida = new Partida(usr, rival);
		}
		
		atualizarPartidaMim(partida);
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
		janela.atualizarPartidaParaMim(partida);			
		
	}
	
	
	public static void cancelarPartida() {
		
		try {
			clientePeer.encerrarTodasAsConexoes();
			
		} catch (IOException e) {
			// É possivel de acontecer, mas não é um erro
			e.printStackTrace(); // Debug
		}
		
		janela.retornaPainelOnline();
		
	}
	
	public static void trocaPainel(Painel painel) {
		janela.trocaPainel(painel);
	}
	
	public static void conexaoMorreu() {
		JOptionPane.showMessageDialog(null, "A conexão com algum jogador foi interrompida", "Erro: Conexão morreu", JOptionPane.ERROR_MESSAGE);

	}
}

package controladoras;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Cliente;
import main.ClientePeer;
import main.InfoPartida;
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
	
	private static ClientePeer clientePeer;
	private static ServidorPeer servidorPeer;
	private static Cliente clienteServer;
	
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
				clienteServer = cliente;
				
				usr.setIP(servidorPeer.getIp());
				usr.setPort(servidorPeer.getPort());
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			janela = new JanelaPrincipal(cliente);
			entradas[1] = true;
			
		} else {
			throw new IllegalAccessError("Erro: entrarLogin() foi chamado 2x");
		}
		
	}
	
	public static boolean conectarPeerToPeer(Usuario usuario) {
		
		if (usr.getIP().equals(usuario.getIP()) && usr.getPort().equals(usuario.getPort())) {
			JOptionPane.showMessageDialog(null, "Você não pode conectar-se consigo mesmo", "Erro: Conexão com si mesmo", JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		
		return clientePeer.adicionarConexao(usuario);
	}
	
	public static boolean convidarPartida() {
		return clientePeer.convidarPartida(usr);
		
	}
	
	public static void criarHub(boolean convidado, Usuario rival) {
		
		int qualJogadorSou = 1;
		
		if (convidado) {
			qualJogadorSou = 2;
			clientePeer.adicionarConexao(rival);	
			clienteServer.entreiEmUmHub();
			entrarNoHub(rival, usr, qualJogadorSou);
		
		} else {
			clienteServer.criarHub(new InfoPartida(usr, rival));
			entrarNoHub(usr, rival, qualJogadorSou);
		}
	
	}
	
	public static void iniciarPartida(boolean first, int qtdJogadores) {
		try {
			
			int[] info = janela.getInfoStartPartida();
			InfoPartida partida = janela.getInfoPartida(); 
			
			if (first) {
				clientePeer.iniciarPartida(qtdJogadores);
				clienteServer.startJogo(partida);
			}
			
			atualizarPartidaPeca(info[0], info[1], info[2], qtdJogadores);
			clientePeer.mostrarMinhaPeca(info[0], info[1], info[2], qtdJogadores);
		
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public static void atualizarPronto(boolean pronto, int qualJogadorSou) {
		janela.atualizarPronto(pronto, qualJogadorSou);
	}
	
	public static void atualizarHubPecaParaTodos(int jogador, int indexPeca1, int indexPeca2) {
		try {
			clientePeer.atualizarPecas(jogador, indexPeca1, indexPeca2);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public static void atualizarHubPeca(int jogador, int indexPeca1, int indexPeca2) {
		janela.atualizarPecas(jogador, indexPeca1, indexPeca2);
	}
	
	public static void atualizarPartidaPeca(int jogador, int indexPeca1, int indexPeca2, int qtdJogadores) {
		janela.iniciarPecaDaPartida(jogador, indexPeca1, indexPeca2, qtdJogadores);
	}
	
	public static void entrarNoHub(Usuario usuario, Usuario rival, int qualJogadorSou) {
		janela.setQualJogadorSou(qualJogadorSou);
		janela.criarHubPartida(usuario, rival);
	}
	
	public static void entrarNoHubExistente(InfoPartida partida) {
		// TODO Para quatro jogadores
		
	}
	
	public static void enviarPronto(boolean pronto, int qualJogadorSou) {
		try {
			clientePeer.enviarPronto(pronto, qualJogadorSou);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void receberPronto(boolean pronto, int qualJogadorSou) {
		janela.receberPronto(pronto, qualJogadorSou);
	}
	
	public static void enviarJogada(int energiaEsq, int expEsq, int energiaDir, int expDir, int muro, int qualJogadorSou) {
		try {
			clientePeer.enviarJogada(energiaEsq, expEsq, energiaDir, expDir, muro, qualJogadorSou);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void receberJogada(int energiaEsq, int expEsq, int energiaDir, int expDir, int muro, int qualJogadorSou) {
		janela.receberJogada(energiaEsq, expEsq, energiaDir, expDir, muro, qualJogadorSou);
	}
	
	public static void finalizarPartida() {
		retornaPainelOnline();
	}
	
	public static void cancelarPartida() {
		clientePeer.cancelarPartida();
			
		retornaPainelOnline();
	}
	
	public static void encerrarTodasAsConexoes() {
		clientePeer.encerrarTodasAsConexoes();
	}
	
	public static void retornaPainelOnline() {
		try {
			clientePeer.encerrarTodasAsConexoes();
			clienteServer.ficarInativo();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
		janela.setTitle("");
		janela.retornaPainelOnline();
	}
	
	public static void mensagemDeAtaqueVida(String peca, String jogador, int dano) {
		String msg = peca + " do jogador " + jogador;
		msg = msg +  " deu " + dano + " de dano no rei adversario";
		JOptionPane.showMessageDialog(janela, msg);
	}
	
	public static void mensagemDeAtaqueMuro(String peca, String jogador, int dano) {
		String msg = peca + " do jogador " + jogador;
		msg = msg +  " deu " + dano + " de dano no muro adversario";
		JOptionPane.showMessageDialog(janela, msg);
	}
	
	public static void mensagemDeSubirDeNivel(String peca, String jogador) {
		String msg = peca + " do jogador " + jogador;
		msg = msg + " subiu de nivel!";
		JOptionPane.showMessageDialog(janela, msg);
	}
	
	public static void mensagemDeBomba(String peca, String jogador) {
		String msg = peca + " do jogador " + jogador;
		msg = msg + " jogou uma bomba no rei adversario";
		JOptionPane.showMessageDialog(janela, msg);
	}
	
	public static void trocaPainel(Painel painel) {
		janela.trocaPainel(painel);
	}
	
	public static void trocaTitulo(String msg) {
		janela.setTitle(msg);
	}
	
	public static void conexaoMorreu() {
		JOptionPane.showMessageDialog(null, "A conexão com algum jogador foi interrompida", "Erro: Conexão morreu", JOptionPane.ERROR_MESSAGE);
	}
	
}

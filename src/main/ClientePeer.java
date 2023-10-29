package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import controladoras.ControladorPrincipal;
import jogo.Castelo;

public class ClientePeer {
	
	private ArrayList<Usuario> vetorUsuarios;
	private ArrayList<Socket> vetorSockets;
	private ArrayList<ObjectInputStream> vetorEntrada;
	private ArrayList<ObjectOutputStream> vetorSaida;
	private ArrayList<Timer> vetorTimers;
	
	private boolean isJogando = false;
	private boolean enviandoMsg = false;
	
	// ClientePeer sera responsavel por varios usuarios.
	public ClientePeer() {
		vetorUsuarios = new ArrayList<>();
		vetorSockets  = new ArrayList<>();
		vetorEntrada  = new ArrayList<>();
		vetorSaida    = new ArrayList<>();
		vetorTimers   = new ArrayList<>();
	}
	
	// Metodo que devolve se pode comecar a partida, ja que ha uma comunicacao
	// entre os usuarios ate a resposta do ACK ser verdadeira.
	public boolean convidarPartida(Usuario euUsuario) {
		enviandoMsg = true;
		
		boolean vamoJogar = false;
		
		if(vetorSockets.size() != 1) {
			System.err.println("Ao convidar para uma partida foi detectado que o cliente, estava se comunicando com mais de um usuário");
			throw new IllegalAccessError();
		} 
		
		try {
			vetorSaida.get(0).writeObject(Protocolos.GAME_INI.name());
			vetorSaida.get(0).writeObject(euUsuario);
			
			String resposta = (String) vetorEntrada.get(0).readObject();
			
			if (resposta.equals(Protocolos.GAME_ACK.name())) {
				vamoJogar = true;
				
			} else if (resposta.equals(Protocolos.GAME_NEG.name())){
				vamoJogar = false;
				
			} else {
				throw new IllegalAccessError("Resposta de convite a partida não identificado");
			}
			
		} catch (IOException e) {
			ControladorPrincipal.conexaoMorreu();
			return false;
		} catch (ClassNotFoundException e) {
			// Não deveria acontecer
			e.printStackTrace();
		}
		
		enviandoMsg = false;
		return vamoJogar;
	}
	
	// Metodo responsavel por finalizar a partida.
	public void finalizarPartida() throws IOException, ClassNotFoundException {
		enviandoMsg = true;
		isJogando = false;
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.GAME_OVER.name());
			vetorEntrada.get(i).readObject(); // Segurança 
		}
		enviandoMsg = false;
	}
	
	// Metodo responsavel por cancelar a partida.
	public void cancelarPartida() {
		enviandoMsg = true;
		isJogando = false;
		
		encerrarTodasAsConexoes();		
		
		enviandoMsg = false;
		JOptionPane.showMessageDialog(null, "Algum dos jogadores acabou perdendo a conexao, não será possivel continuar a partida", "Erro: Perda de conexao", JOptionPane.ERROR_MESSAGE);
	}
	
	// Metodo que envia a jogada de determinado usuario com as suas informacoes (passadas por parametro).
	public void enviarJogada(int energiaEsq, int expEsq, int energiaDir, int expDir, int muro, int qualJogadorSou) throws IOException, ClassNotFoundException {
		enviandoMsg = true;
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.JOGADA.name()); 
			vetorEntrada.get(i).readObject();
			
			vetorSaida.get(i).writeObject(energiaEsq);
			vetorEntrada.get(i).readObject();
			
			vetorSaida.get(i).writeObject(expEsq);
			vetorEntrada.get(i).readObject();
			
			vetorSaida.get(i).writeObject(energiaDir);
			vetorEntrada.get(i).readObject();
			
			vetorSaida.get(i).writeObject(expDir);
			vetorEntrada.get(i).readObject();
			
			vetorSaida.get(i).writeObject(muro);
			vetorEntrada.get(i).readObject();
			
			vetorSaida.get(i).writeObject(qualJogadorSou);
			vetorEntrada.get(i).readObject();
		}
		enviandoMsg = false;
	}
	
	// Metodo que envia pronto especifico de cada jogador para a partida.
	public void enviarPronto(boolean pronto, int qualJogadorSou) throws IOException {
		enviandoMsg = true;
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.UPDATE_PRONTO.name());
			vetorSaida.get(i).writeObject(pronto);
			vetorSaida.get(i).writeObject(qualJogadorSou);
		}
		enviandoMsg = false;
	}
	
	// Metodo que atualiza as pecas especificas de cada jogador.
	public void atualizarPecas(int jogador, int indexPeca1, int indexPeca2) throws IOException, ClassNotFoundException {
		enviandoMsg = true;
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.UPDATE_PECA.name());
			vetorSaida.get(i).writeObject(jogador);
			vetorEntrada.get(i).readObject();
			vetorSaida.get(i).writeObject(indexPeca1);
			vetorEntrada.get(i).readObject();
			vetorSaida.get(i).writeObject(indexPeca2);
			vetorEntrada.get(i).readObject();
		}
		enviandoMsg = false;
	}
	
	// Metodo que inicia partida.
	public void iniciarPartida(int qtdJogadores) throws IOException {
		enviandoMsg = true;
		isJogando = true;
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.GAME_START.name());
			vetorSaida.get(i).writeObject(qtdJogadores);
		}
		enviandoMsg = false;
	}
	
	// Metodo que indica a peca especifica de cada jogador.
	public void mostrarMinhaPeca(int qualJogadorSou, int indexPeca1, int indexPeca2, int qtdJogadores) throws IOException, ClassNotFoundException {
		enviandoMsg = true;
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.GAME_START_PECA.name());
			vetorSaida.get(i).writeObject(qualJogadorSou);
			vetorEntrada.get(i).readObject();
			vetorSaida.get(i).writeObject(indexPeca1);
			vetorEntrada.get(i).readObject();
			vetorSaida.get(i).writeObject(indexPeca2);
			vetorEntrada.get(i).readObject();
			vetorSaida.get(i).writeObject(qtdJogadores);
		}
		enviandoMsg = false;
	}
	
	// Metodo que adiciona uma conexao por meio de socket. Inclusive possui
	// um timer para verificar o tempo da comunicacao.
	public boolean adicionarConexao(Usuario usuario) {
		
		String ip = usuario.getIP();
		int port  = Integer.parseInt(usuario.getPort());

		System.out.println("IP: " + ip + ":" + port);
		
		try {
			Socket socket = new Socket(ip, port);

			ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
			
			vetorSockets.add(socket);
			vetorEntrada.add(entrada);
			vetorSaida.add(saida);
			
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						if (enviandoMsg == false)
							saida.writeObject(Protocolos.STAY_ALIVE.name());
					} catch (IOException e) {
						isJogando = false;
						cancelarPartida();	
						
					}
					
				}
			}, 5000, 5000);
	
			vetorTimers.add(timer);
			vetorUsuarios.add(usuario);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel conectar com o usuário solicitado", "Erro: Conexão impossivel", JOptionPane.ERROR_MESSAGE);
			return false;
		} 		
		
		return true;
	}
	
	// Metodo que encerra a conexao.
	public void encerrarConexao(Usuario usuario) {
		int index = vetorUsuarios.indexOf(usuario);
		vetorTimers.get(index).cancel();
		
		try {
			vetorSockets.get(index).close();
		} catch (IOException e) {
			// Pode acontecer e tá tudo bem
		}
		
		vetorUsuarios.remove(index);
		vetorTimers.remove(index);
		vetorSockets.remove(index);
		vetorEntrada.remove(index);
		vetorSaida.remove(index);
	}
	
	// Metodo que encerra todas as conexoes utilizando o metodo anterior.
	public void encerrarTodasAsConexoes() {
		for (int i = vetorSockets.size() - 1; i > -1; i--) {
			encerrarConexao(vetorUsuarios.get(i));
		}
		
		vetorUsuarios.clear();
		vetorTimers.clear();
		vetorSockets.clear();
		vetorEntrada.clear();
		vetorSaida.clear();
	}
	
	
}

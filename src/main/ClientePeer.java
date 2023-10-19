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
	
	public ClientePeer() {
		vetorUsuarios = new ArrayList<>();
		vetorSockets  = new ArrayList<>();
		vetorEntrada  = new ArrayList<>();
		vetorSaida    = new ArrayList<>();
		vetorTimers   = new ArrayList<>();
	}
	
	public void convidarPartida() {
		if(vetorSockets.size() != 1) {
			System.err.println("Ao convidar para uma partida foi detectado que o cliente, estava se comunicando com mais de um usuário");
			throw new IllegalAccessError();
		} 
		
		try {
			vetorSaida.get(0).writeObject(Protocolos.GAME_INI.name());
			
			String resposta = (String) vetorEntrada.get(0).readObject();
			
			if (resposta.equals(Protocolos.GAME_ACK.name())) {
				Usuario rival = (Usuario) vetorEntrada.get(0).readObject();
				ControladorPrincipal.criarHub(false, rival);
				
			} else if (resposta.equals(Protocolos.GAME_NEG.name())){
				JOptionPane.showMessageDialog(null, "Jogador recusou sua partida", "Partida recusada", JOptionPane.INFORMATION_MESSAGE);
				
			} else {
				throw new IllegalAccessError("Resposta de convite a partida não identificado");
			}
			
		} catch (IOException e) {
			ControladorPrincipal.conexaoMorreu();
		} catch (ClassNotFoundException e) {
			// Não deveria acontecer
			e.printStackTrace();
		}
	}
	
	// Serve para que no hub possa alternar entre times
	public void atualizarPartida(Partida partida) throws IOException {
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.GAME_UPDATE.name());
			vetorSaida.get(i).writeObject(partida);
		}
	}
	
	// Serve para finalizar uma partida antecipadamente
	public void cancelarPartida() throws IOException, ClassNotFoundException {
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(Protocolos.GAME_OVER.name());
			vetorEntrada.get(i).readObject(); // Segurança 
		}
	}
	
	public void atualizarJogo(Partida partida) throws IOException {
		for (int i = 0; i < vetorSockets.size(); i++) {
			vetorSaida.get(i).writeObject(partida);
			ControladorPrincipal.atualizarInfoCastelo(partida);
		}
	}
	
	public boolean adicionarConexao(Usuario usuario) {
		
		String ip = usuario.getIP();
		int port  = Integer.parseInt(usuario.getPort());
		
		System.out.println("Comecando adicionarConexao");
		
		try {
			Socket socket = new Socket(ip, port);
			vetorSockets.add(socket);
			
			int indexSocket = vetorSockets.indexOf(socket);
			
			System.out.println("Apos criação socket");
			
			ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
			
			System.out.println("Input stream pegos");
			
			vetorEntrada.add(entrada);
			vetorSaida.add(saida);
			
			System.out.println("Input stream adicionados ao vetor");
			
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						vetorSaida.get(indexSocket).writeObject(Protocolos.STAY_ALIVE.name());
					} catch (IOException e) {
						try {
							encerrarConexao(vetorUsuarios.get(vetorUsuarios.indexOf(usuario)));
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
				}
			}, 5000, 5000);
			
			System.out.println("Timer construido");
			
			vetorTimers.add(timer);
			vetorUsuarios.add(usuario);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel conectar com o usuário solicitado", "Erro: Conexão impossivel", JOptionPane.ERROR_MESSAGE);
			return false;
		} 		
		
		return true;
	}
	
	public void encerrarConexao(Usuario usuario) throws IOException {
		int index = vetorUsuarios.indexOf(usuario);
		vetorTimers.get(index).cancel();
		vetorSockets.get(index).close();
		
		vetorUsuarios.remove(index);
		vetorTimers.remove(index);
		vetorSockets.remove(index);
		vetorEntrada.remove(index);
		vetorSaida.remove(index);
	}
	
	public void encerrarTodasAsConexoes() throws IOException {
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

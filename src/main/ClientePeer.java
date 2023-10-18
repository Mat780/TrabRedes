package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
	
	public void adicionarConexao(Usuario usuario) {
		
		String ip = usuario.getIP();
		int port  = Integer.parseInt(usuario.getPort());
		
		try {
			Socket socket = new Socket(ip, port);
			vetorSockets.add(socket);
			
			int indexSocket = vetorSockets.indexOf(socket);
			
			vetorEntrada.add(new ObjectInputStream(vetorSockets.get(indexSocket).getInputStream()));
			vetorSaida.add(new ObjectOutputStream(vetorSockets.get(indexSocket).getOutputStream()));
			
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
			
			vetorTimers.add(timer);
			vetorUsuarios.add(usuario);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 		
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

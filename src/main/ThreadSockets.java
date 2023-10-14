package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ThreadSockets extends Thread {
	private Socket socket;
	private Usuario usuarioAtual;
	
	public ThreadSockets (Socket socket) throws SocketException {
		this.socket = socket;
		int segsTimeOut = 21;
		this.socket.setSoTimeout(segsTimeOut*1000);
		
	}
	
	@Override
	public void run() {
		System.out.println("[ATENDENDO] " + Thread.currentThread().getName());
		boolean online = true;
		ObjectOutputStream saida = null;
		ObjectInputStream entrada = null;
		
		try {
			entrada = new ObjectInputStream(socket.getInputStream());
			saida = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(online) {
			try {
					
					String protocolo = (String) entrada.readObject();
	
					// Verificar qual o protocolo
					if (protocolo.equals(Protocolos.LOGIN.name())) {
						String usuario = (String) entrada.readObject();
						String senha   = (String) entrada.readObject();
						
						String[] resposta = IDsESenhas.login(usuario, senha);
						
						if (resposta.length > 1) usuarioAtual = new Usuario(usuario, senha, resposta[1], resposta[2], resposta[3]);
	
						saida.writeObject(resposta);
	
					} else if (protocolo.equals(Protocolos.REGISTER.name())) {
						String usuario = (String) entrada.readObject();
						String senha   = (String) entrada.readObject();
						String nome    = (String) entrada.readObject();
						String ip      = (String) entrada.readObject();
						String port = "-1";
						
						boolean cadastroEfetuado = IDsESenhas.register(usuario, senha, nome, ip, port);
						
						saida.writeObject(cadastroEfetuado);
						
					} else if (protocolo.equals(Protocolos.LIST_USER_ON_LINE.name())) {
						ArrayList<Usuario> listaOnline = IDsESenhas.listOnline();
						saida.writeObject(listaOnline);
						
					} else if (protocolo.equals(Protocolos.LIST_USER_PLAYING.name())) {
						ArrayList<Partida> listaPartidas = IDsESenhas.listJogando();
						saida.writeObject(listaPartidas);
						
					} else if (protocolo.equals(Protocolos.UPDATE_PORT.name())) {
						String port = (String) entrada.readObject();
						IDsESenhas.updatePort(usuarioAtual.getUsuario(), port);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GAME_HUB.name())) {
						Partida pAntiga = (Partida) entrada.readObject();
						Partida pNova   = (Partida) entrada.readObject();
						IDsESenhas.jogarPartida(pAntiga, pNova, usuarioAtual);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GAME_START.name())) {
						Partida partida = (Partida) entrada.readObject();
						IDsESenhas.startPartida(partida);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GAME_OVER.name())) {
						Partida partida = (Partida) entrada.readObject();
						IDsESenhas.finalizarPartida(partida, usuarioAtual);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.DISCONNECT.name())) {
						online = false;
						
						if (usuarioAtual != null) IDsESenhas.disconnect(usuarioAtual.getUsuario(), false);
						
						saida.writeObject("Ok");
						
						saida.close();
						entrada.close();
						socket.close();
												
					} else if (protocolo.equals(Protocolos.STAY_ALIVE.name())) {
						System.out.println("Cliente mandou oi");
						
					} else if (protocolo.equals("Msg")) { //! Debug
						String m = (String) entrada.readObject();
						saida.writeObject(m.toUpperCase());
					} else {
						System.out.println("Erro indeterminado ao receber: " + protocolo + System.getProperty("line separator") + ", Conexao será fechada por conta deste erro");
						online = false;
					}
					
					
			} catch (SocketTimeoutException e) {
				try {
					IDsESenhas.disconnect(usuarioAtual.getUsuario(), true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				online = false;
				
			} catch (Exception e) {
				e.printStackTrace();
				online = false;
			} 
		}			
		
		System.out.println("Fechando conexão");
		
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
}
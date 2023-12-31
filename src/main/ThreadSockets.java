package main;

import java.io.EOFException;
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
	
	// Metodo em que roda um determinado protocolo correspondente ao que esta true.
	@Override
	public void run() {
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
						ArrayList<InfoPartida> listaPartidas = IDsESenhas.listJogando();
						saida.writeObject(listaPartidas);
						
					} else if (protocolo.equals(Protocolos.UPDATE_IP.name())) {
						String ip = (String) entrada.readObject();
						IDsESenhas.updateIp(usuarioAtual.getUsuario(), ip);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.UPDATE_PORT.name())) {
						String port = (String) entrada.readObject();
						IDsESenhas.updatePort(usuarioAtual.getUsuario(), port);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GAME_HUB.name())) {
						InfoPartida partida = (InfoPartida) entrada.readObject();
						IDsESenhas.criarHub(partida, usuarioAtual);
						saida.writeObject(Protocolos.OK.name());
						
					} else if (protocolo.equals(Protocolos.GAME_HUB_ENTER.name())) {
						IDsESenhas.estouEntrandoEmUmHub(usuarioAtual);
						saida.writeObject(Protocolos.OK.name());
						
					} else if (protocolo.equals(Protocolos.GAME_START.name())) {
						InfoPartida partida = (InfoPartida) entrada.readObject();
						IDsESenhas.startPartida(partida);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GAME_OVER.name())) {
						InfoPartida partida = (InfoPartida) entrada.readObject();
						IDsESenhas.finalizarPartida(partida, usuarioAtual);
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.DISCONNECT.name())) {
						online = false;
						
						if (usuarioAtual != null) {
							IDsESenhas.disconnect(usuarioAtual, false);
							
						} else {
							System.out.println("Alguma coisa tá errada");
						}
						
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GET_QTD_CADASTRADOS.name())) {
						saida.writeObject(IDsESenhas.getQtdRegistrado());
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GET_QTD_ONLINE.name())) {
						saida.writeObject(IDsESenhas.getQtdOnline());
						saida.writeObject("Ok");
						
					} else if (protocolo.equals(Protocolos.GET_QTD_JOGANDO.name())) {
						saida.writeObject(IDsESenhas.getQtdJogando());
						
					} else if (protocolo.equals(Protocolos.VOLTAR_LIST_ONLINE.name())) { 
						IDsESenhas.ficarInativo(usuarioAtual);
						saida.writeObject(Protocolos.OK.name());
						
					} else if (protocolo.equals(Protocolos.STAY_ALIVE.name()) == false) {
						System.out.println("Erro indeterminado ao receber: " + protocolo + ", Conexao será fechada por conta deste erro");
						online = false;
					}
					
					
			} catch (SocketTimeoutException e) {
				
				IDsESenhas.disconnect(usuarioAtual, true);
				online = false;
				
			} catch(EOFException e) {
				IDsESenhas.disconnect(usuarioAtual, false);
				online = false;
				
			} catch (SocketException e) {
				// Quando o cliente fecha a aplicação dele ele solta um connection reset
				// Portanto coloquei um if para caso não seja isso, ele avise de possiveis problemas
				if (e.getMessage().equals("Connection reset") == false) {
					e.printStackTrace();
				}
				
				IDsESenhas.disconnect(usuarioAtual, false);
				online = false;
				
			} catch (Exception e) {
				e.printStackTrace();
				online = false;
			} 
		}			
		
		try {
			saida.close();
			entrada.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}
}

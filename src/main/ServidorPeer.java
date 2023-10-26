package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorPeer extends Thread{

	private ServerSocket serverSocket;
	private Usuario usuario;
	private int port;
	
	public ServidorPeer(Usuario usuario) throws IOException {
		serverSocket = new ServerSocket(0);
		port = serverSocket.getLocalPort();
		this.usuario = usuario;
		this.usuario.setIP(getIp());
		this.usuario.setPort(getPort());
	}
	
	@Override
	public void run() {
		
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				
				ThreadServerPeer serverPeer = new ThreadServerPeer(socket, usuario);
				serverPeer.start();	
			} catch (IOException e) {
				continue;
			}
		}			
		
	}
	
	public String getPort() {
		return Integer.toString(port);
	}
	
	public String getIp() {
		
		String retorno = null;
		
		try {
		    retorno = InetAddress.getLocalHost().toString().split("/")[1];
		    
		} catch (IOException e) {
			System.err.println("Ao tentar pegar o IP da maquina local");
			e.printStackTrace();
		}
		
		return retorno;
	}
	
}

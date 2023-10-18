package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorPeer {

	private ServerSocket serverSocket;
	private int port;
	
	public ServidorPeer(Usuario usuario) throws IOException {
		serverSocket = new ServerSocket();
		port = serverSocket.getLocalPort();
		serverListen(usuario);
	}
	
	private void serverListen(Usuario usuario) throws IOException {
		
		while(true) {
			Socket socket = serverSocket.accept();
			
			ThreadServerPeer serverPeer = new ThreadServerPeer(socket, usuario);
			serverPeer.start();	
		}
		
	}
	
	public int getPort() {
		return port;
	}
	
}

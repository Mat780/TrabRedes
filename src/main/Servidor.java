package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	public static void main(String[] args) throws IOException {
		ServerSocket servidorSocket = new ServerSocket(55555);
		IDsESenhas.inicializar();
		
		while (true) {
			Socket socket = servidorSocket.accept();
			
			ThreadSockets thread = new ThreadSockets(socket);
			thread.start();
		}
		
	}
		
		// 1º Recebe uma conexão, cria uma nova thread para atender a requisição
		// 2º A nova thread verifica a mensagem para saber qual protocolo é aquela mensagem
		// 3º Ao identificar o protocolo prossegue com seu atendimento
		// 4º Após o atendimento do protocolo, fica em aguardo naquela conexão para uma nova mensagem
		// 5º Caso em 60 segundos, não haja uma nova mensagem, encerra a conexão e encerra a thread
	
}
package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import controladoras.ControladorPrincipal;

public class ThreadServerPeer extends Thread {
	
	private Socket socket;
	private Usuario host;
	private ObjectInputStream  entrada;
	private ObjectOutputStream saida;
	private boolean rodando;
	
	public ThreadServerPeer(Socket socket, Usuario host) {
		this.socket = socket;
		this.host = host;
	}
	
	@Override
	public void run() {
		try {
			rodando = true;				
			entrada = new ObjectInputStream(socket.getInputStream());
			saida   = new ObjectOutputStream(socket.getOutputStream());
			while(rodando) {
				String protocolo = (String) entrada.readObject();
				tratarConexao(protocolo);
			}
			
			socket.close();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void tratarConexao(String protocolo) throws ClassNotFoundException, IOException {
		if (protocolo.equals(Protocolos.GAME_INI.name())) {
			
			Usuario j = (Usuario) entrada.readObject();
			
			String aux = Usuario.staticToString(j.getUsuario(), j.getIP(), j.getPort());
			
			String[] opcoes = {"Sim", "Não"};
			
			int opcao = JOptionPane.showInternalOptionDialog(null, "Quer jogar uma partida com este jogador? " + aux, "Quer jogar comigo?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
			
			if (opcao == 0) {
				saida.writeObject(Protocolos.GAME_ACK.name());
				
				saida.writeObject(host);
				Usuario usr = (Usuario) entrada.readObject();
				
				Partida partida = new Partida(usr, host);
				
				//TODO Iniciar novo jogo com a partida criada
				
			} else {
				saida.writeObject(Protocolos.GAME_NEG.name());
				rodando = false;
			}
			
		} else if (protocolo.equals(Protocolos.GAME_ENTER.name())) {
			Partida partida = (Partida) entrada.readObject();
			ControladorPrincipal.atualizarPartidaParaTodos(partida);
			
		} else if (protocolo.equals(Protocolos.GAME_UPDATE.name())) {
			Partida partida = (Partida) entrada.readObject();
			ControladorPrincipal.atualizarPartidaMim(partida);
			
		} else if (protocolo.equals(Protocolos.GAME_OVER.name())) {
			ControladorPrincipal.cancelarPartida();
			saida.writeObject("Ok");
			rodando = false;
			
		} else if (protocolo.equals(Protocolos.STAY_ALIVE.name()) == false) {
			System.out.println("Erro indeterminado ao receber: " + protocolo + ", Conexao será fechada por conta deste erro");
			rodando = false;
		}
	}
}

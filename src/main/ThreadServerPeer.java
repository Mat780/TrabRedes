package main;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import controladoras.ControladorPrincipal;

public class ThreadServerPeer extends Thread {
	
	private Socket socket;
	private Partida partida;
	private ObjectInputStream  entrada;
	private ObjectOutputStream saida;
	private boolean rodando;
	
	public ThreadServerPeer(Socket socket, Usuario host) {
		this.socket = socket;
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
			
			fecharConexao();
			
		} catch (EOFException | SocketException e) {
			ControladorPrincipal.cancelarPartida();
			fecharConexao();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		fecharConexao();
		
	}
	
	private void fecharConexao() {
		try {
			if (socket.isClosed() == false) socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void tratarConexao(String protocolo) throws ClassNotFoundException, IOException {
		if (protocolo.equals(Protocolos.GAME_INI.name())) {
			
			Usuario j = (Usuario) entrada.readObject();
			
			String aux = j.toString();
			
			String[] opcoes = {"Sim", "Não"};
			
			int opcao = JOptionPane.showInternalOptionDialog(null, "Quer jogar uma partida com este jogador? " + aux, "Quer jogar comigo?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
			
			if (opcao == 0) {
				saida.writeObject(Protocolos.GAME_ACK.name());
				ControladorPrincipal.criarHub(true, j);
				
			} else {
				saida.writeObject(Protocolos.GAME_NEG.name());
				rodando = false;
			}
			
		} else if (protocolo.equals(Protocolos.GAME_ENTER.name())) {
			InfoPartida partida = (InfoPartida) entrada.readObject();
			ControladorPrincipal.entrarNoHubExistente(partida);
			
		} else if (protocolo.equals(Protocolos.UPDATE_PECA.name())) {
			int jogador = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			int index1 = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			int index2 = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			
			ControladorPrincipal.atualizarHubPeca(jogador, index1, index2);
			
		} else if (protocolo.equals(Protocolos.JOGADA.name())) {
			int energiaEsq;
			int expEsq;
			int energiaDir;
			int expDir;
			int muro;
			int qualJogadorSou;
			
			saida.writeObject(Protocolos.OK.name());
			
			energiaEsq = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			
			expEsq = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			
			energiaDir = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			
			expDir = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			
			muro = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			
			qualJogadorSou = (int) entrada.readObject();
			saida.writeObject(Protocolos.OK.name());
			
			ControladorPrincipal.receberJogada(energiaEsq, expEsq, energiaDir, expDir, muro, qualJogadorSou);
			
		} else if (protocolo.equals(Protocolos.UPDATE_PRONTO.name())) {
			boolean pronto = (boolean) entrada.readObject();
			int qualJogadorSou = (int) entrada.readObject();
			ControladorPrincipal.atualizarPronto(pronto, qualJogadorSou);
			
		} else if (protocolo.equals(Protocolos.GAME_START.name())) {
			int jogadores = (int) entrada.readObject();
			ControladorPrincipal.iniciarPartida(false, jogadores);
			
		} else if (protocolo.equals(Protocolos.GAME_START_PECA.name())) {
			int qualJogadorSou = (int) entrada.readObject();
			saida.writeObject("Ok");
			int indexPeca1 = (int) entrada.readObject();
			saida.writeObject("Ok");
			int indexPeca2 = (int) entrada.readObject();
			saida.writeObject("Ok");
			int qtdJogadores = (int) entrada.readObject();
			
			ControladorPrincipal.atualizarPartidaPeca(qualJogadorSou, indexPeca1, indexPeca2, qtdJogadores);
			
		} else if (protocolo.equals(Protocolos.GAME_OVER.name())) {
			saida.writeObject("Ok");
			ControladorPrincipal.finalizarPartida();
			rodando = false;
			
		} else if (protocolo.equals(Protocolos.STAY_ALIVE.name()) == false) {
			System.out.println("Erro indeterminado ao receber: " + protocolo + ", Conexao será fechada por conta deste erro");
			rodando = false;
		}
	}
}

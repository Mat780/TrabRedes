package main;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Cliente {
	
	private Socket clienteSocket;
	private ObjectInputStream entrada;
	private ObjectOutputStream saida;
	private Timer timer;
	
	public Cliente() throws IOException {
		
		String ipServer = null;
		
		try (FileReader leitorDoIPDoServer = new FileReader("src/main/config")) {
			Properties propriedades = new Properties();
			propriedades.load(leitorDoIPDoServer);
			ipServer = propriedades.getProperty("SERVER_IP");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		clienteSocket = new Socket (ipServer, 55555);
		saida = new ObjectOutputStream(clienteSocket.getOutputStream());
		entrada = new ObjectInputStream(clienteSocket.getInputStream());
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					saida.writeObject(Protocolos.STAY_ALIVE.name());
				} catch (IOException e) {
					System.out.println("Erro ao manter conexão com o socket");
					try {
						clienteSocket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		}, 5000, 5000);
	}
	
	public String[] loginUsuario(String usuario, String senha) throws IOException {
		saida.writeObject(Protocolos.LOGIN.name());
		saida.writeObject(usuario);
		saida.writeObject(senha);
		
		String[] resposta = new String[1];
		
		try {
			resposta = (String[]) entrada.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Erro: Ao fazer cast das informações do servidor, cast de Object para String[]");
			e.printStackTrace();
		}
		
		return resposta;
		
	}
	
	public boolean register(String usuario, String senha, String nome) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.REGISTER.name());
		saida.writeObject(usuario);
		saida.writeObject(senha);
		saida.writeObject(nome);
		String ip = InetAddress.getLocalHost().toString().split("/")[1];
		saida.writeObject(ip);
		
		return (boolean) entrada.readObject();
	}
	
	public ArrayList<Usuario> listarOnline() throws ClassNotFoundException, IOException {
		saida.writeObject(Protocolos.LIST_USER_ON_LINE.name());
		return (ArrayList<Usuario>) entrada.readObject();
	}
	
	public ArrayList<Partida> listarJogando() throws ClassNotFoundException, IOException {
		saida.writeObject(Protocolos.LIST_USER_PLAYING.name());
		return (ArrayList<Partida>) entrada.readObject();
	}
	
	public void updatePort(String port) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.UPDATE_PORT.name());
		saida.writeObject(port);
		entrada.readObject();
	}
	
	public void entrandoNoHub(Partida pAntiga, Partida pNova) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GAME_HUB.name());
		saida.writeObject(pAntiga);
		saida.writeObject(pNova);
		entrada.readObject();
		
	}
	
	public void startJogo(Partida partida) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GAME_START.name());
		saida.writeObject(partida);
		entrada.readObject();
	}
	
	public void finalizarJogo(Partida partida) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GAME_OVER.name());
		saida.writeObject(partida);
		entrada.readObject();
	}
	
	public void encerrarConexao() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.DISCONNECT.name());
		entrada.readObject();
		
		saida.close();
		entrada.close();
		timer.cancel();
		clienteSocket.close();
	}
	
	public int getQtdCadastrados() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GET_QTD_CADASTRADOS.name());
		int retorno = (int) entrada.readObject();
		entrada.readObject();
		
		return retorno;
	}
	
	public int getQtdOnline() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GET_QTD_ONLINE.name());
		int retorno = (int) entrada.readObject();
		entrada.readObject();
		
		return retorno;
	}
	
	public int getQtdJogando() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GET_QTD_JOGANDO.name());
		return (int) entrada.readObject();
	}
	
	public void enviarMsg(String m) throws IOException, ClassNotFoundException {
		saida.writeObject("Msg");
		saida.writeObject(m);
		
		System.out.println("Já consegui enviar");
		
		String recebido = (String) entrada.readObject();
		
		System.out.println("Recebi");
		
		System.out.println(recebido);
			
	}
	
}

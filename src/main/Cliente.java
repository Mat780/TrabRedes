package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
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
		
		// Cria as variaveis que serao utilizadas
		// para pegar o ip, no caso.
		Path pathfile = Path.of("logs/config.txt");
		Properties properties = new Properties();
		
		try {
			properties.load(Files.newBufferedReader(pathfile));
			ipServer = properties.getProperty("SERVER_IP");
			
		} catch (IOException e) {
			System.err.println("Erro: Arquivo de config.txt não foi encontrado, o programa não executára normalmente sem ele");
		}
		
		// Instancia o socket do cliente, a saida, a entrada alem de estabelecer o timer.
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
					try {
						clienteSocket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		}, 5000, 5000);
	}
	
	// Metodo responsavel pelo logindo usuario.
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
	
	// Metodo responsavel por fazer o registro do usuario, alem da senha e do nome. 
	public boolean register(String usuario, String senha, String nome) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.REGISTER.name());
		saida.writeObject(usuario);
		saida.writeObject(senha);
		saida.writeObject(nome);
		String ip = InetAddress.getLocalHost().toString().split("/")[1];
		saida.writeObject(ip);
		
		return (boolean) entrada.readObject();
	}
	
	// Metodo que lista os usuarios que estao online.
	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> listarOnline() throws ClassNotFoundException, IOException {
		saida.writeObject(Protocolos.LIST_USER_ON_LINE.name());
		return (ArrayList<Usuario>) entrada.readObject();
	}
	
	// Metodo que lista os usuarios que estao jogando.
	@SuppressWarnings("unchecked")
	public ArrayList<InfoPartida> listarJogando() throws ClassNotFoundException, IOException {
		saida.writeObject(Protocolos.LIST_USER_PLAYING.name());
		return (ArrayList<InfoPartida>) entrada.readObject();
	}
	
	// Metodo que atualiza o ip.
	public void updateIp(String ip) throws IOException {
		saida.writeObject(Protocolos.UPDATE_IP.name());
		saida.writeObject(ip);
		try {
			entrada.readObject();
		} catch (ClassNotFoundException e) {
			// Nunca deveria acontecer
		} 
	}
	
	// Metodo que atualiza a porta.
	public void updatePort(String port) throws IOException {
		saida.writeObject(Protocolos.UPDATE_PORT.name());
		saida.writeObject(port);
		try {
			entrada.readObject();			
		} catch (ClassNotFoundException e) {
			// Nunca deveria acontecer
		}
	}
	
	// Metodo que lista os usuarios que estao inativos.
	public void ficarInativo() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.VOLTAR_LIST_ONLINE.name());
		entrada.readObject();
	}
	
	// Metodo que e responsavel por criar um hub para a partida.
	public void criarHub(InfoPartida partida) {
		try {
			saida.writeObject(Protocolos.GAME_HUB.name());
			saida.writeObject(partida);
			entrada.readObject();		
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace(); // Nunca deveria acontecer
		}
	}
	
	// Metodo para indicar que entrou no hub.
	public void entreiEmUmHub() {
		try {
			saida.writeObject(Protocolos.GAME_HUB_ENTER.name());
			entrada.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace(); // Nunca deveria acontecer
		}
	}
	
	// Metodo para indicar que entrou no jogo.
	public void startJogo(InfoPartida partida) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GAME_START.name());
		saida.writeObject(partida);
		entrada.readObject();
	}
	
	// Metodo para indicar que acabou o jogo.
	public void finalizarJogo(InfoPartida partida) throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GAME_OVER.name());
		saida.writeObject(partida);
		entrada.readObject();
	}
	
	// Metodo para indicar que encerrou-se a conexao.
	public void encerrarConexao() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.DISCONNECT.name());
		entrada.readObject();
		
		saida.close();
		entrada.close();
		timer.cancel();
		clienteSocket.close();
	}
	
	// Metodo que devolve quantos usuarios estao cadastrados.
	public int getQtdCadastrados() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GET_QTD_CADASTRADOS.name());
		int retorno = (int) entrada.readObject();
		entrada.readObject();
		
		return retorno;
	}
	
	// Metodo que devolve quantos usuarios estao online.
	public int getQtdOnline() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GET_QTD_ONLINE.name());
		int retorno = (int) entrada.readObject();
		entrada.readObject();
		
		return retorno;
	}
	
	// Metodo que devolve quantos usuarios estao jogando.
	public int getQtdJogando() throws IOException, ClassNotFoundException {
		saida.writeObject(Protocolos.GET_QTD_JOGANDO.name());
		return (int) entrada.readObject();
	}
	
	// Metodo que envia mensagens para verificar a conexao.
	public void enviarMsg(String m) throws IOException, ClassNotFoundException {
		saida.writeObject("Msg");
		saida.writeObject(m);
		
		System.out.println("Já consegui enviar");
		
		String recebido = (String) entrada.readObject();
		
		System.out.println("Recebi");
		
		System.out.println(recebido);
			
	}
	
}

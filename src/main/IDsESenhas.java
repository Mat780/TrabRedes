package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class IDsESenhas {

	private static HashMap<String, String[]> jogadoresRegistrados = new HashMap<>();
	private static HashMap<String, String[]> jogadoresOnline  	  = new HashMap<>();   // INATIVO
	private static HashMap<String, String[]> jogadoresJogando  	  = new HashMap<>();   // ATIVO
	private static ArrayList<InfoPartida> partidasEmAndamento     = new ArrayList<>(); // ATIVO
	
	private static String filepathLog = "logs/game.log";
	private static String filepath = "logs/usuarios.txt";
	
	private static boolean isInicializado = false;
	
	private IDsESenhas() {} // Construtor Inexistente
	
	public static synchronized boolean register(String usuario, String senha, String nome, String ip, String port) throws IOException {
		
		boolean isUsuarioCadastrado = (jogadoresRegistrados.get(usuario)) != null;
		
		if (isUsuarioCadastrado) return false;
		
		FileWriter escritor = new FileWriter(filepath, true);
		
		String[] infoFinal = new String[4];
		infoFinal[0] = senha;
		infoFinal[1] = nome;
		infoFinal[2] = ip;
		infoFinal[3] = port;
		jogadoresRegistrados.put(usuario, infoFinal);
		
		String registro = usuario + "~" + senha + "~" + nome + "~" + ip + "~" + port + "\n";
		
		escritor.append(registro);            
		escritor.close();
		
		Usuario usr = new Usuario(usuario, senha, nome, ip, port);
	
		makeLog("Usuario " + usr.getUsuario() + " realizou cadastro");
		
		return true;
	}
	
	public static synchronized String[] login(String usuario, String senha) throws IOException {
		
		String[] cadastro = jogadoresRegistrados.get(usuario);
		
		String[] naoFoiEncontrado = new String[1];
		naoFoiEncontrado[0] = "Nao foi encontrado nenhum usuario";
		
		String[] senhaIncorreta = new String[1];
		senhaIncorreta[0] = "Senha incorreta";
		
		if (cadastro == null) return naoFoiEncontrado;
		
		if (cadastro[0].equals(senha)) {
			jogadoresOnline.put(usuario, cadastro);
			Usuario usr = new Usuario(usuario, senha, cadastro[1], cadastro[2], cadastro[3]);
			String str = usr.getUsuario();
			makeLog("Usuario " + str + " conectou-se");
			makeLog("Usuario " + str + " tornou-se INATIVO");
			return cadastro;
		}
		else return senhaIncorreta;
	}
	
	public static synchronized void updateIp(String usuario, String ip) {
		String[] infoUsr = jogadoresOnline.get(usuario);
		infoUsr[2] = ip;
		jogadoresOnline.put(usuario, infoUsr);
	}
	
	public static synchronized void updatePort(String usuario, String port) {
		String[] infoUsr = jogadoresOnline.get(usuario);
		infoUsr[3] = port;
		jogadoresOnline.put(usuario, infoUsr);
	}

	public static synchronized void ficarInativo(Usuario usuario) throws IOException {
		String[] info = jogadoresJogando.remove(usuario.getUsuario());
		
		jogadoresOnline.put(usuario.getUsuario(), info);
		jogadoresJogando.remove(usuario.getUsuario());
		makeLog("Usuario " + usuario.getUsuario() + " tornou-se INATIVO");
	}
	
	public static synchronized void disconnect(Usuario usuario, boolean isDead) {
		
		String usrUsuario = usuario.getUsuario();
		String[] infoUsr = jogadoresOnline.get(usuario.getUsuario());
		
		if(infoUsr == null) {
			for (int i = 0; i < partidasEmAndamento.size(); i++) {
				
				String usrJ1 = partidasEmAndamento.get(i).getJ1().getUsuario();
				String usrJ2 = partidasEmAndamento.get(i).getJ2().getUsuario();
				String usrJ3 = partidasEmAndamento.get(i).getJ3() == null ? null : partidasEmAndamento.get(i).getJ3().getUsuario();
				String usrJ4 = partidasEmAndamento.get(i).getJ4() == null ? null : partidasEmAndamento.get(i).getJ4().getUsuario();
				
				if(usrUsuario.equals(usrJ1) || usrUsuario.equals(usrJ2) || usrUsuario.equals(usrJ3) || usrUsuario.equals(usrJ4)) {
					partidasEmAndamento.remove(i);
					break;
				}
			}
		}
		
		try {
			if(isDead) makeLog("Usuario " + usuario.getUsuario() + " não responde");
			else makeLog("Usuario " + usuario.getUsuario() + " desconectou-se da rede");	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jogadoresOnline.remove(usuario.getUsuario());
		jogadoresJogando.remove(usuario.getUsuario());
	}
	
	public static synchronized void refreshCadastro() throws FileNotFoundException {
		BufferedReader leitor = new BufferedReader(new FileReader(filepath));
		
		String linha;
		
		try {
			while((linha = leitor.readLine()) != null) {
				String[] infos = linha.split("~", 0);
				String[] infoFinal = new String[4];
				infoFinal[0] = infos[1]; // Senha
				infoFinal[1] = infos[2]; // Nome
				infoFinal[2] = infos[3]; // Ip
				infoFinal[3] = infos[4]; // Port
				jogadoresRegistrados.put(infos[0], infoFinal);
			}
			leitor.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static synchronized void criarHub(InfoPartida partida, Usuario usuario) {
		estouEntrandoEmUmHub(usuario);
		partidasEmAndamento.add(partida);
	}
	
	public static synchronized void estouEntrandoEmUmHub(Usuario usuario) {
		String[] infoUsr = jogadoresRegistrados.get(usuario.getUsuario());
		jogadoresJogando.put(usuario.getUsuario(), infoUsr);
		jogadoresOnline.remove(usuario.getUsuario());
		
		try {
			makeLog("Usuario " + usuario.getUsuario() + " tornou-se ATIVO");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void startPartida(InfoPartida partida) throws IOException {
		
		String str1 = partida.getJ1().getUsuario();
		String str2 = partida.getJ2().getUsuario();
		String str3 = "";
		String str4 = "";
		
		if(partida.getJ3() != null) {
			str3 = partida.getJ3().getUsuario();
			str4 = partida.getJ4().getUsuario();
		}
		
		if (str3.isEmpty() || str3.isBlank()) makeLog("Usuario " + str1 + " e " + str2 + ": PLAYING");
		else makeLog("Usuario " + str1 + ", " + str2 + ", " + str3 + " e " + str4 + ": PLAYING");
				
	}
	
	public static synchronized void finalizarPartida(InfoPartida partida, Usuario usuario) throws IOException {
		partidasEmAndamento.remove(partida);
		
		String[] infoUsr = jogadoresJogando.get(usuario.getUsuario());
		String string = usuario.getUsuario();
		makeLog("Usuario " + string + " tornou-se INATIVO");
		
		jogadoresOnline.put(usuario.getUsuario(), infoUsr);
		jogadoresJogando.remove(usuario.getUsuario());
		
	}
	
	public static synchronized ArrayList<Usuario> listOnline() throws UnknownHostException {
		ArrayList<Usuario> arrayUsuarios = new ArrayList<>();
		for (Entry<String, String[]> entry : jogadoresOnline.entrySet()) {
			arrayUsuarios.add(new Usuario(entry.getKey(), entry.getValue()[0] , entry.getValue()[1], entry.getValue()[2], entry.getValue()[3]));
		}
		
		return arrayUsuarios;
	}
	
	public static synchronized ArrayList<InfoPartida> listJogando() {
		return partidasEmAndamento;
	}
	
	public static synchronized int getQtdRegistrado() {
		return jogadoresRegistrados.size();
	}
	
	public static synchronized int getQtdOnline() {
		return jogadoresOnline.size() + jogadoresJogando.size();
	}
	
	public static synchronized int getQtdJogando() {
		return jogadoresJogando.size();
	}
	
	private static synchronized void makeLog(String msg) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now(); 
	    System.out.println(dtf.format(now) + ": " + msg);
	    
	    FileWriter escritorLog = new FileWriter(filepathLog, true);
		escritorLog.append(dtf.format(now) + ": " + msg + "\n");
		escritorLog.close();
	}
	
	public static void inicializar() {
		if (isInicializado == false) {
			try {
				refreshCadastro();
				File fileRegister = new File(filepath);
				File fileLog 	  = new File(filepathLog);
				
				if(fileLog.exists() == false && (fileLog.createNewFile() == false)) {
					System.out.println("Não foi possivel criar o arquivo de Log");
				}
				
				if(fileRegister.exists() == false && fileRegister.createNewFile() == false) {
					System.out.println("Não foi possivel criar o arquivo de registro");
				}
				
				isInicializado = true;			
				
			} catch (IOException e) {
				System.out.println("Erro: Ao inicializar o IDsESenhas");
				e.printStackTrace();
			}
		} else {
			throw new IllegalAccessError("Erro: IDsESenhas tentou ser inicializado 2x");
		}
		
	}
	
}

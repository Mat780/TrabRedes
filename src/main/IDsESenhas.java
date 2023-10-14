package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
	private static ArrayList<Partida> partidasEmAndamento         = new ArrayList<>(); // ATIVO
	
	private static String filepathLog = "logs/game.log";
	private static String filepath = "logs/usuarios.txt";
	
	private static boolean isInicializado = false;
	
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
		
		String usr = Usuario.staticToString(usuario, ip, port);
		makeLog("Usuario " + usr + " realizou cadastro");
		
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
			System.out.println(cadastro[2]);
			String str = Usuario.staticToString(usuario, cadastro[2], cadastro[3]);
			makeLog("Usuario " + str + " conectou-se");
			makeLog("Usuario " + str + " tornou-se INATIVO");
			return cadastro;
		}
		else return senhaIncorreta;
	}
	
	public static synchronized void updatePort(String usuario, String port) {
		String[] infoUsr = jogadoresOnline.get(usuario);
		infoUsr[3] = port;
		jogadoresOnline.put(usuario, infoUsr);
	}

	public static synchronized void disconnect(String usuario, boolean isDead) throws IOException {
		
		String[] infoUsr = jogadoresOnline.get(usuario);
		if(infoUsr == null) infoUsr = jogadoresJogando.get(usuario);
		
		if(isDead) makeLog("Usuario " + Usuario.staticToString(usuario, infoUsr[2], infoUsr[3]) + " não responde");
		else makeLog("Usuario " + Usuario.staticToString(usuario, infoUsr[2], infoUsr[3]) + " desconectou-se da rede");
		
		jogadoresOnline.remove(usuario);
		jogadoresJogando.remove(usuario);
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
	
	public static synchronized void jogarPartida(Partida partidaAntiga, Partida partidaNova, Usuario usuario) throws IOException {
		String[] usuarioEstavaJogando = jogadoresJogando.get(usuario.getUsuario());
		
		if(usuarioEstavaJogando != null) { //
			partidasEmAndamento.remove(partidaAntiga);
			
		} else {
			String[] infoUsr = jogadoresOnline.get(usuario.getUsuario());
			jogadoresJogando.put(usuario.getUsuario(), infoUsr);
			jogadoresOnline.remove(usuario.getUsuario());
			makeLog("Usuario " + Usuario.staticToString(usuario.getUsuario(), infoUsr[2], infoUsr[3]) + " tornou-se ATIVO");
		}
		
		int partidaNovaJaCadastrada = partidasEmAndamento.indexOf(partidaNova);
		if (partidaNovaJaCadastrada == -1) partidasEmAndamento.add(partidaNova);
	}
	
	public static synchronized void startPartida(Partida partida) throws IOException {
		
		Usuario usr1 = partida.getJ1();
		Usuario usr2 = partida.getJ2();
		Usuario usr3 = partida.getJ3();
		Usuario usr4 = partida.getJ4();
		
		String str1 = Usuario.staticToString(usr1.getUsuario(), usr1.getIP(), usr1.getPort());
		String str2 = Usuario.staticToString(usr2.getUsuario(), usr2.getIP(), usr2.getPort());;
		String str3 = "";
		String str4 = "";
		
		if(usr3 != null) {
			str3 = Usuario.staticToString(usr3.getUsuario(), usr3.getIP(), usr3.getPort());
			str4 = Usuario.staticToString(usr4.getUsuario(), usr4.getIP(), usr4.getPort());
		}
		
		if (str3.isEmpty() || str3.isBlank()) makeLog("Usuario " + str1 + " e " + str2 + ": PLAYING");
		else makeLog("Usuario " + str1 + ", " + str2 + ", " + str3 + " e " + str4 + ": PLAYING");
		
	}
	
	public static synchronized void finalizarPartida(Partida partida, Usuario usuario) throws IOException {
		partidasEmAndamento.remove(partida);
		
		String[] infoUsr = jogadoresJogando.get(usuario.getUsuario());
		String string = Usuario.staticToString(usuario.getUsuario(), infoUsr[2], infoUsr[3]);
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
	
	public static synchronized ArrayList<Partida> listJogando() {
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

package main;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Usuario implements Serializable{
	protected String nome;
	protected String usuario;
	private String senha;
	private String ip;
	private String port;
	
	public Usuario(String usuario, String senha, String nome, String ip, String port) throws UnknownHostException {
		setNome(nome);
		setUsuario(usuario);
		setSenha(senha);
		setIP(ip);
		setPort(port);
	}
	
	private void setNome(String nome) {
		this.nome = nome;
	}
	
	private void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	private void setSenha(String senha) {
		this.senha = senha;
	}
	
	private void setIP(String ip) throws UnknownHostException {
		if (ip == null) this.ip = InetAddress.getLocalHost().toString();
		else this.ip = ip;
	}
	
	private void setPort(String port) {
		this.port = port;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public String getSenha() {
		return senha;
	}
	
	public String getIP() {
		return ip;
	}

	public String getPort() {
		return port;
	}
	
	private static String darEspacamento(int espacoJaOcupado, int qtdEspacosTotal) {
		
		String s = "";
		
		for(int i = 0; i < (qtdEspacosTotal - espacoJaOcupado); i++) {
			s += " ";
		}
		
		return s;
	}
	
	public static String staticToString(String usuario, String ip, String port) {
		return usuario + " IP: " + ip + " Port: " + port;
	}
	
	@Override
	public String toString() {
		return "  Usuario: " + usuario + darEspacamento(usuario.length(), 60) + " IP: " + getIP() + darEspacamento(ip.length(), 15) + " Port: " + port + darEspacamento(port.length(), 6);
	}

}

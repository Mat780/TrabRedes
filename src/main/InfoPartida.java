package main;

import java.io.Serializable;

import excecoes.ExcecaoPartidaCheia;

public class InfoPartida implements Serializable {

	private static final long serialVersionUID = -4874702180941374579L;
	private Usuario jogador1;
	private Usuario jogador2;
	private Usuario jogador3;
	private Usuario jogador4;
	
	public InfoPartida(Usuario usr1, Usuario usr2) {
		this.jogador1 = usr1;
		this.jogador2 = usr2;
	}
	
	public void adicionarJogador(Usuario j) throws ExcecaoPartidaCheia {
		
		if (jogador1 == null) { 
			this.jogador1 = j;
			
		} else if (jogador2 == null) { 
			this.jogador2 = j;
		
		} else if (jogador3 == null) { 
			this.jogador3 = j;
		
		} else if (jogador4 == null) { 
			this.jogador4 = j;
		
		} else throw new ExcecaoPartidaCheia();
		
	}
	
	public void retirarJogador(Usuario j) {
		if 		(jogador4.equals(j)) jogador4 = null;
		else if (jogador3.equals(j)) jogador3 = null;
		else if (jogador2.equals(j)) jogador2 = null;
		else if (jogador1.equals(j)) jogador1 = null;
		
	}
	
	public Usuario getJ1() {
		return jogador1;
	}
	
	public Usuario getJ2() {
		return jogador2;
	}
	
	public Usuario getJ3() {
		return jogador3;
	}
	
	public Usuario getJ4() {
		return jogador4;
	}
	
	@Override
	public String toString() {
		String s = "";
		
		if (jogador1 != null) s += jogador1.getUsuario() + "(" + jogador1.getIP() + ":" + jogador1.getPort() + ")";
		if (jogador2 != null) s += " X " + jogador2.getUsuario() + "(" + jogador2.getIP() + ":" + jogador2.getPort() + ")";
		if (jogador3 != null) s += " X " + jogador3.getUsuario() + "(" + jogador3.getIP() + ":" + jogador3.getPort() + ")";
		if (jogador4 != null) s += " X " + jogador4.getUsuario() + "(" + jogador4.getIP() + ":" + jogador4.getPort() + ")";
		
		return s;
	}
}

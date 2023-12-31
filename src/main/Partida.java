package main;

import java.io.Serializable;

import excecoes.ExcecaoPartidaCheia;
import jogo.Castelo;
import jogo.CavaleiroPeca;
import jogo.MagoPeca;
import jogo.Peca;

public class Partida implements Serializable {
	
	private static final long serialVersionUID = 6813387199836623368L;
	
	private Usuario jogador1;
	private Usuario jogador2;
	private Usuario jogador3;
	private Usuario jogador4;
	private Castelo casteloAliado;
	private Castelo casteloInimigo;
	
	public Partida(Usuario j1, Usuario j2) {
		setJ1(j1);
		setJ2(j2);
		setJ3(null);
		setJ4(null);
		casteloAliado = new Castelo(10, 0, new CavaleiroPeca(), new MagoPeca());
		casteloInimigo = new Castelo(10, 0, new CavaleiroPeca(), new MagoPeca());
	}
	
	public void setJ1(Usuario j1) {
		jogador1 = j1;
	}
	
	public void setJ2(Usuario j2) {
		jogador2 = j2;
	}
	
	public void setJ3(Usuario j3) {
		jogador3 = j3;
	}
	
	public void setJ4(Usuario j4) {
		jogador4 = j4;
	}
	
	public void setPecaJ1(Peca p1, Peca p2) {
		casteloAliado.trocaPecasJ1(p1, p2);
	}
	
	public void setPecaJ2(Peca p1, Peca p2) {
		casteloInimigo.trocaPecasJ1(p1, p2);
	}
	
	public void setPecaJ3(Peca p1, Peca p2) {
		casteloAliado.trocaPecasJ2(p1, p2);
	}
	
	public void setPecaJ4(Peca p1, Peca p2) {
		casteloInimigo.trocaPecasJ2(p1, p2);
	}

	public void setCasteloAliado(Castelo aliado) {
		this.casteloAliado = aliado;
	}
	
	public void setCasteloInimigo(Castelo inimigo) {
		this.casteloInimigo = inimigo;
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
	
	public Castelo getCasteloJ1J3() {
		return casteloAliado;
	}
	
	public Castelo getCasteloJ2J4() {
		return casteloInimigo;
	}
	
	// Metodo que adiciona o jogador da partida mediante o parametro.
	public void adicionarJogador(Usuario j) throws ExcecaoPartidaCheia {
		
		if (jogador1 == null) { 
			setJ1(j); 
		}
		else if (jogador2 == null) { 
			setJ2(j); 
		}
		else if (jogador3 == null) { 
			setJ3(j);
		}
		else if (jogador4 == null) { 
			setJ4(j); 
		}
		else throw new ExcecaoPartidaCheia();
		
	}
	
	// Metodo que retira o usuario.
	public void retirarJogador(Usuario j) {
		if (jogador4.equals(j)) setJ4(null);
		else if (jogador3.equals(j)) setJ3(null);
		else if (jogador2.equals(j)) setJ2(null);
		else if (jogador1.equals(j)) setJ1(null);
		
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

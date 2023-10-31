package jogo;

import java.io.Serializable;

public abstract class Peca implements Serializable {

	private static final long serialVersionUID = -3394292443815323189L;

	private int atributoDaEsq; 
	private int atributoDaDir; 
	private int nivel; 
	private int experiencia; 
	private int energia; 
	private int quandoAtacar;
	
	protected Peca(int atributoDaEsq, int atributoDaDir, int experiencia, int energia, int quandoAtacar) {
		setAtributoDaEsq(atributoDaEsq);
		setAtributoDaDir(atributoDaDir);
		setNivel(1);
		setExperiencia(experiencia);
		setEnergia(energia);
		setQuandoAtacar(quandoAtacar);
	}
	
	protected void setAtributoDaEsq(int atributoDaEsq) {
		this.atributoDaEsq = atributoDaEsq;
	}
	
	protected void setAtributoDaDir(int atributoDaDir) {
		this.atributoDaDir = atributoDaDir;
	}
	
	protected void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	protected void setExperiencia(int experiencia) {
		if (experiencia > 6) experiencia = 6;
		this.experiencia = experiencia;
	}
	
	protected void setEnergia(int energia) {
		if      (energia < 0) 			 energia = 0;
		else if (energia > quandoAtacar) energia = quandoAtacar;
		
		this.energia = energia;
	}
	
	protected void setQuandoAtacar(int quandoAtacar) {
		this.quandoAtacar = quandoAtacar;
	}
	
	public int getAtributoDaEsq() {
		return atributoDaEsq;
	}
	
	public int getAtributoDaDir() {
		return atributoDaDir;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public int getExperiencia() {
		return experiencia;
	}
	
	public int getEnergia() {
		return energia;
	}
	
	public int getQuandoAtacar() {
		return quandoAtacar;
	}
	
	// Metodo que seta os valores de energia e experiencia da peca.
	public void receberJogada(int energia, int exp) {
		setEnergia(getEnergia() + energia);
		setExperiencia(getExperiencia() + exp);
	}
	
	// Metodos que sao implementados nas classes filhas.
	public abstract int getID();
	
	public abstract void atacar(Castelo casteloInimigo, String jogador);
	
	public abstract void defender(Castelo casteloAliado, String jogador);
	
	public abstract void aumentarNivel(Castelo casteloInimigo, String jogador);
	
	public abstract Peca clonarPeca();
	
}

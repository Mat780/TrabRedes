package jogo;

import javax.swing.ImageIcon;

public abstract class Peca {
	protected int atributoDaEsq;
	protected int atributoDaDir;
	protected int nivel;
	protected int experiencia;
	protected int energia;
	protected int quandoAtacar;
	private ImageIcon icone;
	
	protected Peca(int atributoDaEsq, int atributoDaDir, int experiencia, int energia, int quandoAtacar, String icone) {
		setAtributoDaEsq(atributoDaEsq);
		setAtributoDaDir(atributoDaDir);
		setNivel(1);
		setExperiencia(experiencia);
		setEnergia(energia);
		setQuandoAtacar(quandoAtacar);
		this.icone = new ImageIcon("imagens/" + icone);
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
		this.experiencia = experiencia;
	}
	
	protected void setEnergia(int energia) {
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
	
	public ImageIcon getIcon() {
		return icone;
	}
	
	public void atacar(int energia, Castelo casteloInimigo) {
		// Propositalmente vazio
		System.out.println("Atacar não foi implementado nesta peca");
	}
	
	public void aumentarNivel(int experiencia, Castelo casteloInimigo) {
		// Implementa nos filhos
	}
	
}

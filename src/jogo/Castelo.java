package jogo;

import java.io.Serializable;

public class Castelo implements Serializable {
	
	private static final long serialVersionUID = -360944243657702826L;
	private int vida;
	private int muro;
	private Peca peca1J1;
	private Peca peca2J1;
	private Peca peca1J2 = null;
	private Peca peca2J2 = null;
	private boolean quatroJogadores = false;
	
	// O castelo (ou a base) possui como atributos as suas pecas aliadas,
	// por isso estao em seu construtor.
	public Castelo(int vida, int muro, Peca peca1, Peca peca2) {
		setPeca1J1(peca1);
		setPeca2J1(peca2);
		setVida(vida);
		setMuro(muro);
	}
	
	private void setPeca1J1(Peca peca) {
		this.peca1J1 = peca;
	}
	
	private void setPeca2J1(Peca peca) {
		this.peca2J1 = peca;
	}

	private void setPeca1J2(Peca peca) {
		this.peca1J2 = peca;
	}
	
	private void setPeca2J2(Peca peca) {
		this.peca2J2 = peca;
	}
	
	private void setVida(int vida) {
		this.vida = vida;
	}
	
	// Set para definir a altura do muro.
	private void setMuro(int muro) {
		int muroMaximo = 6;
		
		if (quatroJogadores) muroMaximo *= 2;
		if (muro < 0) muro = 0;
		
		if (muro >= muroMaximo) {
			this.muro = muroMaximo;	
			
		} else {
			this.muro = muro;
		}
	}
	
	// Metodo para a implementacao de 4 jogadores.
	public void modoQuatroJogadores() {
		quatroJogadores = true;
		vida = vida * 2;
	}

	public int getVida() {
		return vida;
	}
	
	public int getMuro() {
		return muro;
	}
	
	public Peca getPeca1J1() {
		return peca1J1;
	}
	
	public Peca getPeca2J1() {
		return peca2J1;
	}
	
	public Peca getPeca1J2() {
		return peca1J2;
	}
	
	public Peca getPeca2J2() {
		return peca2J2;
	}
	
	// Define, no castelo, quem vai atacar (isso no caso dos
	// 2 metodos troca) para nao houver um conflito e nem
	// deadlock.
	public void trocaPecasJ1(Peca p1, Peca p2) {
		setPeca1J1(p1);
		setPeca2J1(p2);
	}
	
	public void trocaPecasJ2(Peca p1, Peca p2) {
		setPeca1J2(p1);
		setPeca2J2(p2);
	}
	
	// Metodo para construir o muro.
	public void constroiMuro(int constroiMuro) {
		setMuro(constroiMuro + muro);
	}
	
	// Metodo para quando uma peca ataca o muro
	// diminuindo sua altura.
	public void atacarMuro(int dano) {
		setMuro(muro - dano);
	}
	
	// Metodo responsavel por diminuir a vida da base
	// (do castelo) ja que foi atacado (dano).
	public void atacarPontosDeVida(int dano) {
		setVida(vida - dano);
	}
	
}

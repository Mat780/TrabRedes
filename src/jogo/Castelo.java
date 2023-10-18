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
	
	private void setMuro(int muro) {
		if (muro >= 6) {
			this.muro = 6;	
			
		} else {
			this.muro = muro;
		}
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
	
	public void trocaPecasJ1(Peca p1, Peca p2) {
		setPeca1J1(p1);
		setPeca2J1(p2);
	}
	
	public void trocaPecasJ2(Peca p1, Peca p2) {
		setPeca1J2(p1);
		setPeca2J2(p2);
	}
	
	public void constroiMuro(int constroiMuro) {
		setMuro(constroiMuro + muro);
	}
	
	public void atacarMuro(int dano) {
		this.muro = this.muro - dano;
	}
	
	public void atacarPontosDeVida(int dano) {
		this.vida = this.vida - dano;
	}
}

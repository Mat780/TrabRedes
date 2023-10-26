package jogo;

import controladoras.ControladorPrincipal;

public class CavaleiroPeca extends Peca {
	
	private static final long serialVersionUID = -6458173219840995194L;

	private int id = 0;
	
	public CavaleiroPeca() {
		super(3, 3, 0, 0, 3);
	}
	
	@Override
	public void atacar(Castelo casteloInimigo, String jogador) {
		if (getQuandoAtacar() - getEnergia() <= 0) {
			
			if (casteloInimigo.getMuro() > 0) {
				casteloInimigo.atacarMuro(getAtributoDaDir());
				ControladorPrincipal.mensagemDeAtaqueMuro(this.toString(), jogador, getAtributoDaDir());

			} else {
				casteloInimigo.atacarPontosDeVida(getAtributoDaEsq());
				ControladorPrincipal.mensagemDeAtaqueVida(this.toString(), jogador, getAtributoDaEsq());
			}
			
			setExperiencia(getExperiencia() + 2);
			setEnergia(0);
		}
	}
	
	@Override
	public void defender(Castelo casteloAliado, String jogador) {
		// Cavaleiro não se defende
	}
	
	@Override
	public void aumentarNivel(Castelo casteloInimigo, String jogador) {
		if (getExperiencia() >= 6) {
			
			if (getNivel() == 1) {
				setNivel(2);
				setAtributoDaEsq(5);
				setAtributoDaDir(5);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else if (getNivel() == 2) {
				setNivel(3);
				setAtributoDaEsq(7);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
				ControladorPrincipal.mensagemDeBomba(this.toString(), jogador);
			}
			
			setExperiencia(0);
		}
	}
	
	public Peca clonarPeca() {
		return new CavaleiroPeca();
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Cavaleiro";
	}
}

package jogo;

import controladoras.ControladorPrincipal;

public class ArqueiraPeca extends Peca {

	private static final long serialVersionUID = 8270194711572710165L;
	
	private int id = 2;

	public ArqueiraPeca() {
		super(3, 1, 0, 0, 4);
	}
	
	@Override
	public void atacar(Castelo casteloInimigo, String jogador) {
		if (getQuandoAtacar() - getEnergia() <= 0) {
			
			if (casteloInimigo.getMuro() < 3) {
				casteloInimigo.atacarPontosDeVida(getAtributoDaEsq());
				ControladorPrincipal.mensagemDeAtaqueVida(this.toString(), jogador, getAtributoDaEsq());
				
			} else {
				casteloInimigo.atacarMuro(getAtributoDaDir());
				ControladorPrincipal.mensagemDeAtaqueMuro(this.toString(), jogador, getAtributoDaDir());
			}
			
			setExperiencia(getExperiencia() + 2);
			setEnergia(0);
			
		} 
	}
	
	@Override
	public void defender(Castelo casteloAliado, String jogador) {
		// Arqueira não se defende
	}
	
	@Override
	public void aumentarNivel(Castelo casteloInimigo, String jogador) {
		
		if (getExperiencia() >= 6) {
			
			if (getNivel() == 1) {
				setNivel(2);
				setQuandoAtacar(3);
				setAtributoDaEsq(4);
				setAtributoDaDir(2);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else if (getNivel() == 2) {
				setNivel(3);
				setAtributoDaEsq(6);
				setAtributoDaDir(3);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
				ControladorPrincipal.mensagemDeBomba(this.toString(), jogador);
			}
			
			setExperiencia(0);
		}
	}
	
	public Peca clonarPeca() {
		return new ArqueiraPeca();
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Arqueira";
	}

}

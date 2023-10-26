package jogo;

import javax.swing.JOptionPane;

import controladoras.ControladorPrincipal;

public class MagoPeca extends Peca {
	
	private static final long serialVersionUID = 5706906505976683719L;

	private int id = 1;
	
	public MagoPeca() {
		super(2, 2, 0, 0, 5);
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
			
			
			if (casteloInimigo.getMuro() < 6) {
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
		// Mago não defende
	}
	
	@Override
	public void aumentarNivel(Castelo casteloInimigo, String jogador) {
		
		if (getExperiencia() >= 6) {
			
			if (getNivel() == 1) {
				setNivel(2);
				setQuandoAtacar(4);
				setAtributoDaEsq(3);
				setAtributoDaDir(3);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);

			} else if (getNivel() == 2) {
				setNivel(3);
				setAtributoDaDir(5);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
				ControladorPrincipal.mensagemDeBomba(this.toString(), jogador);
				
			}
			
			setExperiencia(0);
		}
		
	}
	
	@Override
	public Peca clonarPeca() {
		return new MagoPeca();
	}
	
	@Override
	public String toString() {
		return "Mago";
	}

	@Override
	public int getID() {
		return id;
	}
}

package jogo;

import javax.swing.JOptionPane;

import controladoras.ControladorPrincipal;

public class ConstrutorPeca extends Peca {

	private static final long serialVersionUID = 5137619455347318467L;

	private int id = 5;
	
	public ConstrutorPeca() {
		super(1, 3, 0, 0, 4);
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
		if (getQuandoAtacar() - getEnergia() <= 0) {
			casteloAliado.constroiMuro(2);
			JOptionPane.showMessageDialog(null, "Construtor do jogador " + jogador + " aumentou o muro em 2");		
		}		
	}
	
	@Override
	public void aumentarNivel(Castelo casteloInimigo, String jogador) {
		if (getExperiencia() >= 6) {
			
			if (getNivel() == 1) {
				setNivel(2);
				setAtributoDaEsq(2);	
				setAtributoDaDir(5);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else if (getNivel() == 2) {
				setNivel(3);
				setAtributoDaEsq(4);	
				setQuandoAtacar(3);
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
		return new ConstrutorPeca();
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Construtor";
	}
	
}

package jogo;

import controladoras.ControladorPrincipal;

public class ArqueiraPeca extends Peca {

	private static final long serialVersionUID = 8270194711572710165L;
	
	private int id = 2;

	public ArqueiraPeca() {
		super(3, 1, 0, 0, 4);
	}
	
	// Metodo responsavel pelo ataque da peca com as suas caracteristicas de ataque.
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
	
	// Metodo responsavel pela defesa que esta peca nao possui como caracteristica.
	@Override
	public void defender(Castelo casteloAliado, String jogador) {
		// Arqueira não se defende
	}
	
	// Metodo responsavel por aumentar o nivel setando valores dos atributos quando
	// corresponde as caracteristicas definidas no documento.
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
	
	// Metodo que clona a peca.
	public Peca clonarPeca() {
		return new ArqueiraPeca();
	}
	
	// Metodo para pegar o id da peca.
	@Override
	public int getID() {
		return id;
	}
	
	// Metodo toString para especificar a peca.
	@Override
	public String toString() {
		return "Arqueira";
	}

}

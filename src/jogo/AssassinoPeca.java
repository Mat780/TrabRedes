package jogo;

import javax.swing.JOptionPane;

import controladoras.ControladorPrincipal;

public class AssassinoPeca extends Peca {

	private static final long serialVersionUID = 8872587363780244814L;

	private int id = 4;
	
	public AssassinoPeca() {
		super(1, 1, 0, 0, 3);
	}
	
	// Metodo responsavel pelo ataque da peca com as suas caracteristicas de ataque.
	// Por causa das caracteristicas do assassino, e necessario varias verificacoes e
	// saber qual ira atacar primeiro para noa ter deadlock.
	// Alem disso, tem uma codificacao para a tentativa de implementacao com 4 jogadores.
	@Override
	public void atacar(Castelo casteloInimigo, String jogador) {
		if (getQuandoAtacar() - getEnergia() <= 0) {
			
			String msg = "Assassino do jogador " + jogador + " atrasou em " + getAtributoDaEsq() + " o ";
			boolean souJ1ouJ3 = false;
			
			if (jogador.equals("J1") || jogador.equals("J3"))
				souJ1ouJ3 = true;
			
			Peca peca1 = casteloInimigo.getPeca1J1();
			Peca peca2 = casteloInimigo.getPeca2J1();
			Peca peca3 = casteloInimigo.getPeca1J2();
			Peca peca4 = casteloInimigo.getPeca2J2();
				
			int difEneP1 = peca1.getQuandoAtacar() - peca1.getEnergia();
			int difEneP2 = peca2.getQuandoAtacar() - peca2.getEnergia();
			int difEneP3 = (peca3 != null) ? peca3.getQuandoAtacar() - peca3.getEnergia() : 999;
			int difEneP4 = (peca4 != null) ? peca4.getQuandoAtacar() - peca3.getEnergia() : 999;
			
			if (difEneP1 < difEneP2 && difEneP1 < difEneP3 && difEneP1 < difEneP4) {
				peca1.setEnergia(peca1.getEnergia() - getAtributoDaEsq());		
				msg = msg + peca1.toString() + " do jogador ";
				
				if (souJ1ouJ3) msg = msg + "J2";
				else msg = msg + "J1";
			}
			
			else if (difEneP2 < difEneP3 && difEneP2 < difEneP4) {
				peca2.setEnergia(peca2.getEnergia() - getAtributoDaEsq());
				msg = msg + peca2.toString() + " do jogador ";
				
				if (souJ1ouJ3) msg = msg + "J2";
				else msg = msg + "J1";
			}
			
			else if (difEneP3 < difEneP4) {
				peca3.setEnergia(peca3.getEnergia() - getAtributoDaEsq());
				msg = msg + peca3.toString() + " do jogador ";
				
				if (souJ1ouJ3) msg = msg + "J4";
				else msg = msg + "J3";
			}
			
			else {
				peca4.setEnergia(peca4.getEnergia() - getAtributoDaEsq());
				msg = msg + peca4.toString() + " do jogador ";
				
				if (souJ1ouJ3) msg = msg + "J4";
				else msg = msg + "J3";
			}
			
			JOptionPane.showMessageDialog(null, msg);

			casteloInimigo.atacarPontosDeVida(getAtributoDaDir());
			ControladorPrincipal.mensagemDeAtaqueVida(this.toString(), jogador, getAtributoDaDir());
			
			setExperiencia(getExperiencia() + 2);
			setEnergia(0);
			
		}
	}
	
	// Metodo responsavel pela defesa que esta peca nao possui como caracteristica.
	@Override
	public void defender(Castelo casteloAliado, String jogador) {
		// Assassino não se defende
	}
	
	// Metodo responsavel por aumentar o nivel setando valores dos atributos quando
	// corresponde as caracteristicas definidas no documento.
	@Override
	public void aumentarNivel(Castelo casteloInimigo, String jogador) {
		if (getExperiencia() >= 6) {
			
			if (getNivel() == 1) {
				setNivel(2);
				setAtributoDaDir(2);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else if (getNivel() == 2) {
				setNivel(3);
				setAtributoDaEsq(2);
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
		return new AssassinoPeca();
	}

	// Metodo para pegar o id da peca.
	@Override
	public int getID() {
		return id;
	}
	
	// Metodo toString para especificar a peca.
	@Override
	public String toString() {
		return "Assassino";
	}
}

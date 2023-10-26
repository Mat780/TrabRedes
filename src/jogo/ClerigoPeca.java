package jogo;

import javax.swing.JOptionPane;

import controladoras.ControladorPrincipal;

public class ClerigoPeca extends Peca {
	
	private static final long serialVersionUID = -1659378513363957100L;

	private int id = 3;
	
	public ClerigoPeca() {
		super(1, 2, 0, 0, 4);
	}
	
	@Override
	public void atacar(Castelo casteloInimigo, String jogador) {
		// Clerigo não ataca
	}
	
	@Override
	public void defender(Castelo casteloAliado, String jogador) {
		if (getQuandoAtacar() - getEnergia() <= 0) {
			
			casteloAliado.atacarPontosDeVida(getAtributoDaEsq() * -1);
			JOptionPane.showMessageDialog(null, "Clerigo do jogador " + jogador + " curou o rei alidado em " + getAtributoDaEsq());
			
			Peca peca1 = casteloAliado.getPeca1J1();
			Peca peca2 = casteloAliado.getPeca2J1();
			Peca peca3 = casteloAliado.getPeca1J2();
			Peca peca4 = casteloAliado.getPeca2J2();
			
			boolean souJ1ouJ3 = false;
			if (jogador.equals("J1") || jogador.equals("J3")) souJ1ouJ3 = true;
			
			String msg = "Clerigo do jogador " + jogador + " deu energia ao ";
			
			int eneP1 = peca1.getEnergia();
			int eneP2 = peca2.getEnergia();
			int eneP3 = (peca3 != null) ? peca3.getEnergia() : 999;
			int eneP4 = (peca4 != null) ? peca4.getEnergia() : 999;
			
			if (peca1.equals(this)) {
				if (eneP2 < eneP3 && eneP2 < eneP4) {
					peca2.setEnergia(eneP2 + getAtributoDaDir());	
					msg = msg + peca2.toString() + " do jogador ";
					
					if (souJ1ouJ3) msg = msg + "J1";
					else msg = msg + "J2";
				}
				else if (eneP3 < eneP4) {
					peca3.setEnergia(eneP3 + getAtributoDaDir());
					msg = msg + peca3.toString() + " do jogador ";
					
					if (souJ1ouJ3) msg = msg + "J3";
					else msg = msg + "J4";
				}
				else {
					peca4.setEnergia(eneP4 + getAtributoDaDir());
					msg = msg + peca4.toString() + " do jogador ";
					
					if (souJ1ouJ3) msg = msg + "J3";
					else msg = msg + "J4";
				}
				
			} else if (peca2.equals(this)) {
				if (eneP1 < eneP3 && eneP1 < eneP4) {
					peca1.setEnergia(eneP1 + getAtributoDaDir());	
					msg = msg + peca1.toString() + " do jogador ";
					
					if (souJ1ouJ3) msg = msg + "J1";
					else msg = msg + "J2";
				
				} else if (eneP3 < eneP4) {
					peca3.setEnergia(eneP3 + getAtributoDaDir());
					msg = msg + peca3.toString() + " do jogador ";
					
					if (souJ1ouJ3) msg = msg + "J3";
					else msg = msg + "J4";
				
				} else  {
					peca4.setEnergia(eneP4 + getAtributoDaDir());
					msg = msg + peca4.toString() + " do jogador ";
					
					if (souJ1ouJ3) msg = msg + "J3";
					else msg = msg + "J4";
				}
				
			} else if (peca3 != null) {
				if (peca3.equals(this)) {
					if (eneP2 < eneP1 && eneP2 < eneP4) {
						peca2.setEnergia(eneP2 + getAtributoDaDir());	
						msg = msg + peca2.toString() + " do jogador ";
						
						if (souJ1ouJ3) msg = msg + "J1";
						else msg = msg + "J2";
					
					} else if (eneP1 < eneP4) {
						peca1.setEnergia(eneP1 + getAtributoDaDir());
						msg = msg + peca1.toString() + " do jogador ";
						
						if (souJ1ouJ3) msg = msg + "J1";
						else msg = msg + "J2";
					
					} else {
						peca4.setEnergia(eneP4 + getAtributoDaDir());
						msg = msg + peca4.toString() + " do jogador ";
						
						if (souJ1ouJ3) msg = msg + "J3";
						else msg = msg + "J4";
					}
					
				} else {
					if (eneP2 < eneP3 && eneP2 < eneP1) {
						peca2.setEnergia(eneP2 + getAtributoDaDir());	
						msg = msg + peca2.toString() + " do jogador ";
						
						if (souJ1ouJ3) msg = msg + "J1";
						else msg = msg + "J2";
					
					} else if (eneP3 < eneP1) {
						peca3.setEnergia(eneP3 + getAtributoDaDir());
						msg = msg + peca3.toString() + " do jogador ";
						
						if (souJ1ouJ3) msg = msg + "J3";
						else msg = msg + "J4";
					
					} else {
						peca4.setEnergia(eneP1 + getAtributoDaDir());
						msg = msg + peca4.toString() + " do jogador ";
						
						if (souJ1ouJ3) msg = msg + "J3";
						else msg = msg + "J4";
					}
				}
				
			} else { // Codigo nunca deveria chegar aqui
				System.out.println("Erro de implementação no clerigo"); //! Debug
			}
			
			JOptionPane.showMessageDialog(null, msg);
	
			setExperiencia(getExperiencia() + 2);
			setEnergia(0);
			
		}
	}
	
	@Override
	public void aumentarNivel(Castelo casteloInimigo, String jogador) {
		if (getExperiencia() >= 6) {
			
			if (getNivel() == 1) {
				setNivel(2);
				setAtributoDaEsq(2);
				setQuandoAtacar(3);
				ControladorPrincipal.mensagemDeSubirDeNivel(this.toString(), jogador);
				
			} else if (getNivel() == 2) {
				setNivel(3);
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
		return new ClerigoPeca();
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Clerigo";
	}
}

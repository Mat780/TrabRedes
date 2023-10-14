package jogo;

public class ClerigoPeca extends Peca {
	
	public ClerigoPeca() {
		super(-1, 2, 0, 0, 4, "clerigo.png");
	}
	
	@Override
	public void atacar(int energiaNova, Castelo casteloAliado) {
		if (quandoAtacar - (getEnergia() + energiaNova) <= 0) {
	
			casteloAliado.atacarPontosDeVida(getAtributoDaEsq());
			
			Peca peca1 = casteloAliado.getPeca1J1();
			Peca peca2 = casteloAliado.getPeca2J1();
			Peca peca3 = casteloAliado.getPeca1J2();
			Peca peca4 = casteloAliado.getPeca2J2();
			
			int eneP1 = peca1.getEnergia();
			int eneP2 = peca2.getEnergia();
			int eneP3 = (peca3 != null) ? peca3.getEnergia() : 999;
			int eneP4 = (peca4 != null) ? peca4.getEnergia() : 999;
			
			if (peca1.equals(this)) {
				if (eneP2 < eneP3 && eneP2 < eneP4) peca2.setEnergia(eneP2 + getAtributoDaDir());	
				else if (eneP3 < eneP4) peca3.setEnergia(eneP3 + getAtributoDaDir());
				else peca4.setEnergia(eneP4 + getAtributoDaDir());
				
			} else if (peca2.equals(this)) {
				if (eneP1 < eneP3 && eneP1 < eneP4) peca1.setEnergia(eneP1 + getAtributoDaDir());	
				else if (eneP3 < eneP4) peca3.setEnergia(eneP3 + getAtributoDaDir());
				else peca4.setEnergia(eneP4 + getAtributoDaDir());
				
			} else if (peca3 != null) {
				if (peca3.equals(this)) {
					if (eneP2 < eneP1 && eneP2 < eneP4) peca2.setEnergia(eneP2 + getAtributoDaDir());	
					else if (eneP1 < eneP4) peca1.setEnergia(eneP1 + getAtributoDaDir());
					else peca4.setEnergia(eneP4 + getAtributoDaDir());
					
				} else {
					if (eneP2 < eneP3 && eneP2 < eneP1) peca2.setEnergia(eneP2 + getAtributoDaDir());	
					else if (eneP3 < eneP1) peca3.setEnergia(eneP3 + getAtributoDaDir());
					else peca4.setEnergia(eneP1 + getAtributoDaDir());
				}
				
			} else { // Codigo nunca deveria chegar aqui
				System.out.println("Erro de implementação no clerigo"); //! Debug
			}
	
			setExperiencia(getExperiencia() + 2);
			setEnergia(0);
			
		} else {
			setEnergia(getEnergia() + energiaNova);
		}
	}
	
	@Override
	public void aumentarNivel(int experienciaNova, Castelo casteloInimigo) {
		
		setExperiencia(experienciaNova + getExperiencia());
		
		if (getExperiencia() >= 6) {
			
			if (nivel == 1) {
				setNivel(2);
				setAtributoDaEsq(-2);
				setQuandoAtacar(3);
				
			} else if (nivel == 2) {
				setNivel(3);
				setAtributoDaDir(3);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
			}
			
			setExperiencia(0);
		}
		
	}
	
	@Override
	public String toString() {
		return "Clerigo";
	}
}

package jogo;

public class AssassinoPeca extends Peca {

	public AssassinoPeca() {
		super(1, 1, 0, 0, 3, "assassino");
	}
	
	@Override
	public void atacar(int energiaNova, Castelo casteloInimigo) {
		if (quandoAtacar - (getEnergia() + energiaNova) <= 0) {
			
			Peca peca1 = casteloInimigo.getPeca1J1();
			Peca peca2 = casteloInimigo.getPeca2J1();
			Peca peca3 = casteloInimigo.getPeca1J2();
			Peca peca4 = casteloInimigo.getPeca2J2();
				
			int difEneP1 = peca1.getQuandoAtacar() - peca1.getEnergia();
			int difEneP2 = peca2.getQuandoAtacar() - peca2.getEnergia();
			int difEneP3 = (peca3 != null) ? peca3.getQuandoAtacar() - peca3.getEnergia() : 999;
			int difEneP4 = (peca4 != null) ? peca4.getQuandoAtacar() - peca3.getEnergia() : 999;
			
			if (difEneP1 < difEneP2 && difEneP1 < difEneP2 && difEneP1 < difEneP3 && difEneP1 < difEneP4)
				peca1.setEnergia(peca1.getEnergia() - getAtributoDaEsq());
			
			else if (difEneP2 < difEneP3 && difEneP2 < difEneP4) peca2.setEnergia(peca2.getEnergia() - getAtributoDaEsq());
			
			else if (difEneP3 < difEneP4) peca3.setEnergia(peca3.getEnergia() - getAtributoDaEsq());
			
			else peca4.setEnergia(peca4.getEnergia() - getAtributoDaEsq());

			casteloInimigo.atacarPontosDeVida(getAtributoDaDir());
			
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
				setAtributoDaDir(2);
				
			} else if (nivel == 2) {
				setNivel(3);
				setAtributoDaEsq(2);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
			}
			
			setExperiencia(0);
		}
	}
	
	@Override
	public String toString() {
		return "Assassino";
	}
}

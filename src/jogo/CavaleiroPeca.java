package jogo;

public class CavaleiroPeca extends Peca {
	
	public CavaleiroPeca() {
		super(3, 3, 0, 0, 3, "cavaleiro.png");
	}
	
	@Override
	public void atacar(int energiaNova, Castelo casteloInimigo) {
		if (quandoAtacar - (getEnergia() + energiaNova) <= 0) {
			
			if (casteloInimigo.getMuro() > 0) {
				casteloInimigo.atacarMuro(getAtributoDaDir());

			} else {
				casteloInimigo.atacarPontosDeVida(getAtributoDaEsq());
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
				setAtributoDaEsq(5);
				setAtributoDaDir(5);
				
				
			} else if (nivel == 2) {
				setNivel(3);
				setAtributoDaEsq(7);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
			}
			
			setExperiencia(0);
		}
	}
	
	@Override
	public String toString() {
		return "Cavaleiro";
	}
}

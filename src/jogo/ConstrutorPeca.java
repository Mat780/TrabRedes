package jogo;

public class ConstrutorPeca extends Peca {

	public ConstrutorPeca() {
		super(1, 3, 0, 0, 4, "construtor.png");
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
				setAtributoDaEsq(2);	
				setAtributoDaDir(5);
				
			} else if (nivel == 2) {
				setNivel(3);
				setAtributoDaEsq(4);	
				setQuandoAtacar(3);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
			}
			
			setExperiencia(0);
		}
		
	}
	
	@Override
	public String toString() {
		return "Construtor";
	}
}

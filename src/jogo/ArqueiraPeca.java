package jogo;

public class ArqueiraPeca extends Peca {

	public ArqueiraPeca() {
		super(3, 1, 0, 0, 4, "arqueira.png");
	}
	
	@Override
	public void atacar(int energiaNova, Castelo casteloInimigo) {
		if (quandoAtacar - (getEnergia() + energiaNova) <= 0) {
			
			if (casteloInimigo.getMuro() < 3) {
				casteloInimigo.atacarPontosDeVida(getAtributoDaDir());
				
			} else {
				casteloInimigo.atacarMuro(getAtributoDaEsq());
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
				setQuandoAtacar(3);
				setAtributoDaEsq(4);
				setAtributoDaDir(2);
				
			} else if (nivel == 2) {
				setNivel(3);
				setAtributoDaEsq(6);
				setAtributoDaDir(3);
				
			} else {
				casteloInimigo.atacarPontosDeVida(2);
			}
			
			setExperiencia(0);
		}
	}
	
	@Override
	public String toString() {
		return "Arqueira";
	}
}

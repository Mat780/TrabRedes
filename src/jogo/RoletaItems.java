package jogo;

import javax.swing.ImageIcon;

public class RoletaItems {
	private ImageIcon icone;
	private ImageIcon iconeCheck;
	private enumItensDaRoleta itemDaRoleta;
	
	public RoletaItems(String icone, enumItensDaRoleta itemDaRoleta) {
		setIcone(icone);
		setItemDaRoleta(itemDaRoleta);
	}
	
	// Estabelece a "mesma imagem" para quando e escolhida (travada) ou nao.
	private void setIcone(String icone) {
		this.icone = new ImageIcon("imagens/" + icone + ".png");
		this.iconeCheck = new ImageIcon("imagens/" + icone + "Check.png");
	}
	private void setItemDaRoleta(enumItensDaRoleta item) {
		this.itemDaRoleta = item;
	}
	
	public ImageIcon getIcone() {
		return icone;
	}
	
	public ImageIcon getIconeCheck() {
		return iconeCheck;
	}
	
	public enumItensDaRoleta getItemDaRoleta() {
		return itemDaRoleta;
	}
	
}

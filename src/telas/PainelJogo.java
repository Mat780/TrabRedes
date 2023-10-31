	package telas;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import jogo.ArqueiraPeca;
import jogo.AssassinoPeca;
import jogo.Castelo;
import jogo.CavaleiroPeca;
import jogo.ClerigoPeca;
import jogo.ConstrutorPeca;
import jogo.MagoPeca;
import jogo.Peca;
import jogo.RoletaItems;
import jogo.enumItensDaRoleta;
import main.Partida;

import javax.swing.SwingConstants;

import controladoras.ControladorPrincipal;

import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;
import javax.swing.JButton;


public class PainelJogo implements Painel {

	private JPanel painel;
	private JPanel painelRoleta;
	
	private Partida partida = new Partida(null, null);
	
	// Comeca definicao dos jogadores.
	private boolean jogaJ1eJ2 = true;
	private int qualJogadorSou = -1;
	private int qtdJogadores = -1;
	private boolean[] jogadoresProntos = {false, false, false, false};
	private boolean isPartidaComecando = true;
	
	Timer timerSincronia;
	private String[] ordemPecasJ1eJ2;
	private String[] ordemPecasJ3eJ4;
	
	private ImageIcon cavaleiro = new ImageIcon("imagens/cavaleiro.png");
	private ImageIcon arqueira = new ImageIcon("imagens/arqueira.png");
	private ImageIcon assassino = new ImageIcon("imagens/assassino.png");
	private ImageIcon clerigo = new ImageIcon("imagens/clerigo.png");
	private ImageIcon construtor = new ImageIcon("imagens/construtor.png");
	private ImageIcon mago = new ImageIcon("imagens/mago.png");
	// Finaliza definicao dos jogadores
	
	// Comeca definicao da roleta.
	private int moedasRestantes = 3;
	private JLabel imgMoeda1;
	private JLabel imgMoeda2;
	private JLabel imgMoeda3;	
	private ImageIcon moedaCheia = new ImageIcon("imagens/moedaRoleta.png");
	private ImageIcon moedaVazia = new ImageIcon("imagens/moedaRoletaVazia.png");
	
	private boolean[] rodasTravamento = {false, false, false, false, false};
	private int[] rodasIndex = {0,0,0,0,0};
	
	private RoletaItems[][] roleta;
	
	private JButton botaoRoletaRodar;
	private JButton roleta1;
	private JButton roleta2;
	private JButton roleta3;
	private JButton roleta4;
	private JButton roleta5;
	
	private ArrayList<RoletaItems> itensDaRoleta;
	// Termina definicao da roleta.
	
	// Da new em todas as pecas no vetor de Peca.
	private Peca[] pecasDoJogo = {
			new CavaleiroPeca(), 	// 0
			new MagoPeca(), 		// 1
			new ArqueiraPeca(),		// 2
			new ClerigoPeca(),		// 3
			new AssassinoPeca(),	// 4
			new ConstrutorPeca()	// 5
	};
	
	// Icones.
	private JLabel imgJ1P1;
	private JLabel imgJ1P2;
	private JLabel imgJ2P1;
	private JLabel imgJ2P2;
	private JLabel imgJ3P1;
	private JLabel imgJ3P2;
	private JLabel imgJ4P1;
	private JLabel imgJ4P2;
	
	private JLabel labelQuandoAtacarJ1P1;
	private JLabel labelQuandoAtacarJ1P2;
	private JLabel labelQuandoAtacarJ2P1;
	private JLabel labelQuandoAtacarJ2P2;
	private JLabel labelQuandoAtacarJ3P1;
	private JLabel labelQuandoAtacarJ3P2;
	private JLabel labelQuandoAtacarJ4P1;
	private JLabel labelQuandoAtacarJ4P2;
	
	private JLabel labelExpJ1P1;
	private JLabel labelExpJ1P2;
	private JLabel labelExpJ2P1;
	private JLabel labelExpJ2P2;
	private JLabel labelExpJ3P1;
	private JLabel labelExpJ3P2;
	private JLabel labelExpJ4P1;
	private JLabel labelExpJ4P2;
	
	private JLabel labelAtributoEsqJ1P1;
	private JLabel labelAtributoEsqJ1P2;
	private JLabel labelAtributoEsqJ2P1;
	private JLabel labelAtributoEsqJ2P2;
	private JLabel labelAtributoEsqJ3P1;
	private JLabel labelAtributoEsqJ3P2;
	private JLabel labelAtributoEsqJ4P1;
	private JLabel labelAtributoEsqJ4P2;
	
	private JLabel labelAtributoDirJ1P1;
	private JLabel labelAtributoDirJ1P2;
	private JLabel labelAtributoDirJ2P1;
	private JLabel labelAtributoDirJ2P2;
	private JLabel labelAtributoDirJ3P1;
	private JLabel labelAtributoDirJ3P2;
	private JLabel labelAtributoDirJ4P1;
	private JLabel labelAtributoDirJ4P2;
	
	private JLabel labelMuroJ1J3;
	private JLabel labelVidaJ1J3;

	private JLabel labelMuroJ2J4;
	private JLabel labelVidaJ2J4;
	
	private Color bronze = new Color(148, 93, 30);
	private Color prata = new Color(193, 193, 193);
	private Color ouro = new Color(212, 175, 55);
	private Color amarelo = new Color(230, 200, 20);
	private Color ciano = new Color(0, 175, 225);
	
	public PainelJogo() {
		
		// Define as propriedades das fontes.
		// Tambem define a ordem das pecas, os itens da roleta
		// e ela propria.
		String fontePadraoEscura = "Arial Black";
		String fontePadrao = "Arial";
		Font arialBlack = new Font(fontePadraoEscura, Font.PLAIN, 18);
		
		Font fontLabelQuandoAtacar = new Font(fontePadraoEscura, Font.PLAIN, 12);
		Font fontLabelExp = new Font(fontePadraoEscura, Font.PLAIN, 14);
		
		ordemPecasJ1eJ2 = new String[4];
		ordemPecasJ1eJ2 = new String[4];
		
		setRotinaDeSincronia();
		
		itensDaRoleta = new ArrayList<>();
		itensDaRoleta.add(new RoletaItems("mana", enumItensDaRoleta.MANA)); // 0
		itensDaRoleta.add(new RoletaItems("manaExperiencia", enumItensDaRoleta.MANA_EXP)); // 1
		itensDaRoleta.add(new RoletaItems("mana2x", enumItensDaRoleta.MANA_2X)); // 2
		itensDaRoleta.add(new RoletaItems("mana2xExperiencia", enumItensDaRoleta.MANA_2X_EXP)); // 3
		
		itensDaRoleta.add(new RoletaItems("moeda", enumItensDaRoleta.MOEDA)); // 4
		itensDaRoleta.add(new RoletaItems("moedaExperiencia", enumItensDaRoleta.MOEDA_EXP)); // 5
		itensDaRoleta.add(new RoletaItems("moeda2x", enumItensDaRoleta.MOEDA_2X)); // 6
		itensDaRoleta.add(new RoletaItems("moeda2xExperiencia", enumItensDaRoleta.MOEDA_2X_EXP)); // 7
		
		itensDaRoleta.add(new RoletaItems("martelo", enumItensDaRoleta.MARTELO)); // 8
		itensDaRoleta.add(new RoletaItems("martelo2x", enumItensDaRoleta.MARTELO_2X)); // 9
		
		roleta = new RoletaItems[5][8];
		
		roleta[0][0] = itensDaRoleta.get(4);
		roleta[0][1] = itensDaRoleta.get(0);
		roleta[0][2] = itensDaRoleta.get(4);
		roleta[0][3] = itensDaRoleta.get(5);
		roleta[0][4] = itensDaRoleta.get(0);
		roleta[0][5] = itensDaRoleta.get(8);
		roleta[0][6] = itensDaRoleta.get(3);
		roleta[0][7] = itensDaRoleta.get(8);
		
		roleta[1][0] = itensDaRoleta.get(5);
		roleta[1][1] = itensDaRoleta.get(0);
		roleta[1][2] = itensDaRoleta.get(6);
		roleta[1][3] = itensDaRoleta.get(1);
		roleta[1][4] = itensDaRoleta.get(4);
		roleta[1][5] = itensDaRoleta.get(8);
		roleta[1][6] = itensDaRoleta.get(2);
		roleta[1][7] = itensDaRoleta.get(9);
		
		roleta[2][0] = itensDaRoleta.get(5);
		roleta[2][1] = itensDaRoleta.get(0);
		roleta[2][2] = itensDaRoleta.get(1);
		roleta[2][3] = itensDaRoleta.get(4);
		roleta[2][4] = itensDaRoleta.get(0);
		roleta[2][5] = itensDaRoleta.get(9);
		roleta[2][6] = itensDaRoleta.get(6);
		roleta[2][7] = itensDaRoleta.get(9);
		
		roleta[3][0] = itensDaRoleta.get(4);
		roleta[3][1] = itensDaRoleta.get(0);
		roleta[3][2] = itensDaRoleta.get(5);
		roleta[3][3] = itensDaRoleta.get(0);
		roleta[3][4] = itensDaRoleta.get(9);
		roleta[3][5] = itensDaRoleta.get(4);
		roleta[3][6] = itensDaRoleta.get(1);
		roleta[3][7] = itensDaRoleta.get(9);
		
		roleta[4][0] = itensDaRoleta.get(4);
		roleta[4][1] = itensDaRoleta.get(3);
		roleta[4][2] = itensDaRoleta.get(8);
		roleta[4][3] = itensDaRoleta.get(4);
		roleta[4][4] = itensDaRoleta.get(0);
		roleta[4][5] = itensDaRoleta.get(7);
		roleta[4][6] = itensDaRoleta.get(0);
		roleta[4][7] = itensDaRoleta.get(9);
		
		painel = new JPanel();
		painel.setBackground(Color.GRAY);
		
		JPanel painelDelimitador = new JPanel();
		painelDelimitador.setBackground(Color.LIGHT_GRAY);
		GroupLayout gl_painel = new GroupLayout(painel);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addContainerGap()
					.addComponent(painelDelimitador, GroupLayout.PREFERRED_SIZE, 750, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addContainerGap()
					.addComponent(painelDelimitador, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		// Painel para os objetos e atributos de cada jogador.
		JPanel painelCasteloAliado = new JPanel();
		painelCasteloAliado.setBackground(new Color(212, 212, 212));
		
		painelRoleta = new JPanel();
		painelRoleta.setBackground(Color.GRAY);
		
		JPanel painelCasteloInimigo = new JPanel();
		painelCasteloInimigo.setBackground(new Color(212, 212, 212));
		painelCasteloInimigo.setLayout(new GridLayout(1, 5, 0, 0));
		
		JPanel painelJ4P1 = new JPanel();
		painelCasteloInimigo.add(painelJ4P1);
		
		imgJ4P1 = new JLabel("");
		
		JPanel painelAtributosJ4P1 = new JPanel();
		painelAtributosJ4P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ4P1 = new JLabel("");
		labelAtributoEsqJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ4P1.setForeground(Color.RED);
		labelAtributoEsqJ4P1.setFont(arialBlack);
		painelAtributosJ4P1.add(labelAtributoEsqJ4P1);
		
		labelAtributoDirJ4P1 = new JLabel("");
		labelAtributoDirJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ4P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ4P1.setFont(arialBlack);
		painelAtributosJ4P1.add(labelAtributoDirJ4P1);
		
		JPanel painelNivelJ4P1 = new JPanel();
		painelNivelJ4P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ4P1 = new JLabel("");
		labelQuandoAtacarJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ4P1.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ4P1.setFont(fontLabelQuandoAtacar);
		painelNivelJ4P1.add(labelQuandoAtacarJ4P1);
		
		labelExpJ4P1 = new JLabel("");
		labelExpJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ4P1.setForeground(new Color(69, 180, 22));
		labelExpJ4P1.setFont(fontLabelExp);
		painelNivelJ4P1.add(labelExpJ4P1);
		GroupLayout gl_painelJ4P1 = new GroupLayout(painelJ4P1);
		gl_painelJ4P1.setHorizontalGroup(
			gl_painelJ4P1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 146, Short.MAX_VALUE)
				.addGroup(gl_painelJ4P1.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ4P1, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ4P1.createSequentialGroup()
					.addGap(5)
					.addComponent(painelAtributosJ4P1, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ4P1.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ4P1, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ4P1.setVerticalGroup(
			gl_painelJ4P1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 179, Short.MAX_VALUE)
				.addGroup(gl_painelJ4P1.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ4P1, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ4P1, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(painelAtributosJ4P1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		painelJ4P1.setLayout(gl_painelJ4P1);
		
		JPanel painelJ2P1 = new JPanel();
		painelCasteloInimigo.add(painelJ2P1);
		
		imgJ2P1 = new JLabel("");
		
		JPanel painelAtributosJ2P1 = new JPanel();
		painelAtributosJ2P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ2P1 = new JLabel("");
		labelAtributoEsqJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ2P1.setForeground(Color.RED);
		labelAtributoEsqJ2P1.setFont(arialBlack);
		painelAtributosJ2P1.add(labelAtributoEsqJ2P1);
		
		labelAtributoDirJ2P1 = new JLabel("");
		labelAtributoDirJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ2P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ2P1.setFont(arialBlack);
		painelAtributosJ2P1.add(labelAtributoDirJ2P1);
		
		JPanel painelNivelJ2P1 = new JPanel();
		painelNivelJ2P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ2P1 = new JLabel("");
		labelQuandoAtacarJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ2P1.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ2P1.setFont(fontLabelQuandoAtacar);
		painelNivelJ2P1.add(labelQuandoAtacarJ2P1);
		
		labelExpJ2P1 = new JLabel("");
		labelExpJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ2P1.setForeground(new Color(69, 180, 22));
		labelExpJ2P1.setFont(fontLabelExp);
		painelNivelJ2P1.add(labelExpJ2P1);
		GroupLayout gl_painelJ2P1 = new GroupLayout(painelJ2P1);
		gl_painelJ2P1.setHorizontalGroup(
			gl_painelJ2P1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 146, Short.MAX_VALUE)
				.addGroup(gl_painelJ2P1.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ2P1, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ2P1.createSequentialGroup()
					.addGap(5)
					.addComponent(painelAtributosJ2P1, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ2P1.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ2P1, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ2P1.setVerticalGroup(
			gl_painelJ2P1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 179, Short.MAX_VALUE)
				.addGroup(gl_painelJ2P1.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ2P1, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ2P1, GroupLayout.PREFERRED_SIZE, 101, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(painelAtributosJ2P1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		painelJ2P1.setLayout(gl_painelJ2P1);
		
		JPanel painelCoroaInimigo = new JPanel();
		painelCasteloInimigo.add(painelCoroaInimigo);
		
		labelMuroJ2J4 = new JLabel("Muro: 0/6");
		labelMuroJ2J4.setHorizontalAlignment(SwingConstants.CENTER);
		labelMuroJ2J4.setFont(new Font(fontePadrao, Font.PLAIN, 18));
		
		JLabel imgCoroaInimigo = new JLabel("");
		imgCoroaInimigo.setIcon(new ImageIcon("imagens/coroa.png"));
		imgCoroaInimigo.setBackground(Color.LIGHT_GRAY);
		
		labelVidaJ2J4 = new JLabel("Vida: 10/10");
		labelVidaJ2J4.setHorizontalAlignment(SwingConstants.CENTER);
		labelVidaJ2J4.setForeground(Color.RED);
		labelVidaJ2J4.setFont(new Font(fontePadrao, Font.PLAIN, 18));
		GroupLayout gl_painelCoroaInimigo = new GroupLayout(painelCoroaInimigo);
		gl_painelCoroaInimigo.setHorizontalGroup(
			gl_painelCoroaInimigo.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_painelCoroaInimigo.createSequentialGroup()
					.addGroup(gl_painelCoroaInimigo.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelVidaJ2J4)
						.addGroup(Alignment.LEADING, gl_painelCoroaInimigo.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_painelCoroaInimigo.createParallelGroup(Alignment.LEADING)
								.addComponent(labelMuroJ2J4, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
								.addComponent(imgCoroaInimigo, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))))
					.addGap(29))
		);
		gl_painelCoroaInimigo.setVerticalGroup(
			gl_painelCoroaInimigo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCoroaInimigo.createSequentialGroup()
					.addGap(5)
					.addComponent(labelVidaJ2J4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(imgCoroaInimigo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(labelMuroJ2J4)
					.addGap(8))
		);
		painelCoroaInimigo.setLayout(gl_painelCoroaInimigo);
		
		JPanel painelJ2P2 = new JPanel();
		painelCasteloInimigo.add(painelJ2P2);
		
		imgJ2P2 = new JLabel("");
		
		JPanel painelAtributosJ2P2 = new JPanel();
		painelAtributosJ2P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ2P2 = new JLabel("");
		labelAtributoEsqJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ2P2.setForeground(Color.RED);
		labelAtributoEsqJ2P2.setFont(arialBlack);
		painelAtributosJ2P2.add(labelAtributoEsqJ2P2);
		
		labelAtributoDirJ2P2 = new JLabel("");
		labelAtributoDirJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ2P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ2P2.setFont(arialBlack);
		painelAtributosJ2P2.add(labelAtributoDirJ2P2);
		
		JPanel painelNivelJ2P2 = new JPanel();
		painelNivelJ2P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ2P2 = new JLabel("");
		labelQuandoAtacarJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ2P2.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ2P2.setFont(fontLabelQuandoAtacar);
		painelNivelJ2P2.add(labelQuandoAtacarJ2P2);
		
		labelExpJ2P2 = new JLabel("");
		labelExpJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ2P2.setForeground(new Color(69, 180, 22));
		labelExpJ2P2.setFont(fontLabelExp);
		painelNivelJ2P2.add(labelExpJ2P2);
		GroupLayout gl_painelJ2P2 = new GroupLayout(painelJ2P2);
		gl_painelJ2P2.setHorizontalGroup(
			gl_painelJ2P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 146, Short.MAX_VALUE)
				.addGroup(gl_painelJ2P2.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ2P2, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ2P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelAtributosJ2P2, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ2P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ2P2, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ2P2.setVerticalGroup(
			gl_painelJ2P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 179, Short.MAX_VALUE)
				.addGroup(gl_painelJ2P2.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ2P2, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ2P2, GroupLayout.PREFERRED_SIZE, 101, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(painelAtributosJ2P2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		painelJ2P2.setLayout(gl_painelJ2P2);
		
		JPanel painelJ4P2 = new JPanel();
		painelCasteloInimigo.add(painelJ4P2);
		
		imgJ4P2 = new JLabel("");
		
		JPanel painelAtributosJ4P2 = new JPanel();
		painelAtributosJ4P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ4P2 = new JLabel("");
		labelAtributoEsqJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ4P2.setForeground(Color.RED);
		labelAtributoEsqJ4P2.setFont(arialBlack);
		painelAtributosJ4P2.add(labelAtributoEsqJ4P2);
		
		labelAtributoDirJ4P2 = new JLabel("");
		labelAtributoDirJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ4P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ4P2.setFont(arialBlack);
		painelAtributosJ4P2.add(labelAtributoDirJ4P2);
		
		JPanel painelNivelJ4P2 = new JPanel();
		painelNivelJ4P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ4P2 = new JLabel("");
		labelQuandoAtacarJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ4P2.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ4P2.setFont(fontLabelQuandoAtacar);
		painelNivelJ4P2.add(labelQuandoAtacarJ4P2);
		
		labelExpJ4P2 = new JLabel("");
		labelExpJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ4P2.setForeground(new Color(69, 180, 22));
		labelExpJ4P2.setFont(fontLabelExp);
		painelNivelJ4P2.add(labelExpJ4P2);
		GroupLayout gl_painelJ4P2 = new GroupLayout(painelJ4P2);
		gl_painelJ4P2.setHorizontalGroup(
			gl_painelJ4P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 146, Short.MAX_VALUE)
				.addGroup(gl_painelJ4P2.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ4P2, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ4P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelAtributosJ4P2, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ4P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ4P2, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ4P2.setVerticalGroup(
			gl_painelJ4P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 179, Short.MAX_VALUE)
				.addGroup(gl_painelJ4P2.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ4P2, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ4P2, GroupLayout.PREFERRED_SIZE, 101, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(painelAtributosJ4P2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		painelJ4P2.setLayout(gl_painelJ4P2);
		// Termino desta definicao para os jogadores 2 e 4.
		
		// Criacao de um botao para rodar a roleta.
		botaoRoletaRodar = new JButton("RODAR");
		botaoRoletaRodar.addActionListener(new ListenerRoleta());
		botaoRoletaRodar.setFont(arialBlack);
		
		JPanel painelRoletaInfo = new JPanel();
		painelRoletaInfo.setBackground(new Color(170, 170, 170));
		GroupLayout gl_painelDelimitador = new GroupLayout(painelDelimitador);
		gl_painelDelimitador.setHorizontalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painelDelimitador.createSequentialGroup()
							.addGroup(gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
								.addComponent(painelCasteloAliado, GroupLayout.PREFERRED_SIZE, 732, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_painelDelimitador.createSequentialGroup()
									.addComponent(painelRoletaInfo, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(painelRoleta, GroupLayout.PREFERRED_SIZE, 434, Short.MAX_VALUE)
									.addGap(25)
									.addComponent(botaoRoletaRodar, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)))
							.addGap(25))
						.addGroup(gl_painelDelimitador.createSequentialGroup()
							.addComponent(painelCasteloInimigo, GroupLayout.PREFERRED_SIZE, 732, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(25, Short.MAX_VALUE))))
		);
		gl_painelDelimitador.setVerticalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addContainerGap()
					.addComponent(painelCasteloInimigo, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
						.addComponent(painelRoletaInfo, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
						.addComponent(botaoRoletaRodar, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
						.addComponent(painelRoleta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10)
					.addComponent(painelCasteloAliado, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		painelRoletaInfo.setLayout(new GridLayout(2, 0, 0, 0));
		
		// Propriedades das roletas (tanto dos itens quando das imagens).
		JLabel labelRoletadasRestantes = new JLabel("ROLETADAS RESTANTES");
		labelRoletadasRestantes.setForeground(Color.BLACK);
		labelRoletadasRestantes.setFont(new Font(fontePadrao, Font.BOLD, 10));
		labelRoletadasRestantes.setHorizontalAlignment(SwingConstants.CENTER);
		painelRoletaInfo.add(labelRoletadasRestantes);
		
		JPanel painelRoletaInfoMoedas = new JPanel();
		painelRoletaInfoMoedas.setBackground(Color.LIGHT_GRAY);
		painelRoletaInfo.add(painelRoletaInfoMoedas);
		painelRoletaInfoMoedas.setLayout(new GridLayout(1, 3, 0, 0));
		
		imgMoeda1 = new JLabel("");
		imgMoeda1.setHorizontalAlignment(SwingConstants.CENTER);
		imgMoeda1.setIcon(moedaCheia);
		painelRoletaInfoMoedas.add(imgMoeda1);
		
		imgMoeda2 = new JLabel("");
		imgMoeda2.setIcon(moedaCheia);
		imgMoeda2.setHorizontalAlignment(SwingConstants.CENTER);
		painelRoletaInfoMoedas.add(imgMoeda2);
		
		imgMoeda3 = new JLabel("");
		imgMoeda3.setIcon(moedaCheia);
		imgMoeda3.setHorizontalAlignment(SwingConstants.CENTER);
		painelRoletaInfoMoedas.add(imgMoeda3);
		painelRoleta.setLayout(new GridLayout(1, 5, 5, 0));
		
		roleta1 = new JButton("");
		roleta1.addActionListener(new ListenerRodas(0));
		painelRoleta.add(roleta1);
		
		roleta2 = new JButton("");
		roleta2.addActionListener(new ListenerRodas(1));
		painelRoleta.add(roleta2);
		
		roleta3 = new JButton("");
		roleta3.addActionListener(new ListenerRodas(2));
		painelRoleta.add(roleta3);
		
		roleta4 = new JButton("");
		roleta4.addActionListener(new ListenerRodas(3));
		painelRoleta.add(roleta4);
		
		roleta5 = new JButton("");
		roleta5.addActionListener(new ListenerRodas(4));
		painelRoleta.add(roleta5);
		
		// Continuacao do painel para os objetos e atributos de cada jogador (1 e 3).
		painelCasteloAliado.setLayout(new GridLayout(1, 5, 0, 0));
		
		JPanel painelJ3P1 = new JPanel();
		painelCasteloAliado.add(painelJ3P1);
		
		JPanel painelNivelJ3P1 = new JPanel();
		painelNivelJ3P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ3P1 = new JLabel("");
		labelQuandoAtacarJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ3P1.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ3P1.setFont(fontLabelQuandoAtacar);
		painelNivelJ3P1.add(labelQuandoAtacarJ3P1);
		
		labelExpJ3P1 = new JLabel();
		labelExpJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ3P1.setForeground(new Color(69, 180, 22));
		labelExpJ3P1.setFont(fontLabelExp);
		painelNivelJ3P1.add(labelExpJ3P1);
		
		imgJ3P1 = new JLabel("");
		
		JPanel painelAtributosJ3P1 = new JPanel();
		painelAtributosJ3P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ3P1 = new JLabel("");
		labelAtributoEsqJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ3P1.setForeground(Color.RED);
		labelAtributoEsqJ3P1.setFont(arialBlack);
		painelAtributosJ3P1.add(labelAtributoEsqJ3P1);
		
		labelAtributoDirJ3P1 = new JLabel("");
		labelAtributoDirJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ3P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ3P1.setFont(arialBlack);
		painelAtributosJ3P1.add(labelAtributoDirJ3P1);
		GroupLayout gl_painelJ3P1 = new GroupLayout(painelJ3P1);
		gl_painelJ3P1.setHorizontalGroup(
			gl_painelJ3P1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 148, Short.MAX_VALUE)
				.addGroup(gl_painelJ3P1.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ3P1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ3P1.createSequentialGroup()
					.addGap(5)
					.addComponent(painelAtributosJ3P1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ3P1.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ3P1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ3P1.setVerticalGroup(
			gl_painelJ3P1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 179, Short.MAX_VALUE)
				.addGroup(gl_painelJ3P1.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ3P1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ3P1, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(painelAtributosJ3P1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		painelJ3P1.setLayout(gl_painelJ3P1);
		
		JPanel painelJ1P1 = new JPanel();
		painelCasteloAliado.add(painelJ1P1);
		
		imgJ1P1 = new JLabel("");
		imgJ1P1.setIcon(null);
		
		JPanel paineAtributosJ1P1 = new JPanel();
		
		JPanel painelNivelJ1P1 = new JPanel();
		painelNivelJ1P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ1P1 = new JLabel("");
		labelQuandoAtacarJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ1P1.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ1P1.setFont(fontLabelQuandoAtacar);
		painelNivelJ1P1.add(labelQuandoAtacarJ1P1);
		GroupLayout gl_painelJ1P1 = new GroupLayout(painelJ1P1);
		gl_painelJ1P1.setHorizontalGroup(
			gl_painelJ1P1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelJ1P1.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ1P1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ1P1.createSequentialGroup()
					.addGap(5)
					.addComponent(paineAtributosJ1P1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ1P1.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ1P1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ1P1.setVerticalGroup(
			gl_painelJ1P1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelJ1P1.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ1P1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ1P1, GroupLayout.PREFERRED_SIZE, 99, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(paineAtributosJ1P1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		
		labelExpJ1P1 = new JLabel("");
		labelExpJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ1P1.setForeground(new Color(69, 180, 22));
		labelExpJ1P1.setFont(fontLabelExp);
		painelNivelJ1P1.add(labelExpJ1P1);
		paineAtributosJ1P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ1P1 = new JLabel("");
		labelAtributoEsqJ1P1.setFont(arialBlack);
		labelAtributoEsqJ1P1.setForeground(Color.RED);
		labelAtributoEsqJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		paineAtributosJ1P1.add(labelAtributoEsqJ1P1);
		
		labelAtributoDirJ1P1 = new JLabel("");
		labelAtributoDirJ1P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ1P1.setFont(arialBlack);
		labelAtributoDirJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		paineAtributosJ1P1.add(labelAtributoDirJ1P1);
		painelJ1P1.setLayout(gl_painelJ1P1);
		
		JPanel painelCoroaAliada = new JPanel();
		painelCasteloAliado.add(painelCoroaAliada);
		
		labelMuroJ1J3 = new JLabel("Muro: 0/6");
		labelMuroJ1J3.setHorizontalAlignment(SwingConstants.CENTER);
		labelMuroJ1J3.setFont(new Font(fontePadrao, Font.PLAIN, 18));
		
		labelVidaJ1J3 = new JLabel("Vida: 10/10");
		labelVidaJ1J3.setForeground(new Color(255, 0, 0));
		labelVidaJ1J3.setHorizontalAlignment(SwingConstants.CENTER);
		labelVidaJ1J3.setFont(new Font(fontePadrao, Font.PLAIN, 18));
		
		JLabel imgCoroaAliado = new JLabel("");
		imgCoroaAliado.setBackground(Color.LIGHT_GRAY);
		imgCoroaAliado.setIcon(new ImageIcon("imagens/coroa.png"));
		GroupLayout gl_painelCoroaAliada = new GroupLayout(painelCoroaAliada);
		gl_painelCoroaAliada.setHorizontalGroup(
			gl_painelCoroaAliada.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCoroaAliada.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_painelCoroaAliada.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_painelCoroaAliada.createSequentialGroup()
							.addComponent(labelVidaJ1J3)
							.addContainerGap(27, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_painelCoroaAliada.createSequentialGroup()
							.addGroup(gl_painelCoroaAliada.createParallelGroup(Alignment.TRAILING)
								.addComponent(labelMuroJ1J3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
								.addComponent(imgCoroaAliado, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(29))))
		);
		gl_painelCoroaAliada.setVerticalGroup(
			gl_painelCoroaAliada.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCoroaAliada.createSequentialGroup()
					.addGap(5)
					.addComponent(labelMuroJ1J3)
					.addGap(18)
					.addComponent(imgCoroaAliado, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(labelVidaJ1J3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);
		painelCoroaAliada.setLayout(gl_painelCoroaAliada);
		//Termino da ultima definicao e inicio de outra referente ao jogador 1 e 2.
		
		JPanel painelJ1P2 = new JPanel();
		painelCasteloAliado.add(painelJ1P2);
		
		imgJ1P2 = new JLabel("");
		
		JPanel painelAtributosJ1P2 = new JPanel();
		painelAtributosJ1P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ1P2 = new JLabel("");
		labelAtributoEsqJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ1P2.setForeground(Color.RED);
		labelAtributoEsqJ1P2.setFont(arialBlack);
		painelAtributosJ1P2.add(labelAtributoEsqJ1P2);
		
		labelAtributoDirJ1P2 = new JLabel("");
		labelAtributoDirJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ1P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ1P2.setFont(arialBlack);
		painelAtributosJ1P2.add(labelAtributoDirJ1P2);
		
		JPanel painelNivelJ1P2 = new JPanel();
		painelNivelJ1P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ1P2 = new JLabel("");
		labelQuandoAtacarJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ1P2.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ1P2.setFont(fontLabelQuandoAtacar);
		painelNivelJ1P2.add(labelQuandoAtacarJ1P2);
		
		labelExpJ1P2 = new JLabel("");
		labelExpJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ1P2.setForeground(new Color(69, 180, 22));
		labelExpJ1P2.setFont(fontLabelExp);
		painelNivelJ1P2.add(labelExpJ1P2);
		GroupLayout gl_painelJ1P2 = new GroupLayout(painelJ1P2);
		gl_painelJ1P2.setHorizontalGroup(
			gl_painelJ1P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 246, Short.MAX_VALUE)
				.addGroup(gl_painelJ1P2.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ1P2, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ1P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelAtributosJ1P2, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ1P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ1P2, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ1P2.setVerticalGroup(
			gl_painelJ1P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 179, Short.MAX_VALUE)
				.addGroup(gl_painelJ1P2.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ1P2, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ1P2, GroupLayout.PREFERRED_SIZE, 101, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(painelAtributosJ1P2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		painelJ1P2.setLayout(gl_painelJ1P2);
		
		// Inicio das definicoes anteriores ja citadas, mas para os jogadores 2 e 3.
		JPanel painelJ3P2 = new JPanel();
		painelCasteloAliado.add(painelJ3P2);
		
		imgJ3P2 = new JLabel("");
		
		JPanel painelAtributosJ3P2 = new JPanel();
		painelAtributosJ3P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelAtributoEsqJ3P2 = new JLabel("");
		labelAtributoEsqJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ3P2.setForeground(Color.RED);
		labelAtributoEsqJ3P2.setFont(arialBlack);
		painelAtributosJ3P2.add(labelAtributoEsqJ3P2);
		
		labelAtributoDirJ3P2 = new JLabel("");
		labelAtributoDirJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ3P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ3P2.setFont(arialBlack);
		painelAtributosJ3P2.add(labelAtributoDirJ3P2);
		
		JPanel painelNivelJ3P2 = new JPanel();
		painelNivelJ3P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelQuandoAtacarJ3P2 = new JLabel("");
		labelQuandoAtacarJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuandoAtacarJ3P2.setForeground(new Color(128, 0, 255));
		labelQuandoAtacarJ3P2.setFont(fontLabelQuandoAtacar);
		painelNivelJ3P2.add(labelQuandoAtacarJ3P2);
		
		labelExpJ3P2 = new JLabel("");
		labelExpJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ3P2.setForeground(new Color(69, 180, 22));
		labelExpJ3P2.setFont(fontLabelExp);
		painelNivelJ3P2.add(labelExpJ3P2);
		GroupLayout gl_painelJ3P2 = new GroupLayout(painelJ3P2);
		gl_painelJ3P2.setHorizontalGroup(
			gl_painelJ3P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 246, Short.MAX_VALUE)
				.addGroup(gl_painelJ3P2.createSequentialGroup()
					.addGap(25)
					.addComponent(imgJ3P2, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_painelJ3P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelAtributosJ3P2, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_painelJ3P2.createSequentialGroup()
					.addGap(5)
					.addComponent(painelNivelJ3P2, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_painelJ3P2.setVerticalGroup(
			gl_painelJ3P2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 179, Short.MAX_VALUE)
				.addGroup(gl_painelJ3P2.createSequentialGroup()
					.addGap(2)
					.addComponent(painelNivelJ3P2, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(imgJ3P2, GroupLayout.PREFERRED_SIZE, 101, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(painelAtributosJ3P2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		painelJ3P2.setLayout(gl_painelJ3P2);
		painelDelimitador.setLayout(gl_painelDelimitador);
		painel.setLayout(gl_painel);
		
	}
	
	// Cria um listener para "ouvir" as rodas.
	private class ListenerRodas implements ActionListener {
		
		private int qualRoda;
		
		public ListenerRodas(int qualRoda) {
			this.qualRoda = qualRoda;
		}

		// Metodo responsavel pelo travamento dos itens escolhidos
		// durante a partida,
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int index = rodasIndex[qualRoda];
			RoletaItems item = roleta[qualRoda][index];
			
			if (rodasTravamento[qualRoda]) {
				if      (e.getSource() == roleta1) roleta1.setIcon(item.getIcone());
				else if (e.getSource() == roleta2) roleta2.setIcon(item.getIcone());
				else if (e.getSource() == roleta3) roleta3.setIcon(item.getIcone());
				else if (e.getSource() == roleta4) roleta4.setIcon(item.getIcone());
				else if (e.getSource() == roleta5) roleta5.setIcon(item.getIcone());
				
			} else {
				if		(e.getSource() == roleta1) roleta1.setIcon(item.getIconeCheck());
				else if (e.getSource() == roleta2) roleta2.setIcon(item.getIconeCheck());
				else if (e.getSource() == roleta3) roleta3.setIcon(item.getIconeCheck());
				else if (e.getSource() == roleta4) roleta4.setIcon(item.getIconeCheck());
				else if (e.getSource() == roleta5) roleta5.setIcon(item.getIconeCheck());
			}
			
			if      (qualRoda == 0) roleta1.repaint();
			else if (qualRoda == 1) roleta2.repaint();
			else if (qualRoda == 2) roleta3.repaint();
			else if (qualRoda == 3) roleta4.repaint();
			else if (qualRoda == 4) roleta5.repaint();
			
			rodasTravamento[qualRoda] = !rodasTravamento[qualRoda]; // Inverte o proprio sinal
			
		}
		
	}
	
	// Metodo responsavel por um rand (meio que aleatorio, por isso
	// a utilizacao do tempo) para o aparecimento dos itens ao jogador
	// de forma aleatoria.
	private void fazARoletaRodar() {
		Random r1, r2, r3, r4, r5;
		int i1, i2, i3, i4, i5;
		
		r1 = new Random(System.currentTimeMillis());
		r2 = new Random(System.currentTimeMillis() + 1);
		r3 = new Random(System.currentTimeMillis() + 2);
		r4 = new Random(System.currentTimeMillis() + 3);
		r5 = new Random(System.currentTimeMillis() + 4);
		
		i1 = r1.nextInt(7);
		i2 = r2.nextInt(7);
		i3 = r3.nextInt(7);
		i4 = r4.nextInt(7);
		i5 = r5.nextInt(7);
		
		int[] vetI = {i1,i2,i3,i4,i5};
		int maxIteracoes = Arrays.stream(vetI).summaryStatistics().getMax();
		
		for (int i = 0; i < maxIteracoes; i++) {
			
			if (rodasTravamento[0] == false && i1 != 0) {
				rodasIndex[0] = (rodasIndex[0] + 1) % 8;
				roleta1.setIcon(roleta[0][rodasIndex[0]].getIcone());
				i1 -= 1;
				roleta1.repaint();
			}
			
			if (rodasTravamento[1] == false && i2 != 0) {
				rodasIndex[1] = (rodasIndex[1] + 1) % 8;
				roleta2.setIcon(roleta[1][rodasIndex[1]].getIcone());
				i2 -= 1;
				roleta2.repaint();
			}
			
			if (rodasTravamento[2] == false && i3 != 0) {
				rodasIndex[2] = (rodasIndex[2] + 1) % 8;
				roleta3.setIcon(roleta[2][rodasIndex[2]].getIcone());
				i3 -= 1;
				roleta3.repaint();
			}
			
			if (rodasTravamento[3] == false && i4 != 0) {
				rodasIndex[3] = (rodasIndex[3] + 1) % 8;
				roleta4.setIcon(roleta[3][rodasIndex[3]].getIcone());
				i4 -= 1;
				roleta4.repaint();
			}
			
			if (rodasTravamento[4] == false && i5 != 0) {
				rodasIndex[4] = (rodasIndex[4] + 1) % 8;
				roleta5.setIcon(roleta[4][rodasIndex[4]].getIcone());
				i5 -= 1;
				roleta5.repaint();
			}
		}
		
		roleta1.repaint();
		roleta2.repaint();
		roleta3.repaint();
		roleta4.repaint();
		roleta5.repaint();
	}
	
	// Criacao de um listener para a roleta.
	private class ListenerRoleta implements ActionListener {
		
		// Metodo responsavel por alterar as imagens mediante a
		// vez da rodada da roleta (moedasRestantes) e enviar
		// a controladora a jogada se for a ultima vez que se rodou
		// e aperta em atacar.
		@Override
		public void actionPerformed(ActionEvent e) {
			if(moedasRestantes != 0) {
				moedasRestantes -= 1;
				
				if (moedasRestantes == 2) {
					imgMoeda1.setIcon(moedaVazia);
				} else if (moedasRestantes == 1) {
					imgMoeda2.setIcon(moedaVazia);
				} else {
					imgMoeda3.setIcon(moedaVazia);
				}
				
				fazARoletaRodar();
				
				if (moedasRestantes == 0) botaoRoletaRodar.setText("ATACAR");
				
			} else if (jogadoresProntos[qualJogadorSou - 1] == false) {
				int[] vetorRoleta = verificarRoleta();
				
				int energiaEsq = vetorRoleta[0];
				int expEsq = vetorRoleta[1];
				int energiaDir = vetorRoleta[2];
				int expDir = vetorRoleta[3];
				int martelo = vetorRoleta[4];
				
				energiaEsq -= 2;
				energiaDir -= 2;
				martelo -= 2;
				
				if (energiaEsq < 0) energiaEsq = 0;
				if (energiaDir < 0) energiaDir = 0;
				if (martelo < 0) martelo = 0;

				ControladorPrincipal.enviarJogada(energiaEsq, expEsq, energiaDir, expDir, martelo, qualJogadorSou);
				receberJogada(energiaEsq, expEsq, energiaDir, expDir, martelo, qualJogadorSou);
				rotinaSincronia();
				
			} else {
				JOptionPane.showMessageDialog(null, "Espere o seu oponente, ele ainda não finalizou a jogada");
			}
		}	
		
		// Metodo para retornar o vetor de retorno mediante ao que
		// foi estabelecido na roleta.
		private int[] verificarRoleta() {
			
			int energiaEsqAliado = 0;
			int expEsqAliado = 0;
			int energiaDirAliado = 0;
			int expDirAliado = 0;
			int marteloAliado = 0;
			
			for (int i = 0; i < 5; i++) {
				enumItensDaRoleta item = roleta[i][rodasIndex[i]].getItemDaRoleta();
				
				if (item == enumItensDaRoleta.MOEDA) {
					energiaEsqAliado += 1;
				}
				
				else if (item == enumItensDaRoleta.MOEDA_EXP) {
					energiaEsqAliado += 1;
					expEsqAliado += 1;
				}
				
				else if (item == enumItensDaRoleta.MOEDA_2X) {
					energiaEsqAliado += 2;
				}
				
				else if (item == enumItensDaRoleta.MOEDA_2X_EXP) {
					energiaEsqAliado += 2;
					expEsqAliado += 1;
				}
				
				else if (item == enumItensDaRoleta.MANA) {
					energiaDirAliado += 1;
				}
				
				else if (item == enumItensDaRoleta.MANA_EXP) {
					energiaDirAliado += 1;
					expDirAliado += 1;
				}
				
				else if (item == enumItensDaRoleta.MANA_2X) {
					energiaDirAliado += 2;
				}
				
				else if (item == enumItensDaRoleta.MANA_2X_EXP) {
					energiaDirAliado += 2;
					expDirAliado += 1;
				}
				
				else if (item == enumItensDaRoleta.MARTELO) {
					marteloAliado += 1;
				} 
				
				else if (item == enumItensDaRoleta.MARTELO_2X) {
					marteloAliado += 2;
				}
				
				else {
					System.out.println("Erro ao atacar, enum desconhecido detectado");
				}
			}
			
			int[] vetorRetorno = new int[5];
			
			vetorRetorno[0] = energiaEsqAliado;
			vetorRetorno[1] = expEsqAliado;
			vetorRetorno[2] = energiaDirAliado;
			vetorRetorno[3] = expDirAliado;
			vetorRetorno[4] = marteloAliado;
			
			return vetorRetorno;
		}
	}
	
	// Metodo responsavel por jogador receber jogada.
	public void receberJogada(int energiaEsq, int expEsq, int energiaDir, int expDir, int muro, int qualJogadorSou) {
		if (qualJogadorSou == 1 || qualJogadorSou == 3) 
			partida.getCasteloJ1J3().constroiMuro(muro);
			
		else if (qualJogadorSou == 2 || qualJogadorSou == 4) 
			partida.getCasteloJ2J4().constroiMuro(muro);
		
		atualizaStatusCoroas(); // Atualiza a "vida".
		
		Peca p1;
		Peca p2;
		
		if (qualJogadorSou == 1) {
			p1 = partida.getCasteloJ1J3().getPeca1J1();
			p2 = partida.getCasteloJ1J3().getPeca2J1();
			
			p1.receberJogada(energiaEsq, expEsq);
			p2.receberJogada(energiaDir, expDir);
			
			setInfoPartida(labelQuandoAtacarJ1P1, labelExpJ1P1, labelAtributoEsqJ1P1, labelAtributoDirJ1P1, p1, true);
			setInfoPartida(labelQuandoAtacarJ1P2, labelExpJ1P2, labelAtributoEsqJ1P2, labelAtributoDirJ1P2, p2, false);
			
		} else if (qualJogadorSou == 2) {
			p1 = partida.getCasteloJ2J4().getPeca1J1();
			p2 = partida.getCasteloJ2J4().getPeca2J1();
			
			p1.receberJogada(energiaEsq, expEsq);
			p2.receberJogada(energiaDir, expDir);
			
			setInfoPartida(labelQuandoAtacarJ2P1, labelExpJ2P1, labelAtributoEsqJ2P1, labelAtributoDirJ2P1, p1, true);
			setInfoPartida(labelQuandoAtacarJ2P2, labelExpJ2P2, labelAtributoEsqJ2P2, labelAtributoDirJ2P2, p2, false);
			
		} else if (qualJogadorSou == 3) {
			p1 = partida.getCasteloJ1J3().getPeca1J2();
			p2 = partida.getCasteloJ1J3().getPeca2J2();
			
			p1.receberJogada(energiaEsq, expEsq);
			p2.receberJogada(energiaDir, expDir);
			
			setInfoPartida(labelQuandoAtacarJ3P1, labelExpJ3P1, labelAtributoEsqJ3P1, labelAtributoDirJ3P1, p1, true);
			setInfoPartida(labelQuandoAtacarJ3P2, labelExpJ3P2, labelAtributoEsqJ3P2, labelAtributoDirJ3P2, p2, false);
			
		} else if (qualJogadorSou == 4) {
			p1 = partida.getCasteloJ2J4().getPeca1J2(); 
			p2 = partida.getCasteloJ2J4().getPeca2J2(); 

			p1.receberJogada(energiaEsq, expEsq);
			p2.receberJogada(energiaDir, expDir);
			
			setInfoPartida(labelQuandoAtacarJ4P1, labelExpJ4P1, labelAtributoEsqJ4P1, labelAtributoDirJ4P1, p1, true);
			setInfoPartida(labelQuandoAtacarJ4P2, labelExpJ4P2, labelAtributoEsqJ4P2, labelAtributoDirJ4P2, p2, false);
		}
		
		jogadoresProntos[qualJogadorSou - 1] = true;
		
	}
	
	// Metodo responsavel por atacar em ordem (J1 e J2 // J3 e J4).
	private void atacar() {
		
		Castelo casteloJ1J3 = partida.getCasteloJ1J3();
		Castelo casteloJ2J4 = partida.getCasteloJ2J4();
		
		if (jogaJ1eJ2) {
			for (int i = 0; i < 4; i++) {
				
				if (ordemPecasJ1eJ2[i].equals("J1P1")) {
					casteloJ1J3.getPeca1J1().aumentarNivel(casteloJ2J4, "J1");
					casteloJ1J3.getPeca1J1().defender(casteloJ1J3, "J1");					
					casteloJ1J3.getPeca1J1().atacar(casteloJ2J4, "J1");
					casteloJ1J3.getPeca1J1().aumentarNivel(casteloJ2J4, "J1");
					
				} else if (ordemPecasJ1eJ2[i].equals("J1P2")) {
					casteloJ1J3.getPeca2J1().aumentarNivel(casteloJ2J4, "J1");
					casteloJ1J3.getPeca2J1().defender(casteloJ1J3, "J1");			
					casteloJ1J3.getPeca2J1().atacar(casteloJ2J4, "J1");
					casteloJ1J3.getPeca2J1().aumentarNivel(casteloJ2J4, "J1");
					
				} else if (ordemPecasJ1eJ2[i].equals("J2P1")) {
					casteloJ2J4.getPeca1J1().aumentarNivel(casteloJ1J3, "J2");
					casteloJ2J4.getPeca1J1().defender(casteloJ2J4, "J2");
					casteloJ2J4.getPeca1J1().atacar(casteloJ1J3, "J2");
					casteloJ2J4.getPeca1J1().aumentarNivel(casteloJ1J3, "J2");
				
				} else if (ordemPecasJ1eJ2[i].equals("J2P2")) {
					casteloJ2J4.getPeca2J1().aumentarNivel(casteloJ1J3, "J2");
					casteloJ2J4.getPeca2J1().defender(casteloJ2J4, "J2");
					casteloJ2J4.getPeca2J1().atacar(casteloJ1J3, "J2");
					casteloJ2J4.getPeca2J1().aumentarNivel(casteloJ1J3, "J2");
					
				} else System.err.println("Erro ao atacar, ordem Pecas J1 e J2 não identificada");
				
				setInfoPartida(labelQuandoAtacarJ1P1, labelExpJ1P1, labelAtributoEsqJ1P1, labelAtributoDirJ1P1, casteloJ1J3.getPeca1J1(), true);
				setInfoPartida(labelQuandoAtacarJ1P2, labelExpJ1P2, labelAtributoEsqJ1P2, labelAtributoDirJ1P2, casteloJ1J3.getPeca2J1(), false);
				setInfoPartida(labelQuandoAtacarJ2P1, labelExpJ2P1, labelAtributoEsqJ2P1, labelAtributoDirJ2P1, casteloJ2J4.getPeca1J1(), true);
				setInfoPartida(labelQuandoAtacarJ2P2, labelExpJ2P2, labelAtributoEsqJ2P2, labelAtributoDirJ2P2, casteloJ2J4.getPeca2J1(), false);
				
			}
			
		} else {
			for (int i = 0; i < 4; i++) {
				if (ordemPecasJ3eJ4[i].equals("J3P1")) {
					casteloJ1J3.getPeca1J2().aumentarNivel(casteloJ2J4, "J3");
					casteloJ1J3.getPeca1J2().defender(casteloJ1J3, "J3");
					casteloJ1J3.getPeca1J2().atacar(casteloJ2J4, "J3");
					casteloJ1J3.getPeca1J2().aumentarNivel(casteloJ2J4, "J3");
					
				} else if (ordemPecasJ3eJ4[i].equals("J3P2")) {
					casteloJ1J3.getPeca2J2().aumentarNivel(casteloJ2J4, "J3");
					casteloJ1J3.getPeca2J2().defender(casteloJ1J3, "J3");
					casteloJ1J3.getPeca2J2().atacar(casteloJ2J4, "J3");
					casteloJ1J3.getPeca2J2().aumentarNivel(casteloJ2J4, "J3");
					
				} else if (ordemPecasJ3eJ4[i].equals("J4P1")) {
					casteloJ2J4.getPeca1J2().aumentarNivel(casteloJ1J3, "J4");
					casteloJ2J4.getPeca1J2().defender(casteloJ2J4, "J4");
					casteloJ2J4.getPeca1J2().atacar(casteloJ1J3, "J4");
					casteloJ2J4.getPeca1J2().aumentarNivel(casteloJ1J3, "J4");
				
				} else if (ordemPecasJ3eJ4[i].equals("J4P2")) {
					casteloJ2J4.getPeca2J2().aumentarNivel(casteloJ1J3, "J4");
					casteloJ2J4.getPeca2J2().defender(casteloJ2J4, "J4");
					casteloJ2J4.getPeca2J2().atacar(casteloJ1J3, "J4");
					casteloJ2J4.getPeca2J2().aumentarNivel(casteloJ1J3, "J4");
				
				} else System.err.println("Erro ao atacar, ordem Pecas J3 e J4 não identificada");
				
				setInfoPartida(labelQuandoAtacarJ3P1, labelExpJ3P1, labelAtributoEsqJ3P1, labelAtributoDirJ3P1, casteloJ1J3.getPeca1J2(), true);
				setInfoPartida(labelQuandoAtacarJ3P2, labelExpJ3P2, labelAtributoEsqJ3P2, labelAtributoDirJ3P2, casteloJ1J3.getPeca2J2(), false);
				setInfoPartida(labelQuandoAtacarJ4P1, labelExpJ4P1, labelAtributoEsqJ4P1, labelAtributoDirJ4P1, casteloJ2J4.getPeca1J2(), true);
				setInfoPartida(labelQuandoAtacarJ4P2, labelExpJ4P2, labelAtributoEsqJ4P2, labelAtributoDirJ4P2, casteloJ2J4.getPeca2J2(), false);
				atualizaStatusCoroas();
				painel.repaint();
			}
		}	
		
		atualizaStatusCoroas();
		
		if (qtdJogadores == 4) jogaJ1eJ2 = !jogaJ1eJ2;
		
		for (int i = 0; i < 5; i++) rodasTravamento[i] = false;
		
		fazARoletaRodar();
		
		alternaAtaqueEEspera();
		verificaVitoria();
		
	}
	
	// Metodo que atualiza o estado do seu castelo, da sua base, correspondendo
	// ao valor do muro, e da vida.
	private void atualizaStatusCoroas() {
		int status2x = qtdJogadores == 4 ? 2 : 1;
		int muroTotal = 6 * status2x;
		
		System.out.println("CasteloJ1J3 Muro: " + partida.getCasteloJ1J3().getMuro());
		System.out.println("CasteloJ2J4 Muro: " + partida.getCasteloJ2J4().getMuro());
		
		labelMuroJ1J3.setText("Muro: " + partida.getCasteloJ1J3().getMuro() + "/" + muroTotal);
		labelMuroJ2J4.setText("Muro: " + partida.getCasteloJ2J4().getMuro() + "/" + muroTotal);
		
		labelVidaJ1J3.setText("Vida: " + partida.getCasteloJ1J3().getVida() + "/" + 10);
		labelVidaJ2J4.setText("Vida: " + partida.getCasteloJ2J4().getVida() + "/" + 10);
	}
	
	// Metodo responsavel por verificar qual jogador ganhou a partida.
	private void verificaVitoria() {

		Castelo casteloJ1J3 = partida.getCasteloJ1J3();
		Castelo casteloJ2J4 = partida.getCasteloJ2J4();
		
		String msg = "";
		
		if (casteloJ1J3.getVida() <= 0 && casteloJ2J4.getVida() <= 0) {
			msg = "Empate, os dois reis morreram!";
		}
		
		else if (casteloJ1J3.getVida() <= 0) {
			if (qualJogadorSou == 1 || qualJogadorSou == 3)
				msg = "Você perdeu, seu rei foi morto!";
			else 
				msg = "Parabéns você ganhou, o rei inimigo está morto!";
		}
		
		else if (casteloJ2J4.getVida() <= 0) {
			if (qualJogadorSou == 2 || qualJogadorSou == 4)
				msg = "Você perdeu, seu rei foi morto!";
			else 
				msg = "Parabéns você ganhou, o rei inimigo está morto!";
		}
		
		if(msg.isBlank() == false || msg.isEmpty() == false) {
			JOptionPane.showMessageDialog(null, msg);
			ControladorPrincipal.finalizarPartida();
			resetarPartida();			
		}
		
	}
	
	// Metodo responsavel por iniciar a peca de determinado jogador durante a partida.
	public void iniciarPecaDaPartida(int qualJogadorSou, int indexPeca1, int indexPeca2, int qtdJogadores) {

		if (this.qualJogadorSou == -1) {
			String msg = "Partida em andamento: Sou J" + qualJogadorSou;
			
			if (qualJogadorSou == 1 || qualJogadorSou == 3)
				msg = msg + " do time J1J3";
			else 
				msg = msg + " do time J2J4";
			
			ControladorPrincipal.trocaTitulo(msg);
			this.qualJogadorSou = qualJogadorSou;
			this.qtdJogadores = qtdJogadores;	
			
			if (qtdJogadores == 4) {
				partida.getCasteloJ1J3().modoQuatroJogadores();
				partida.getCasteloJ2J4().modoQuatroJogadores();
			}
		}
		
		Peca peca1 = pecasDoJogo[indexPeca1].clonarPeca();
		Peca peca2 = pecasDoJogo[indexPeca2].clonarPeca();
		
		if (qualJogadorSou == 1) {
			partida.setPecaJ1(peca1, peca2);
			setIconePeca(imgJ1P1, indexPeca1);
			setIconePeca(imgJ1P2, indexPeca2);
			setInfoPartida(labelQuandoAtacarJ1P1, labelExpJ1P1, labelAtributoEsqJ1P1, labelAtributoDirJ1P1, peca1, true);
			setInfoPartida(labelQuandoAtacarJ1P2, labelExpJ1P2, labelAtributoEsqJ1P2, labelAtributoDirJ1P2, peca2, false);
			
		} else if (qualJogadorSou == 2) {
			partida.setPecaJ2(peca1, peca2);
			setIconePeca(imgJ2P1, indexPeca1);
			setIconePeca(imgJ2P2, indexPeca2);
			setInfoPartida(labelQuandoAtacarJ2P1, labelExpJ2P1, labelAtributoEsqJ2P1, labelAtributoDirJ2P1, peca1, true);
			setInfoPartida(labelQuandoAtacarJ2P2, labelExpJ2P2, labelAtributoEsqJ2P2, labelAtributoDirJ2P2, peca2, false);
			
		} else if (qualJogadorSou == 3) {
			partida.setPecaJ3(peca1, peca2);
			setIconePeca(imgJ3P1, indexPeca1);
			setIconePeca(imgJ3P2, indexPeca2);
			setInfoPartida(labelQuandoAtacarJ3P1, labelExpJ3P1, labelAtributoEsqJ3P1, labelAtributoDirJ3P1, peca1, true);
			setInfoPartida(labelQuandoAtacarJ3P2, labelExpJ3P2, labelAtributoEsqJ3P2, labelAtributoDirJ3P2, peca2, false);
			
		} else if (qualJogadorSou == 4) {
			partida.setPecaJ4(peca1, peca2);
			setIconePeca(imgJ4P1, indexPeca1);
			setIconePeca(imgJ4P2, indexPeca2);
			setInfoPartida(labelQuandoAtacarJ4P1, labelExpJ4P1, labelAtributoEsqJ4P1, labelAtributoDirJ4P1, peca1, true);
			setInfoPartida(labelQuandoAtacarJ4P2, labelExpJ4P2, labelAtributoEsqJ4P2, labelAtributoDirJ4P2, peca2, false);
			
		}
		
		jogadoresProntos[qualJogadorSou - 1] = true;

	}
	
	// Seta as informacoes do jogador na partida.
	private void setInfoPartida(JLabel labelNivel, JLabel labelExp, JLabel labelEsq, JLabel labelDir, Peca peca, boolean eMoeda) {
		
		if      (peca.getNivel() == 1) labelExp.setForeground(bronze);
		else if (peca.getNivel() == 2) labelExp.setForeground(prata);
		else if (peca.getNivel() == 3) labelExp.setForeground(ouro);
		
		if (eMoeda) {
			labelNivel.setText("Moeda: " + peca.getEnergia() + "/" + peca.getQuandoAtacar());
			labelNivel.setForeground(amarelo);
	
		} else {
			labelNivel.setText("Mana: "  + peca.getEnergia() + "/" + peca.getQuandoAtacar());
			labelNivel.setForeground(ciano);
		}
		
		labelExp.setText("Exp: " + peca.getExperiencia() + "/6");
		labelEsq.setText("[  " + peca.getAtributoDaEsq() + "  ]");
		labelDir.setText("[  " + peca.getAtributoDaDir() + "  ]");
	}
	
	private void setIconePeca(JLabel label, int index) {
		if 		(index == 0) label.setIcon(cavaleiro);
		else if (index == 1) label.setIcon(mago);
		else if (index == 2) label.setIcon(arqueira);
		else if (index == 3) label.setIcon(clerigo);
		else if (index == 4) label.setIcon(assassino);
		else if (index == 5) label.setIcon(construtor);
		else label.setIcon(null);
	}
	
	public void setPartida(Partida p) {
		this.partida = p;
	}
	
	// Metodo que reseta a partida.
	public void resetarPartida() {
		moedasRestantes = 3;
		imgMoeda1.setIcon(moedaCheia);
		imgMoeda2.setIcon(moedaCheia);
		imgMoeda3.setIcon(moedaCheia);
		
		partida = new Partida(null, null);
		
		jogaJ1eJ2 = true;
		qualJogadorSou = -1;
		qtdJogadores = -1;
		isPartidaComecando = true;

		for (int i = 0; i < 4; i++) jogadoresProntos[i] = false;
		
		fazARoletaRodar();
		
	}
	
	// Metodo responsavel por designar em ordem quem, qual jogador, ataca primeiro.
	private void alternaAtaqueEEspera() {
		
		boolean liberaJ1eJ2 = jogaJ1eJ2 && (qualJogadorSou == 1 || qualJogadorSou == 2);
		boolean liberaJ3eJ4 = jogaJ1eJ2 == false && (qualJogadorSou == 3 || qualJogadorSou == 4);
		
		boolean poeEmEsperaJ1eJ2 = jogaJ1eJ2 == false && (qualJogadorSou == 1 || qualJogadorSou == 2);
		boolean poeEmEsperaJ3eJ4 = jogaJ1eJ2 && (qualJogadorSou == 3 || qualJogadorSou == 4);
		
		if (liberaJ1eJ2 || liberaJ3eJ4) { // Libera pra ataque (J1 e J2) ou (J3 e J4).
			moedasRestantes = 3;
			imgMoeda1.setIcon(moedaCheia);
			imgMoeda2.setIcon(moedaCheia);
			imgMoeda3.setIcon(moedaCheia);
			
			roleta1.setEnabled(true);
			roleta2.setEnabled(true);
			roleta3.setEnabled(true);
			roleta4.setEnabled(true);
			roleta5.setEnabled(true);
			
			botaoRoletaRodar.setEnabled(true);
			botaoRoletaRodar.setText("RODAR");
			
		} else if (poeEmEsperaJ1eJ2 || poeEmEsperaJ3eJ4) { // Faz (J1 e J2) ou (J3 e J4) esperar.
			moedasRestantes = 0;
			imgMoeda1.setIcon(moedaVazia);
			imgMoeda2.setIcon(moedaVazia);
			imgMoeda3.setIcon(moedaVazia);
			
			roleta1.setEnabled(false);
			roleta2.setEnabled(false);
			roleta3.setEnabled(false);
			roleta4.setEnabled(false);
			roleta5.setEnabled(false);
			
			botaoRoletaRodar.setText("ESPERE");
			botaoRoletaRodar.setEnabled(false);
			
		} else {
			System.err.println("Erro: Ao alternar ataque e espera");
		}
		
	}
	
	// Metodo que define a ordem de ataque de acordo com as pecas.
	private void definirOrdemDeAtaque() {
		Peca[] pecasJ1eJ2 = new Peca[4];
		Peca[] pecasJ3eJ4 = new Peca[4];
		
		int[] quaisPecas = new int[4];
		int atacaPrimeiro;
		int index;
		int qualJogador;
		
		pecasJ1eJ2[0] = partida.getCasteloJ1J3().getPeca1J1();
		pecasJ1eJ2[1] = partida.getCasteloJ1J3().getPeca2J1();
		
		pecasJ1eJ2[2] = partida.getCasteloJ2J4().getPeca1J1();
		pecasJ1eJ2[3] = partida.getCasteloJ2J4().getPeca2J1();
		
		// Pega qual a ordem de ataque das pecas.
		for (int i = 0; i < 4; i++) {
			quaisPecas[i] = qualAOrdemDaPeca(pecasJ1eJ2[i]);
		}
		
		// Ordena qual peca de qual jogador ira atacar primeiro.
		for (int i = 0; i < 4; i++) {
			atacaPrimeiro = 999;
			index = -1;
			
			// Ve qual a proxima peca que ataca primeiro.
			for (int j = 0; j < 4; j++) {
				if(quaisPecas[j] < atacaPrimeiro) {
					atacaPrimeiro = quaisPecas[j];
					index = j;
				}
			}
			
			quaisPecas[index] = 999;
			
			// Pega a informacao do Index e a transforma para ser usada na String.
			if (index == 0 || index == 1) {
				qualJogador = 1;
				index += 1;
			} else {
				qualJogador = 2;
				index -= 1;
			}
			
			// Define a ordem e escreve na String qual jogador e qual peca.
			ordemPecasJ1eJ2[i] = "J" + qualJogador + "P" + index;
		}
		
		if (qtdJogadores == 4) {
			
			pecasJ3eJ4[0] = partida.getCasteloJ1J3().getPeca1J2();
			pecasJ3eJ4[1] = partida.getCasteloJ1J3().getPeca2J2();
			
			pecasJ3eJ4[2] = partida.getCasteloJ2J4().getPeca1J2();
			pecasJ3eJ4[3] = partida.getCasteloJ2J4().getPeca2J2();
			
			// Pega qual a ordem de ataque das pecas.
			for (int i = 0; i < 4; i++) {
				quaisPecas[i] = qualAOrdemDaPeca(pecasJ3eJ4[i]);
			}
			
			for (int i = 0; i < 4; i++) {
				atacaPrimeiro = 999;
				index = -1;
				
				// Ve qual a proxima peca que ataca primeiro.
				for (int j = 0; j < 4; j++) {
					if(quaisPecas[j] < atacaPrimeiro) {
						atacaPrimeiro = quaisPecas[j];
						index = j;
					}
				}
				
				quaisPecas[index] = 999;
				
				// Pega a informacao do Index e a transforma para ser usada na String.
				if (index == 0 || index == 1) {
					qualJogador = 3;
					index += 1;
				} else {
					qualJogador = 4;
					index -= 1;
				}
				
				// Define a ordem e escreve na String qual jogador e qual peca.
				ordemPecasJ3eJ4[i] = "J" + qualJogador + "P" + index;
			}
		}
	}
	
	// Metodo chamado anteriormente para estabelecer a ordem entre as pecas em
	// si e nao apenas as escolhidas por um jogador.
	private int qualAOrdemDaPeca(Peca peca) {
		int resposta = -1;
		
		if      (peca instanceof ClerigoPeca)  	 resposta = 1;
		else if (peca instanceof AssassinoPeca)  resposta = 2;
		else if (peca instanceof ConstrutorPeca) resposta = 3;
		else if (peca instanceof MagoPeca)       resposta = 4;
		else if (peca instanceof CavaleiroPeca)  resposta = 5;
		else if (peca instanceof ArqueiraPeca)   resposta = 6;
		else System.err.println("Erro ao definir a ordem da peca, Peca não reconhecida");
		
		return resposta;
	}
	
	// Metodo que ativa timer de sincronia.
	private void setRotinaDeSincronia() {
		timerSincronia = new Timer();
		timerSincronia.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (qtdJogadores != -1) rotinaSincronia();
				
			}
		}, 500, 500);
	}
	
	// Metodo sincronizado (permite exclusao mutua) para a rotina de sincronia entre os
	// jogadores, sendo a ordem de ataque, a alternatica e os ataques em si.
	private synchronized void rotinaSincronia() {
		boolean doisJogadoresProntos   = qtdJogadores == 2 && jogadoresProntos[0] && jogadoresProntos[1];
		boolean quatroJogadoresProntos = qtdJogadores == 4 && jogadoresProntos[0] && jogadoresProntos[1] && jogadoresProntos[2] && jogadoresProntos[3];
		
		if (doisJogadoresProntos || quatroJogadoresProntos) {
			jogadoresProntos[0] = false;
			jogadoresProntos[1] = false;
			jogadoresProntos[2] = false;
			jogadoresProntos[3] = false;
			
			if (isPartidaComecando) {
				definirOrdemDeAtaque();
				alternaAtaqueEEspera();
				jogaJ1eJ2 = true;
				isPartidaComecando = false;
				
			} else {
				atacar();
			}
		} 
	}
	
	@Override
	public JPanel getPainel() {
		return painel;
	}
	
	// Nao limpa os campos.
	@Override
	public void limparCampos() {
		// Vazio
	}
}

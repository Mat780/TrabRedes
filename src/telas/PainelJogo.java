package telas;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import jogo.Castelo;
import jogo.RoletaItems;
import jogo.enumItensDaRoleta;
import main.Partida;
import main.Usuario;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;


public class PainelJogo implements Painel {

	private JPanel painel;
	private JanelaPrincipal janelaMae;
	private Painel painelDestino;
	private JPanel painelRoleta;
	
	private Partida partida;
	private Usuario euUsuario;
	private boolean jogaJ1eJ2 = true;
	
	private int moedasRestantes = 3;
	private JLabel imgMoeda1;
	private JLabel imgMoeda2;
	private JLabel imgMoeda3;	
	private ImageIcon moedaCheia = new ImageIcon("imagens/moedaRoleta.png");
	private ImageIcon moedaVazia = new ImageIcon("imagens/moedaRoletaVazia.png");
	
	private boolean[] rodasTravamento = {false, false, false, false, false};
	private int[] rodasIndex = {0,1,1,1,1};
	
	private RoletaItems[][] roleta;
	
	private JButton botaoRoletaRodar;
	private JButton roleta1;
	private JButton roleta2;
	private JButton roleta3;
	private JButton roleta4;
	private JButton roleta5;
	
	private ArrayList<RoletaItems> itensDaRoleta;
	
	public PainelJogo(JanelaPrincipal janelaMae, Painel p) {
		this.janelaMae = janelaMae;
		painelDestino = p;
		
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
		
		JPanel painelCasteloAliado = new JPanel();
		painelCasteloAliado.setBackground(new Color(212, 212, 212));
		
		painelRoleta = new JPanel();
		painelRoleta.setBackground(Color.GRAY);
		
		JPanel painelCasteloInimigo = new JPanel();
		painelCasteloInimigo.setBackground(new Color(212, 212, 212));
		painelCasteloInimigo.setLayout(new GridLayout(1, 5, 0, 0));
		
		JPanel painelJ4P1 = new JPanel();
		painelCasteloInimigo.add(painelJ4P1);
		
		JLabel imgJ4P1 = new JLabel("");
		
		JPanel painelAtributosJ4P1 = new JPanel();
		painelAtributosJ4P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ4P1 = new JLabel("[  3  ]");
		labelAtributoEsqJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ4P1.setForeground(Color.RED);
		labelAtributoEsqJ4P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ4P1.add(labelAtributoEsqJ4P1);
		
		JLabel labelAtributoDirJ4P1 = new JLabel("[  5  ]");
		labelAtributoDirJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ4P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ4P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ4P1.add(labelAtributoDirJ4P1);
		
		JPanel painelNivelJ4P1 = new JPanel();
		painelNivelJ4P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ4P1 = new JLabel("Nivel: 1");
		labelNivelJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ4P1.setForeground(new Color(128, 0, 255));
		labelNivelJ4P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ4P1.add(labelNivelJ4P1);
		
		JLabel labelExpJ4P1 = new JLabel("Exp: 0/5");
		labelExpJ4P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ4P1.setForeground(new Color(69, 180, 22));
		labelExpJ4P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
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
		
		JLabel imgJ2P1 = new JLabel("");
		
		JPanel painelAtributosJ2P1 = new JPanel();
		painelAtributosJ2P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ2P1 = new JLabel("[  3  ]\r\n");
		labelAtributoEsqJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ2P1.setForeground(Color.RED);
		labelAtributoEsqJ2P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ2P1.add(labelAtributoEsqJ2P1);
		
		JLabel labelAtributoDirJ2P1 = new JLabel("[  5  ]");
		labelAtributoDirJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ2P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ2P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ2P1.add(labelAtributoDirJ2P1);
		
		JPanel painelNivelJ2P1 = new JPanel();
		painelNivelJ2P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ2P1 = new JLabel("Nivel: 1");
		labelNivelJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ2P1.setForeground(new Color(128, 0, 255));
		labelNivelJ2P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ2P1.add(labelNivelJ2P1);
		
		JLabel labelExpJ2P1 = new JLabel("Exp: 0/5");
		labelExpJ2P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ2P1.setForeground(new Color(69, 180, 22));
		labelExpJ2P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
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
		
		JLabel labelMuroInimigo = new JLabel("Muro: 0/6");
		labelMuroInimigo.setHorizontalAlignment(SwingConstants.CENTER);
		labelMuroInimigo.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JLabel imgCoroaInimigo = new JLabel("");
		imgCoroaInimigo.setIcon(new ImageIcon("D:\\Programação\\Faculdade\\JAVA\\JogoMultiplayerConexaoP2P\\imagens\\coroa.png"));
		imgCoroaInimigo.setBackground(Color.LIGHT_GRAY);
		
		JLabel labelVidaInimigo = new JLabel("Vida: 10/10");
		labelVidaInimigo.setHorizontalAlignment(SwingConstants.CENTER);
		labelVidaInimigo.setForeground(Color.RED);
		labelVidaInimigo.setFont(new Font("Arial", Font.PLAIN, 18));
		GroupLayout gl_painelCoroaInimigo = new GroupLayout(painelCoroaInimigo);
		gl_painelCoroaInimigo.setHorizontalGroup(
			gl_painelCoroaInimigo.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_painelCoroaInimigo.createSequentialGroup()
					.addGroup(gl_painelCoroaInimigo.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelVidaInimigo)
						.addGroup(Alignment.LEADING, gl_painelCoroaInimigo.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_painelCoroaInimigo.createParallelGroup(Alignment.LEADING)
								.addComponent(labelMuroInimigo, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
								.addComponent(imgCoroaInimigo, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))))
					.addGap(29))
		);
		gl_painelCoroaInimigo.setVerticalGroup(
			gl_painelCoroaInimigo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCoroaInimigo.createSequentialGroup()
					.addGap(5)
					.addComponent(labelVidaInimigo, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(imgCoroaInimigo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(labelMuroInimigo)
					.addGap(8))
		);
		painelCoroaInimigo.setLayout(gl_painelCoroaInimigo);
		
		JPanel painelJ2P2 = new JPanel();
		painelCasteloInimigo.add(painelJ2P2);
		
		JLabel imgJ2P2 = new JLabel("");
		
		JPanel painelAtributosJ2P2 = new JPanel();
		painelAtributosJ2P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ2P2 = new JLabel("[  3  ]\r\n");
		labelAtributoEsqJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ2P2.setForeground(Color.RED);
		labelAtributoEsqJ2P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ2P2.add(labelAtributoEsqJ2P2);
		
		JLabel labelAtributoDirJ2P2 = new JLabel("[  5  ]");
		labelAtributoDirJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ2P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ2P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ2P2.add(labelAtributoDirJ2P2);
		
		JPanel painelNivelJ2P2 = new JPanel();
		painelNivelJ2P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ2P2 = new JLabel("Nivel: 1");
		labelNivelJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ2P2.setForeground(new Color(128, 0, 255));
		labelNivelJ2P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ2P2.add(labelNivelJ2P2);
		
		JLabel labelExpJ2P2 = new JLabel("Exp: 0/5");
		labelExpJ2P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ2P2.setForeground(new Color(69, 180, 22));
		labelExpJ2P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
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
		
		JLabel imgJ4P2 = new JLabel("");
		
		JPanel painelAtributosJ4P2 = new JPanel();
		painelAtributosJ4P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ4P2 = new JLabel("[  3  ]\r\n");
		labelAtributoEsqJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ4P2.setForeground(Color.RED);
		labelAtributoEsqJ4P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ4P2.add(labelAtributoEsqJ4P2);
		
		JLabel labelAtributoDirJ4P2 = new JLabel("[  5  ]");
		labelAtributoDirJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ4P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ4P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ4P2.add(labelAtributoDirJ4P2);
		
		JPanel painelNivelJ4P2 = new JPanel();
		painelNivelJ4P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ4P2 = new JLabel("Nivel: 1");
		labelNivelJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ4P2.setForeground(new Color(128, 0, 255));
		labelNivelJ4P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ4P2.add(labelNivelJ4P2);
		
		JLabel labelExpJ4P2 = new JLabel("Exp: 0/5");
		labelExpJ4P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ4P2.setForeground(new Color(69, 180, 22));
		labelExpJ4P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
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
		
		botaoRoletaRodar = new JButton("RODAR");
		botaoRoletaRodar.addActionListener(new listenerRoleta());
		botaoRoletaRodar.setFont(new Font("Arial Black", Font.PLAIN, 18));
		
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
		
		JLabel labelRoletadasRestantes = new JLabel("ROLETADAS RESTANTES");
		labelRoletadasRestantes.setForeground(Color.BLACK);
		labelRoletadasRestantes.setFont(new Font("Arial", Font.BOLD, 11));
		labelRoletadasRestantes.setHorizontalAlignment(SwingConstants.CENTER);
		painelRoletaInfo.add(labelRoletadasRestantes);
		
		JPanel painelRoletaInfoMoedas = new JPanel();
		painelRoletaInfoMoedas.setBackground(Color.LIGHT_GRAY);
		painelRoletaInfo.add(painelRoletaInfoMoedas);
		painelRoletaInfoMoedas.setLayout(new GridLayout(1, 3, 0, 0));
		
		imgMoeda1 = new JLabel("");
		imgMoeda1.setHorizontalAlignment(SwingConstants.CENTER);
		imgMoeda1.setIcon(new ImageIcon("D:\\Programação\\Faculdade\\JAVA\\JogoMultiplayerConexaoP2P\\imagens\\moedaRoletaVazia.png"));
		painelRoletaInfoMoedas.add(imgMoeda1);
		
		imgMoeda2 = new JLabel("");
		imgMoeda2.setIcon(new ImageIcon("D:\\Programação\\Faculdade\\JAVA\\JogoMultiplayerConexaoP2P\\imagens\\moedaRoleta.png"));
		imgMoeda2.setHorizontalAlignment(SwingConstants.CENTER);
		painelRoletaInfoMoedas.add(imgMoeda2);
		
		imgMoeda3 = new JLabel("");
		imgMoeda3.setIcon(new ImageIcon("D:\\Programação\\Faculdade\\JAVA\\JogoMultiplayerConexaoP2P\\imagens\\moedaRoleta.png"));
		imgMoeda3.setHorizontalAlignment(SwingConstants.CENTER);
		painelRoletaInfoMoedas.add(imgMoeda3);
		painelRoleta.setLayout(new GridLayout(1, 5, 5, 0));
		
		roleta1 = new JButton("");
		roleta1.addActionListener(new listenerRodas(0));
		painelRoleta.add(roleta1);
		roleta1.setIcon(new ImageIcon("D:\\Programação\\Faculdade\\JAVA\\JogoMultiplayerConexaoP2P\\imagens\\manaExperiencia.png"));
		
		roleta2 = new JButton("");
		roleta2.addActionListener(new listenerRodas(1));
		painelRoleta.add(roleta2);
		
		roleta3 = new JButton("");
		roleta3.addActionListener(new listenerRodas(2));
		painelRoleta.add(roleta3);
		
		roleta4 = new JButton("");
		roleta4.addActionListener(new listenerRodas(3));
		painelRoleta.add(roleta4);
		
		roleta5 = new JButton("");
		roleta5.addActionListener(new listenerRodas(4));
		painelRoleta.add(roleta5);
		painelCasteloAliado.setLayout(new GridLayout(1, 5, 0, 0));
		
		JPanel painelJ3P1 = new JPanel();
		painelCasteloAliado.add(painelJ3P1);
		
		JPanel painelNivelJ3P1 = new JPanel();
		painelNivelJ3P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ3P1 = new JLabel("Nivel: 1");
		labelNivelJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ3P1.setForeground(new Color(128, 0, 255));
		labelNivelJ3P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ3P1.add(labelNivelJ3P1);
		
		JLabel labelExpJ3P1 = new JLabel("Exp: 0/5");
		labelExpJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ3P1.setForeground(new Color(69, 180, 22));
		labelExpJ3P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ3P1.add(labelExpJ3P1);
		
		JLabel imgJ3P1 = new JLabel("");
		
		JPanel painelAtributosJ3P1 = new JPanel();
		painelAtributosJ3P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ3P1 = new JLabel("[  3  ]\r\n");
		labelAtributoEsqJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ3P1.setForeground(Color.RED);
		labelAtributoEsqJ3P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ3P1.add(labelAtributoEsqJ3P1);
		
		JLabel labelAtributoDirJ3P1 = new JLabel("[  5  ]");
		labelAtributoDirJ3P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ3P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ3P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
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
		
		JLabel imgJ1P1 = new JLabel("");
		imgJ1P1.setIcon(new ImageIcon("D:\\Programação\\Faculdade\\JAVA\\JogoMultiplayerConexaoP2P\\imagens\\arqueira.png"));
		
		JPanel paineAtributosJ1P1 = new JPanel();
		
		JPanel painelNivelJ1P1 = new JPanel();
		painelNivelJ1P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ1P1 = new JLabel("Nivel: 1");
		labelNivelJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ1P1.setForeground(new Color(128, 0, 255));
		labelNivelJ1P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ1P1.add(labelNivelJ1P1);
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
		
		JLabel labelExpJ1P1 = new JLabel("Exp: 0/5");
		labelExpJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ1P1.setForeground(new Color(69, 180, 22));
		labelExpJ1P1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ1P1.add(labelExpJ1P1);
		paineAtributosJ1P1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ1P1 = new JLabel("[  3  ]\r\n");
		labelAtributoEsqJ1P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		labelAtributoEsqJ1P1.setForeground(Color.RED);
		labelAtributoEsqJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		paineAtributosJ1P1.add(labelAtributoEsqJ1P1);
		
		JLabel labelAtributoDirJ1P1 = new JLabel("[  5  ]");
		labelAtributoDirJ1P1.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ1P1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		labelAtributoDirJ1P1.setHorizontalAlignment(SwingConstants.CENTER);
		paineAtributosJ1P1.add(labelAtributoDirJ1P1);
		painelJ1P1.setLayout(gl_painelJ1P1);
		
		JPanel painelCoroaAliada = new JPanel();
		painelCasteloAliado.add(painelCoroaAliada);
		
		JLabel labelMuroAliado = new JLabel("Muro: 0/6");
		labelMuroAliado.setHorizontalAlignment(SwingConstants.CENTER);
		labelMuroAliado.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JLabel labelVidaAliada = new JLabel("Vida: 10/10");
		labelVidaAliada.setForeground(new Color(255, 0, 0));
		labelVidaAliada.setHorizontalAlignment(SwingConstants.CENTER);
		labelVidaAliada.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JLabel imgCoroaAliado = new JLabel("");
		imgCoroaAliado.setBackground(Color.LIGHT_GRAY);
		imgCoroaAliado.setIcon(new ImageIcon("D:\\Programação\\Faculdade\\JAVA\\JogoMultiplayerConexaoP2P\\imagens\\coroa.png"));
		GroupLayout gl_painelCoroaAliada = new GroupLayout(painelCoroaAliada);
		gl_painelCoroaAliada.setHorizontalGroup(
			gl_painelCoroaAliada.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCoroaAliada.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_painelCoroaAliada.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_painelCoroaAliada.createSequentialGroup()
							.addComponent(labelVidaAliada)
							.addContainerGap(27, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_painelCoroaAliada.createSequentialGroup()
							.addGroup(gl_painelCoroaAliada.createParallelGroup(Alignment.TRAILING)
								.addComponent(labelMuroAliado, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
								.addComponent(imgCoroaAliado, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(29))))
		);
		gl_painelCoroaAliada.setVerticalGroup(
			gl_painelCoroaAliada.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelCoroaAliada.createSequentialGroup()
					.addGap(5)
					.addComponent(labelMuroAliado)
					.addGap(18)
					.addComponent(imgCoroaAliado, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(labelVidaAliada, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);
		painelCoroaAliada.setLayout(gl_painelCoroaAliada);
		
		JPanel painelJ1P2 = new JPanel();
		painelCasteloAliado.add(painelJ1P2);
		
		JLabel imgJ1P2 = new JLabel("");
		
		JPanel painelAtributosJ1P2 = new JPanel();
		painelAtributosJ1P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ1P2 = new JLabel("[  3  ]\r\n");
		labelAtributoEsqJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ1P2.setForeground(Color.RED);
		labelAtributoEsqJ1P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ1P2.add(labelAtributoEsqJ1P2);
		
		JLabel labelAtributoDirJ1P2 = new JLabel("[  5  ]");
		labelAtributoDirJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ1P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ1P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ1P2.add(labelAtributoDirJ1P2);
		
		JPanel painelNivelJ1P2 = new JPanel();
		painelNivelJ1P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ1P2 = new JLabel("Nivel: 1");
		labelNivelJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ1P2.setForeground(new Color(128, 0, 255));
		labelNivelJ1P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ1P2.add(labelNivelJ1P2);
		
		JLabel labelExpJ1P2 = new JLabel("Exp: 0/5");
		labelExpJ1P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ1P2.setForeground(new Color(69, 180, 22));
		labelExpJ1P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
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
		
		JPanel painelJ3P2 = new JPanel();
		painelCasteloAliado.add(painelJ3P2);
		
		JLabel imgJ3P2 = new JLabel("");
		
		JPanel painelAtributosJ3P2 = new JPanel();
		painelAtributosJ3P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelAtributoEsqJ3P2 = new JLabel("[  3  ]\r\n");
		labelAtributoEsqJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoEsqJ3P2.setForeground(Color.RED);
		labelAtributoEsqJ3P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ3P2.add(labelAtributoEsqJ3P2);
		
		JLabel labelAtributoDirJ3P2 = new JLabel("[  5  ]");
		labelAtributoDirJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtributoDirJ3P2.setForeground(new Color(53, 97, 244));
		labelAtributoDirJ3P2.setFont(new Font("Arial Black", Font.PLAIN, 18));
		painelAtributosJ3P2.add(labelAtributoDirJ3P2);
		
		JPanel painelNivelJ3P2 = new JPanel();
		painelNivelJ3P2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel labelNivelJ3P2 = new JLabel("Nivel: 1");
		labelNivelJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelNivelJ3P2.setForeground(new Color(128, 0, 255));
		labelNivelJ3P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		painelNivelJ3P2.add(labelNivelJ3P2);
		
		JLabel labelExpJ3P2 = new JLabel("Exp: 0/5");
		labelExpJ3P2.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpJ3P2.setForeground(new Color(69, 180, 22));
		labelExpJ3P2.setFont(new Font("Arial Black", Font.PLAIN, 15));
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
	
	private class listenerRodas implements ActionListener {
		
		private int qualRoda;
		
		public listenerRodas(int qualRoda) {
			this.qualRoda = qualRoda;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int index = rodasIndex[qualRoda];
			RoletaItems item = roleta[qualRoda][index];
			
			if (rodasTravamento[qualRoda]) {
				if(e.getSource() == roleta1) roleta1.setIcon(item.getIcone());
				else if(e.getSource() == roleta2) roleta2.setIcon(item.getIcone());
				else if(e.getSource() == roleta3) roleta3.setIcon(item.getIcone());
				else if(e.getSource() == roleta4) roleta4.setIcon(item.getIcone());
				else if(e.getSource() == roleta5) roleta5.setIcon(item.getIcone());
				
			} else {
				if(e.getSource() == roleta1) roleta1.setIcon(item.getIconeCheck());
				else if(e.getSource() == roleta2) roleta2.setIcon(item.getIconeCheck());
				else if(e.getSource() == roleta3) roleta3.setIcon(item.getIconeCheck());
				else if(e.getSource() == roleta4) roleta4.setIcon(item.getIconeCheck());
				else if(e.getSource() == roleta5) roleta5.setIcon(item.getIconeCheck());
			}
			
			rodasTravamento[qualRoda] = !rodasTravamento[qualRoda]; // Inverte o próprio sinal
			
		}
		
	}
	
	@Override
	public JPanel getPainel() {
		return painel;
	}
	
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
	}
	
	private class listenerRoleta implements ActionListener {
		
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
				
			} else {
				
				// Se for 2 pessoas jogando, então smp continuam os 2 jogando
				// Se for 4 pessoas jogando, então é joga e espera, joga e espera
				
				int[] vetorRoleta = verificarRoleta();
				
				int energiaEsqAliado;
				int expEsqAliado;
				int energiaDirAliado;
				int expDirAliado;
				int marteloAliado;
				
				int energiaEsqInimigo;
				int expEsqInimigo;
				int energiaDirInimigo;
				int expDirInimigo;
				int marteloInimigo;
				
				energiaEsqAliado = vetorRoleta[0];
				expEsqAliado = vetorRoleta[1];
				energiaDirAliado = vetorRoleta[2];
				expDirAliado = vetorRoleta[3];
				marteloAliado = vetorRoleta[4];
				
				energiaEsqAliado -= 2;
				energiaDirAliado -= 2;
				marteloAliado -= 2;
				
				if (energiaEsqAliado < 0) energiaEsqAliado = 0;
				if (energiaDirAliado < 0) energiaDirAliado = 0;
				if (marteloAliado < 0) marteloAliado = 0;
				
				Castelo casteloAliado = partida.getCasteloAliado();
				Castelo casteloInimigo = partida.getCasteloInimigo();
				
				if (jogaJ1eJ2 && (partida.getJ1().getIP().equals(euUsuario.getIP()) || partida.getJ2().getIP().equals(euUsuario.getIP()))) {
					// Aqui se receberia via socket do oponente
					// E enviaria via socket o resultado aliado
					energiaEsqInimigo = 0;
					expEsqInimigo = 0;
					energiaDirInimigo = 0;
					expDirInimigo = 0;
					marteloInimigo = 0;
					
					// Por fim ataca e é atacado
					casteloAliado.constroiMuro(marteloAliado);
					casteloInimigo.constroiMuro(marteloInimigo);
					
					casteloAliado.getPeca1J1().aumentarNivel(expEsqAliado, casteloInimigo);
					casteloAliado.getPeca2J1().aumentarNivel(expDirAliado, casteloInimigo);
					casteloInimigo.getPeca1J1().aumentarNivel(expEsqInimigo, casteloAliado);
					casteloInimigo.getPeca2J1().aumentarNivel(expDirInimigo, casteloAliado);
					
					casteloAliado.getPeca1J1().atacar(energiaEsqAliado, casteloInimigo);
					casteloAliado.getPeca2J1().atacar(energiaDirAliado, casteloInimigo);
					casteloInimigo.getPeca1J1().atacar(energiaEsqInimigo, casteloAliado);
					casteloInimigo.getPeca2J1().atacar(energiaDirInimigo, casteloAliado);
					
					casteloAliado.getPeca1J1().aumentarNivel(expEsqAliado, casteloInimigo);
					casteloAliado.getPeca2J1().aumentarNivel(expDirAliado, casteloInimigo);
					casteloInimigo.getPeca1J1().aumentarNivel(expEsqInimigo, casteloAliado);
					casteloInimigo.getPeca2J1().aumentarNivel(expDirInimigo, casteloAliado);
					
					if(partida.getJ4() == null) {
						moedasRestantes = 3;
						imgMoeda1.setIcon(moedaCheia);
						imgMoeda2.setIcon(moedaCheia);
						imgMoeda3.setIcon(moedaCheia);
						botaoRoletaRodar.setText("RODAR");
					} else {
						jogaJ1eJ2 = false;
						botaoRoletaRodar.setText("AGUARDE");
					}
					
				} else if (jogaJ1eJ2 == false && (partida.getJ3().getIP().equals(euUsuario.getIP()) || partida.getJ4().getIP().equals(euUsuario.getIP()))) {
					// Aqui se receberia via socket do oponente
					// E enviaria via socket o resultado aliado
					energiaEsqInimigo = 0;
					expEsqInimigo = 0;
					energiaDirInimigo = 0;
					expDirInimigo = 0;
					marteloInimigo = 0;
					
					// Por fim ataca e é atacado
					casteloAliado.constroiMuro(marteloAliado);
					casteloInimigo.constroiMuro(marteloInimigo);
					
					casteloAliado.getPeca1J2().aumentarNivel(expEsqAliado, casteloInimigo);
					casteloAliado.getPeca2J2().aumentarNivel(expDirAliado, casteloInimigo);
					casteloInimigo.getPeca1J2().aumentarNivel(expEsqInimigo, casteloAliado);
					casteloInimigo.getPeca2J2().aumentarNivel(expDirInimigo, casteloAliado);
					
					casteloAliado.getPeca1J2().atacar(energiaEsqAliado, casteloInimigo);
					casteloAliado.getPeca2J2().atacar(energiaDirAliado, casteloInimigo);
					casteloInimigo.getPeca1J2().atacar(energiaEsqInimigo, casteloAliado);
					casteloInimigo.getPeca2J2().atacar(energiaDirInimigo, casteloAliado);
					
					casteloAliado.getPeca1J2().aumentarNivel(expEsqAliado, casteloInimigo);
					casteloAliado.getPeca2J2().aumentarNivel(expDirAliado, casteloInimigo);
					casteloInimigo.getPeca1J2().aumentarNivel(expEsqInimigo, casteloAliado);
					casteloInimigo.getPeca2J2().aumentarNivel(expDirInimigo, casteloAliado);
					
					jogaJ1eJ2 = true;
					botaoRoletaRodar.setText("AGUARDE");
					
				} else {
					JOptionPane.showMessageDialog(null, "Agora aguarde a vez do seu parceiro.");
					// Fazer os sockets esperarem por novas infos...
				}		
				
				if (casteloAliado.getVida() <= 0 && casteloInimigo.getVida() <= 0) {
					JOptionPane.showMessageDialog(null, "Empate, os dois reis morreram!");
					janelaMae.trocaPainel(painelDestino);
				}
				
				else if (casteloAliado.getVida() <= 0) {
					JOptionPane.showMessageDialog(null, "Você perdeu, seu rei foi morto!");
					janelaMae.trocaPainel(painelDestino);
				}
				
				else if (casteloInimigo.getVida() <= 0) {
					JOptionPane.showMessageDialog(null, "Parabéns você ganhou, o rei inimigo está morto!");
					janelaMae.trocaPainel(painelDestino);
				}
				
			}
		}	
	}
	
	public void setPartida(Partida p) {
		this.partida = p;
	}

	@Override
	public void limparCampos() {
		// TODO Nao faz nada
		
	}
}

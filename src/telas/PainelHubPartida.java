package telas;

import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import controladoras.ControladorPrincipal;
import jogo.ArqueiraPeca;
import jogo.AssassinoPeca;
import jogo.CavaleiroPeca;
import jogo.ClerigoPeca;
import jogo.ConstrutorPeca;
import jogo.MagoPeca;
import jogo.Peca;
import main.InfoPartida;
import main.Partida;
import main.Usuario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class PainelHubPartida implements Painel{

	private JPanel painel;
	private InfoPartida partida;
	
	private int qualJogadorSou;
	private boolean[] pronto = {false, false, false, false};
	
	private JButton botaoConectar;
	
	private JPanel painelTimes;
	private JPanel painelTimeVermelho;
	private JPanel painelTimeAzul;
		
	private JPanel painelDivisaoAzul;
	private JLabel labelTimeAzul;
	private JPanel painelSelecaoPecasAzul1;
	private JPanel painelSelecaoPecasAzul2;
	
	private JLabel labelJogador1;
	private ListenerJComboBox listenerJ1P1;
	private ListenerJComboBox listenerJ1P2;
	private JComboBox<Peca> jogador1Peca1;
	private JComboBox<Peca> jogador1Peca2;
	
	private JLabel labelJogador3;
	private ListenerJComboBox listenerJ3P1;
	private ListenerJComboBox listenerJ3P2;
	private JComboBox<Peca> jogador3Peca1;
	private JComboBox<Peca> jogador3Peca2;
	
	private JLabel labelTimeVermelho;
	private JPanel painelSelecaoPecasVermelho1;
	private JPanel painelSelecaoPecasVermelho2;
	
	private JLabel labelJogador2;
	private ListenerJComboBox listenerJ2P1;
	private ListenerJComboBox listenerJ2P2;
	private JComboBox<Peca> jogador2Peca1;
	private JComboBox<Peca> jogador2Peca2;
	
	private JLabel labelJogador4;
	private ListenerJComboBox listenerJ4P1;
	private ListenerJComboBox listenerJ4P2;
	private JComboBox<Peca> jogador4Peca1;
	private JComboBox<Peca> jogador4Peca2;
	
	private Peca[] pecasDoJogo = {
			new CavaleiroPeca(), 	// 0
			new MagoPeca(), 		// 1
			new ArqueiraPeca(),		// 2
			new ClerigoPeca(),		// 3
			new AssassinoPeca(),	// 4
			new ConstrutorPeca()	// 5
	};
	
	// Construtor do PainelHubPartida.
	public PainelHubPartida() {
		painel = new JPanel();
		painel.setBackground(Color.GRAY);
		painel.setMaximumSize(new Dimension(800, 600));
		painel.setLayout(null);
		
		partida = new InfoPartida(null, null);
		
		// Definindo os paineis com suas propriedades.
		JPanel painelDelimitador = new JPanel();
		painelDelimitador.setBorder(new EmptyBorder(10, 20, 10, 20));
		painelDelimitador.setForeground(new Color(0, 0, 0));
		painelDelimitador.setBackground(Color.LIGHT_GRAY);
		painelDelimitador.setBounds(29, 23, 725, 521);
		painel.add(painelDelimitador);
		
		JPanel painelBotoes = new JPanel();
		painelBotoes.setBackground(Color.LIGHT_GRAY);
		painelBotoes.setLayout(new GridLayout(0, 1, 80, 0));
		
		// Definindo o botaoConectar com suas propriedades e "Estou pronto!".
		botaoConectar = new JButton("Estou pronto!");
		botaoConectar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoConectar.addActionListener(new ListenerConectar());
		
		painelBotoes.add(botaoConectar);
		
		painelTimes = new JPanel();
		GroupLayout gl_painelDelimitador = new GroupLayout(painelDelimitador);
		gl_painelDelimitador.setHorizontalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addGroup(gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painelDelimitador.createSequentialGroup()
							.addContainerGap()
							.addComponent(painelTimes, GroupLayout.PREFERRED_SIZE, 670, Short.MAX_VALUE))
						.addGroup(gl_painelDelimitador.createSequentialGroup()
							.addGap(143)
							.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_painelDelimitador.setVerticalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addGap(29)
					.addComponent(painelTimes, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		painelTimes.setLayout(new GridLayout(2, 0, 0, 0));
		
		// Definindo os paineis em relacao ao time azul.
		painelTimeAzul = new JPanel();
		painelTimes.add(painelTimeAzul);
		
		labelTimeAzul = new JLabel("Time Azul");
		labelTimeAzul.setForeground(new Color(0, 128, 255));
		labelTimeAzul.setFont(new Font("Bauhaus 93", Font.PLAIN, 20));
		
		painelDivisaoAzul = new JPanel();
		painelDivisaoAzul.setLayout(new GridLayout(2, 2, 0, 0));
		
		labelJogador1 = new JLabel("PlaceHolderName(IP:PORT)");
		labelJogador1.setFont(new Font("Arial", Font.PLAIN, 14));
		painelDivisaoAzul.add(labelJogador1);
		
		painelSelecaoPecasAzul1 = new JPanel();
		painelDivisaoAzul.add(painelSelecaoPecasAzul1);
		painelSelecaoPecasAzul1.setLayout(new GridLayout(0, 2, 0, 0));
		
		jogador1Peca1 = new JComboBox<>(pecasDoJogo);
		jogador1Peca2 = new JComboBox<>(pecasDoJogo);
		
		listenerJ1P1 = new ListenerJComboBox(jogador1Peca1, jogador1Peca2, true);
		jogador1Peca1.addActionListener(listenerJ1P1);
		
		jogador1Peca1.setBackground(new Color(170, 213, 255));
		painelSelecaoPecasAzul1.add(jogador1Peca1);
		
		listenerJ1P2 = new ListenerJComboBox(jogador1Peca1, jogador1Peca2, false);
		jogador1Peca2.addActionListener(listenerJ1P2);
		
		jogador1Peca2.setBackground(new Color(170, 213, 255));
		painelSelecaoPecasAzul1.add(jogador1Peca2);
		
		labelJogador3 = new JLabel("PlaceHolderName(IP:PORT)");
		labelJogador3.setFont(new Font("Arial", Font.PLAIN, 14));
		painelDivisaoAzul.add(labelJogador3);
		
		painelSelecaoPecasAzul2 = new JPanel();
		painelDivisaoAzul.add(painelSelecaoPecasAzul2);
		painelSelecaoPecasAzul2.setLayout(new GridLayout(0, 2, 0, 0));
		
		jogador3Peca1 = new JComboBox<>(pecasDoJogo);
		jogador3Peca2 = new JComboBox<>(pecasDoJogo);
		
		listenerJ3P1 = new ListenerJComboBox(jogador3Peca1, jogador3Peca2, true);
		jogador3Peca1.addActionListener(listenerJ3P1);
		
		jogador3Peca1.setBackground(new Color(170, 213, 255));
		painelSelecaoPecasAzul2.add(jogador3Peca1);
		
		listenerJ3P2 = new ListenerJComboBox(jogador3Peca1, jogador3Peca2, false);
		jogador3Peca2.addActionListener(listenerJ3P2);
		
		jogador3Peca2.setBackground(new Color(170, 213, 255));
		painelSelecaoPecasAzul2.add(jogador3Peca2);
		GroupLayout gl_painelTimeAzul = new GroupLayout(painelTimeAzul);
		gl_painelTimeAzul.setHorizontalGroup(
			gl_painelTimeAzul.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTimeAzul.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelTimeAzul.createParallelGroup(Alignment.LEADING)
						.addComponent(labelTimeAzul)
						.addComponent(painelDivisaoAzul, GroupLayout.PREFERRED_SIZE, 645, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_painelTimeAzul.setVerticalGroup(
			gl_painelTimeAzul.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTimeAzul.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelTimeAzul)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelDivisaoAzul, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		painelTimeAzul.setLayout(gl_painelTimeAzul);
		
		// Definindo os paineis em relacao ao time vermelho.
		// Levando em consideracao que ha implementacao para 4 jogadores.
		painelTimeVermelho = new JPanel();
		painelTimes.add(painelTimeVermelho);
		
		labelTimeVermelho = new JLabel("Time Vermelho");
		labelTimeVermelho.setForeground(new Color(255, 0, 0));
		labelTimeVermelho.setFont(new Font("Bauhaus 93", Font.PLAIN, 20));
		
		JPanel painelSelecaoPecasVermelha = new JPanel();
		GroupLayout gl_painelTimeVermelho = new GroupLayout(painelTimeVermelho);
		gl_painelTimeVermelho.setHorizontalGroup(
			gl_painelTimeVermelho.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTimeVermelho.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelTimeVermelho.createParallelGroup(Alignment.LEADING)
						.addComponent(painelSelecaoPecasVermelha, GroupLayout.PREFERRED_SIZE, 645, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelTimeVermelho))
					.addContainerGap(91, Short.MAX_VALUE))
		);
		gl_painelTimeVermelho.setVerticalGroup(
			gl_painelTimeVermelho.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTimeVermelho.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelTimeVermelho)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelSelecaoPecasVermelha, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
					.addContainerGap())
		);
		painelSelecaoPecasVermelha.setLayout(new GridLayout(2, 2, 0, 0));
		
		labelJogador2 = new JLabel("PlaceHolderName(IP:PORT)");
		labelJogador2.setFont(new Font("Arial", Font.PLAIN, 14));
		painelSelecaoPecasVermelha.add(labelJogador2);
		
		painelSelecaoPecasVermelho1 = new JPanel();
		painelSelecaoPecasVermelha.add(painelSelecaoPecasVermelho1);
		painelSelecaoPecasVermelho1.setLayout(new GridLayout(0, 2, 0, 0));
		
		jogador2Peca1 = new JComboBox<>(pecasDoJogo);
		jogador2Peca2 = new JComboBox<>(pecasDoJogo);
		
		listenerJ2P1 = new ListenerJComboBox(jogador2Peca1, jogador2Peca2, true);
		jogador2Peca1.addActionListener(listenerJ2P1);
		
		jogador2Peca1.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho1.add(jogador2Peca1);
		
		listenerJ2P2 = new ListenerJComboBox(jogador2Peca1, jogador2Peca2, false);
		jogador2Peca2.addActionListener(listenerJ2P2);
		
		jogador2Peca2.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho1.add(jogador2Peca2);
		
		labelJogador4 = new JLabel("PlaceHolderName(IP:PORT)");
		labelJogador4.setFont(new Font("Arial", Font.PLAIN, 14));
		painelSelecaoPecasVermelha.add(labelJogador4);
		
		painelSelecaoPecasVermelho2 = new JPanel();
		painelSelecaoPecasVermelha.add(painelSelecaoPecasVermelho2);
		painelSelecaoPecasVermelho2.setLayout(new GridLayout(0, 2, 0, 0));
		
		jogador4Peca1 = new JComboBox<>(pecasDoJogo);
		jogador4Peca2 = new JComboBox<>(pecasDoJogo);
		
		listenerJ4P1 = new ListenerJComboBox(jogador4Peca1, jogador4Peca2, true);
		jogador4Peca1.addActionListener(listenerJ4P1);
		
		jogador4Peca1.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho2.add(jogador4Peca1);
		
		listenerJ4P2 = new ListenerJComboBox(jogador4Peca1, jogador4Peca2, false);
		jogador4Peca2.addActionListener(listenerJ4P2);
		
		jogador4Peca2.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho2.add(jogador4Peca2);
		painelTimeVermelho.setLayout(gl_painelTimeVermelho);
		painelDelimitador.setLayout(gl_painelDelimitador);
		resetaPecas();
	}
	
	private class ListenerJComboBox implements ActionListener {

		private JComboBox<Peca> box1;
		private JComboBox<Peca> box2;
		private boolean souBox1;
		private boolean possoEscutar;
		
		// Construtor do ListenerJComboBox com as pecas e quem sou.
		public ListenerJComboBox(JComboBox<Peca> box1, JComboBox<Peca> box2, boolean souBox1) {
			this.box1 = box1;
			this.box2 = box2;
			this.souBox1 = souBox1;
			this.possoEscutar = true;
		}
		
		// Metodo especifico para escolher as pecas no hub antes da partida.
		@Override
		public synchronized void actionPerformed(ActionEvent e) {
			int indexPeca1 = box1.getSelectedIndex();
			int indexPeca2 = box2.getSelectedIndex();
			
			if (possoEscutar) {
				if (indexPeca1 == indexPeca2) {
					JOptionPane.showMessageDialog(null, "Você não pode escolher a mesma peça", "Erro: Peça igual", JOptionPane.ERROR_MESSAGE);
					
					desativarEscuta();
					if (souBox1) box1.setSelectedIndex((indexPeca1 + 1) % pecasDoJogo.length);
					else 		 box2.setSelectedIndex((indexPeca2 + 1) % pecasDoJogo.length);
					ativarEscuta();
					
				} else {
					// Linha abaixo nao esta funcionado
					// ControladorPrincipal.atualizarHubPeca(qualJogadorSou, indexPeca1, indexPeca2);
				}
			} 
		}
		
		// Metodo que desativa o "ouvido".
		public void desativarEscuta() {
			this.possoEscutar = false;
		}
		
		// Metodo que ativa o "ouvido".
		public void ativarEscuta() {
			this.possoEscutar = true;
		}
		
	}
	
	// Metodo responsavel por setar as pecas de acordo com suas pecas.
	public void setPecas(int jogador, int indexPeca1, int indexPeca2) {
		System.out.println("O jogador: " + jogador + " Alterou suas pecas para: " + indexPeca1 + " " + indexPeca2);
		if (jogador == 1) {
			jogador1Peca1.setEnabled(true);
			jogador1Peca2.setEnabled(true);
			
			listenerJ1P1.desativarEscuta();
			listenerJ1P2.desativarEscuta();
			
			jogador1Peca1.setSelectedIndex(indexPeca1);
			jogador1Peca2.setSelectedIndex(indexPeca2);
			
			listenerJ1P1.ativarEscuta();
			listenerJ1P2.ativarEscuta();
			
			jogador1Peca1.setEnabled(false);
			jogador1Peca2.setEnabled(false);
			
		} else if (jogador == 2) {
			jogador2Peca1.setEnabled(true);
			jogador2Peca2.setEnabled(true);
			
			listenerJ2P1.desativarEscuta();
			listenerJ2P2.desativarEscuta();
			
			jogador2Peca1.setSelectedIndex(indexPeca1);
			jogador2Peca2.setSelectedIndex(indexPeca2);
			
			listenerJ2P1.ativarEscuta();
			listenerJ2P2.ativarEscuta();
			
			jogador2Peca1.setEnabled(false);
			jogador2Peca2.setEnabled(false);
		
		} else if (jogador == 3) {
			jogador3Peca1.setEnabled(true);
			jogador3Peca2.setEnabled(true);
			
			listenerJ3P1.desativarEscuta();
			listenerJ3P2.desativarEscuta();
			
			jogador3Peca1.setSelectedIndex(indexPeca1);
			jogador3Peca2.setSelectedIndex(indexPeca2);
			
			listenerJ3P1.ativarEscuta();
			listenerJ3P2.ativarEscuta();
			
			jogador3Peca1.setEnabled(false);
			jogador3Peca2.setEnabled(false);
		
		} else if (jogador == 4) {
			jogador4Peca1.setEnabled(true);
			jogador4Peca2.setEnabled(true);
			
			listenerJ4P1.desativarEscuta();
			listenerJ4P2.desativarEscuta();
			
			jogador4Peca1.setSelectedIndex(indexPeca1);
			jogador4Peca2.setSelectedIndex(indexPeca2);
			
			listenerJ4P1.ativarEscuta();
			listenerJ4P2.ativarEscuta();
			
			jogador4Peca1.setEnabled(false);
			jogador4Peca2.setEnabled(false);
		
		}
	
		trancaEscolhasDePeca(); // Define as pecas.
		
		painel.repaint();
		
	}

	// Metodo responsavel por criar uma partida entre um usuario e outro.
	public void criarPartida(Usuario usuario, Usuario rival) {
		this.partida = new InfoPartida(usuario, rival);
		
		atualizarNomesJogadores();
		
		painel.repaint();
		
	}

	// Metodo responsavel pelos usuarios entrarem na partida especifica de cada.
	public void entraPartida(Usuario usuario) {
		this.partida.adicionarJogador(usuario);
		
		if      (partida.getJ1().equals(usuario)) qualJogadorSou = 1;
		else if (partida.getJ2().equals(usuario)) qualJogadorSou = 2;
		else if (partida.getJ3().equals(usuario)) qualJogadorSou = 3;
		else if (partida.getJ4().equals(usuario)) qualJogadorSou = 4;
		
		atualizarNomesJogadores();

		painel.repaint();

	}
	
	// Metodo que atualiza o nome dos jogadores e e
	// chamado nas funcoes anteriores.
	private void atualizarNomesJogadores() {
		Usuario j1 = partida.getJ1();
		Usuario j2 = partida.getJ2();
		Usuario j3 = partida.getJ3();
		Usuario j4 = partida.getJ4();
		
		jogador1Peca1.setEnabled(true);
		jogador1Peca2.setEnabled(true);
		
		jogador2Peca1.setEnabled(true);
		jogador2Peca2.setEnabled(true);
		
		jogador3Peca1.setEnabled(true);
		jogador3Peca2.setEnabled(true);
		
		jogador4Peca1.setEnabled(true);
		jogador4Peca2.setEnabled(true);
		
		if (j1 != null) {			
			labelJogador1.setText(j1.toString());	
			
		} else { 			
			labelJogador1.setText("J1 não está presente nesta partida");
			jogador1Peca1.setSelectedIndex(0);
			jogador1Peca2.setSelectedIndex(1);
		}
		
		if (j2 != null) {
			labelJogador2.setText(j2.toString());	
			
		} else { 			
			labelJogador2.setText("J2 não está presente nesta partida");
			jogador1Peca1.setSelectedIndex(0);
			jogador1Peca2.setSelectedIndex(1);
			
		}
		
		if (j3 != null) {
			labelJogador3.setText(j3.toString());						
		
		} else { 			
			labelJogador3.setText("J3 não está presente nesta partida");
			jogador1Peca1.setSelectedIndex(0);
			jogador1Peca2.setSelectedIndex(1);
			
		}
		
		if (j4 != null) {
			labelJogador4.setText(j4.toString());
						
		} else { 			
			labelJogador4.setText("J4 não está presente nesta partida");
			jogador1Peca1.setSelectedIndex(0);
			jogador1Peca2.setSelectedIndex(1);
			
		}
		
		trancaEscolhasDePeca();
	}
	
	// Metodo que atualiza o pronto de acordo com o jogador.
	public void atualizarPronto(boolean pronto, int qualJogadorSou) {
		this.pronto[qualJogadorSou] = pronto;
	}
	
	// Fazendo um listener para conectar jogadores.
	private class ListenerConectar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			pronto[qualJogadorSou - 1] = !pronto[qualJogadorSou - 1];
			
			// Enviando o pronto.
			ControladorPrincipal.enviarPronto(pronto[qualJogadorSou - 1], qualJogadorSou - 1);
			
			int i;
			for (i = 0; i < 4; i++) {
				if (pronto[i] == false) {
					if (i == 2 && partida.getJ3() == null && partida.getJ4() == null) {
						ControladorPrincipal.iniciarPartida(true, 2); // Iniciar partida com 2 jogadores.
						
						break;
					}
					else break;
				}
			}
			
			if (i == 4) {
				ControladorPrincipal.iniciarPartida(true, 4); // Aqui, no caso, 4.
			}
		}
		
	}
	
	// Seta qual e o jogador mediante o seu numero.
	public void setQualJogadorSou(int numeroJogador) {
		this.qualJogadorSou = numeroJogador;
	}
	
	// Metodo privado para resetar as pecas de cada jogador.
	private void resetaPecas() {
		listenerJ1P1.desativarEscuta();
		listenerJ1P2.desativarEscuta();
		
		listenerJ2P1.desativarEscuta();
		listenerJ2P2.desativarEscuta();
		
		listenerJ3P1.desativarEscuta();
		listenerJ3P2.desativarEscuta();
		
		listenerJ4P1.desativarEscuta();
		listenerJ4P2.desativarEscuta();
		
		jogador1Peca1.setSelectedIndex(0);
		jogador2Peca1.setSelectedIndex(0);
		jogador3Peca1.setSelectedIndex(0);
		jogador4Peca1.setSelectedIndex(0);
		
		jogador1Peca2.setSelectedIndex(1);
		jogador2Peca2.setSelectedIndex(1);
		jogador3Peca2.setSelectedIndex(1);
		jogador4Peca2.setSelectedIndex(1);
		
		listenerJ1P1.ativarEscuta();
		listenerJ1P2.ativarEscuta();
		
		listenerJ2P1.ativarEscuta();
		listenerJ2P2.ativarEscuta();
	
		listenerJ3P1.ativarEscuta();
		listenerJ3P2.ativarEscuta();
		
		listenerJ4P1.ativarEscuta();
		listenerJ4P2.ativarEscuta();
	}
	
	// Metodo que estabelece em definitivo a peca do jogador,
	// isto e, tranca a sua escolha.
	private void trancaEscolhasDePeca() {
		if (qualJogadorSou != 1) {
			jogador1Peca1.setEnabled(false);
			jogador1Peca2.setEnabled(false);
			
		} else {
			jogador1Peca1.setEnabled(true);
			jogador1Peca2.setEnabled(true);
		}
		
		if (qualJogadorSou != 2) {
			jogador2Peca1.setEnabled(false);
			jogador2Peca2.setEnabled(false);
			
		} else {
			jogador2Peca1.setEnabled(true);
			jogador2Peca2.setEnabled(true);
		}
		
		if (qualJogadorSou != 3) {
			jogador3Peca1.setEnabled(false);
			jogador3Peca2.setEnabled(false);
			
		} else {
			jogador3Peca1.setEnabled(true);
			jogador3Peca2.setEnabled(true);
		}
		
		if (qualJogadorSou != 4) {
			jogador4Peca1.setEnabled(false);
			jogador4Peca2.setEnabled(false);
			
		} else {
			jogador4Peca1.setEnabled(true);
			jogador4Peca2.setEnabled(true);
		}
	}

	// Seta o pronto utilizando qual jogador.
	public void setProntoIndex(boolean pronto, int qualJogadorSou) {
		this.pronto[qualJogadorSou - 1] = pronto;
	}
	
	// Metodos de get.
	public int getQualJogadorSou() {
		return qualJogadorSou;
	}
	
	public int getIndexPeca1() {
		int index = -2;
		
		if 		(qualJogadorSou == 1) index = jogador1Peca1.getSelectedIndex();
		else if (qualJogadorSou == 2) index = jogador2Peca1.getSelectedIndex();
		else if (qualJogadorSou == 3) index = jogador3Peca1.getSelectedIndex();
		else if (qualJogadorSou == 4) index = jogador4Peca1.getSelectedIndex();
		
		return index;
	}
	
	public int getIndexPeca2() {
		int index = -2;
		
		if 		(qualJogadorSou == 1) index = jogador1Peca2.getSelectedIndex();
		else if (qualJogadorSou == 2) index = jogador2Peca2.getSelectedIndex();
		else if (qualJogadorSou == 3) index = jogador3Peca2.getSelectedIndex();
		else if (qualJogadorSou == 4) index = jogador4Peca2.getSelectedIndex();
		
		return index;
	}
	
	public InfoPartida getInfoPartida() {
		return partida;
	}
	
	@Override
	public JPanel getPainel() {
		return painel;
	}

	// Metodo sobrescrito para limpar os campos da partida.
	@Override
	public void limparCampos() {
		partida = null;
		resetaPecas();
	}


	
}

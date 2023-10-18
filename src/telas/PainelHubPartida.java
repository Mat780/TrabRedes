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
import javax.swing.JComboBox;

public class PainelHubPartida implements Painel{

	private JPanel painel;
	private Usuario euUsuario;
	private Partida partida;
	
	private int qualJogadorSou;
	
	private JButton botaoConectar;
	private JButton botaoAtualizar;
	
	private JPanel painelTimes;
	private JPanel panelTimeVermelho;
	private JPanel painelTimeAzul;
		
	private JPanel painelSelecaoPecasAzul1;
	private JPanel painelDivisaoAzul;
	private JLabel labelTimeAzul;
	
	private JLabel labelJogador1;
	private JComboBox<Peca> jogador1Peca1;
	private JComboBox<Peca> jogador1Peca2;
	
	private JLabel labelJogador3;
	private JPanel painelSelecaoPecasAzul2;
	private JComboBox<Peca> jogador3Peca1;
	private JComboBox<Peca> jogador3Peca2;
	
	private JLabel labelTimeVermelho;
	private JPanel painelSelecaoPecasVermelho1;
	private JPanel painelSelecaoPecasVermelho2;
	
	private JLabel labelJogador2;
	private JComboBox<Peca> jogador2Peca1;
	private JComboBox<Peca> jogador2Peca2;
	
	private JLabel labelJogador4;
	private JComboBox<Peca> jogador4Peca1;
	private JComboBox<Peca> jogador4Peca2;
	
	
	private Peca[] pecasDoJogo = {
			new CavaleiroPeca(),
			new MagoPeca(),
			new ArqueiraPeca(),
			new ClerigoPeca(),
			new AssassinoPeca(),
			new ConstrutorPeca()
	};
	
	public PainelHubPartida(Usuario usuario) {
		painel = new JPanel();
		painel.setBackground(Color.GRAY);
		painel.setMaximumSize(new Dimension(800, 600));
		painel.setLayout(null);
		
		this.euUsuario = usuario;
		
		JPanel painelDelimitador = new JPanel();
		painelDelimitador.setBorder(new EmptyBorder(10, 20, 10, 20));
		painelDelimitador.setForeground(new Color(0, 0, 0));
		painelDelimitador.setBackground(Color.LIGHT_GRAY);
		painelDelimitador.setBounds(29, 23, 725, 521);
		painel.add(painelDelimitador);
		
		JPanel painelBotoes = new JPanel();
		painelBotoes.setBackground(Color.LIGHT_GRAY);
		painelBotoes.setLayout(new GridLayout(0, 2, 80, 0));
		
		botaoConectar = new JButton("Estou pronto!");
		botaoConectar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoConectar.addActionListener(new ListenerConectar());
		
		painelBotoes.add(botaoConectar);
		
		botaoAtualizar = new JButton("Trocar de time");
		botaoAtualizar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoAtualizar.addActionListener(new ListenerTrocaTime());
		
		painelBotoes.add(botaoAtualizar);
		
		painelTimes = new JPanel();
		GroupLayout gl_painelDelimitador = new GroupLayout(painelDelimitador);
		gl_painelDelimitador.setHorizontalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_painelDelimitador.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
						.addComponent(painelTimes, GroupLayout.PREFERRED_SIZE, 670, Short.MAX_VALUE)
						.addComponent(painelBotoes, GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_painelDelimitador.setVerticalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addGap(29)
					.addComponent(painelTimes, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		painelTimes.setLayout(new GridLayout(2, 0, 0, 0));
		
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
		
		jogador1Peca1.addActionListener(e -> {
			int indexPeca1 = jogador1Peca1.getSelectedIndex();
			int indexPeca2 = jogador1Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca1 + 1) % pecasDoJogo.length;
				jogador1Peca1.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ1(jogador1Peca1.getItemAt(indexPeca1), jogador1Peca2.getItemAt(indexPeca2));
			
		});
		
		jogador1Peca1.setBackground(new Color(170, 213, 255));
		painelSelecaoPecasAzul1.add(jogador1Peca1);
		
		jogador1Peca2 = new JComboBox<>(pecasDoJogo);
		
		jogador1Peca2.addActionListener(e -> {
			int indexPeca1 = jogador1Peca1.getSelectedIndex();
			int indexPeca2 = jogador1Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca2 + 1) % pecasDoJogo.length;
				jogador1Peca2.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ1(jogador1Peca1.getItemAt(indexPeca1), jogador1Peca2.getItemAt(indexPeca2));
		});
		
		jogador1Peca2.setBackground(new Color(170, 213, 255));
		painelSelecaoPecasAzul1.add(jogador1Peca2);
		
		labelJogador3 = new JLabel("PlaceHolderName(IP:PORT)");
		labelJogador3.setFont(new Font("Arial", Font.PLAIN, 14));
		painelDivisaoAzul.add(labelJogador3);
		
		painelSelecaoPecasAzul2 = new JPanel();
		painelDivisaoAzul.add(painelSelecaoPecasAzul2);
		painelSelecaoPecasAzul2.setLayout(new GridLayout(0, 2, 0, 0));
		
		jogador3Peca1 = new JComboBox<>(pecasDoJogo);
		
		jogador3Peca1.addActionListener(e -> {
			int indexPeca1 = jogador3Peca1.getSelectedIndex();
			int indexPeca2 = jogador3Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca1 + 1) % pecasDoJogo.length;
				jogador3Peca1.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ3(jogador3Peca1.getItemAt(indexPeca1), jogador3Peca2.getItemAt(indexPeca2));
		});
		
		jogador3Peca1.setBackground(new Color(170, 213, 255));
		painelSelecaoPecasAzul2.add(jogador3Peca1);
		
		jogador3Peca2 = new JComboBox<>(pecasDoJogo);
		
		jogador3Peca2.addActionListener(e -> {
			int indexPeca1 = jogador3Peca1.getSelectedIndex();
			int indexPeca2 = jogador3Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca1 + 1) % pecasDoJogo.length;
				jogador3Peca2.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ3(jogador3Peca1.getItemAt(indexPeca1), jogador3Peca2.getItemAt(indexPeca2));
		});
		
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
		
		panelTimeVermelho = new JPanel();
		painelTimes.add(panelTimeVermelho);
		
		labelTimeVermelho = new JLabel("Time Vermelho");
		labelTimeVermelho.setForeground(new Color(255, 0, 0));
		labelTimeVermelho.setFont(new Font("Bauhaus 93", Font.PLAIN, 20));
		
		JPanel painelSelecaoPecasVermelha = new JPanel();
		GroupLayout gl_panelTimeVermelho = new GroupLayout(panelTimeVermelho);
		gl_panelTimeVermelho.setHorizontalGroup(
			gl_panelTimeVermelho.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTimeVermelho.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelTimeVermelho.createParallelGroup(Alignment.LEADING)
						.addComponent(painelSelecaoPecasVermelha, GroupLayout.PREFERRED_SIZE, 645, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelTimeVermelho))
					.addContainerGap(91, Short.MAX_VALUE))
		);
		gl_panelTimeVermelho.setVerticalGroup(
			gl_panelTimeVermelho.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTimeVermelho.createSequentialGroup()
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
		
		jogador2Peca1.addActionListener(e -> {
			int indexPeca1 = jogador2Peca1.getSelectedIndex();
			int indexPeca2 = jogador2Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca1 + 1) % pecasDoJogo.length;
				jogador2Peca1.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ2(jogador2Peca1.getItemAt(indexPeca1), jogador2Peca2.getItemAt(indexPeca2));
		});
		
		jogador2Peca1.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho1.add(jogador2Peca1);
		
		jogador2Peca2 = new JComboBox<>(pecasDoJogo);
		
		jogador2Peca2.addActionListener(e -> {
			int indexPeca1 = jogador2Peca1.getSelectedIndex();
			int indexPeca2 = jogador2Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca1 + 1) % pecasDoJogo.length;
				jogador2Peca2.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ2(jogador2Peca1.getItemAt(indexPeca1), jogador2Peca2.getItemAt(indexPeca2));
		});
		
		jogador2Peca2.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho1.add(jogador2Peca2);
		
		labelJogador4 = new JLabel("PlaceHolderName(IP:PORT)");
		labelJogador4.setFont(new Font("Arial", Font.PLAIN, 14));
		painelSelecaoPecasVermelha.add(labelJogador4);
		
		painelSelecaoPecasVermelho2 = new JPanel();
		painelSelecaoPecasVermelha.add(painelSelecaoPecasVermelho2);
		painelSelecaoPecasVermelho2.setLayout(new GridLayout(0, 2, 0, 0));
		
		jogador4Peca1 = new JComboBox<>(pecasDoJogo);
		
		jogador4Peca1.addActionListener(e -> {
			int indexPeca1 = jogador4Peca1.getSelectedIndex();
			int indexPeca2 = jogador4Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca1 + 1) % pecasDoJogo.length;
				jogador4Peca1.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ4(jogador4Peca1.getItemAt(indexPeca1), jogador4Peca2.getItemAt(indexPeca2));
		});
		
		jogador4Peca1.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho2.add(jogador4Peca1);
		
		jogador4Peca2 = new JComboBox<>(pecasDoJogo);
		
		jogador4Peca2.addActionListener(e -> {
			int indexPeca1 = jogador4Peca1.getSelectedIndex();
			int indexPeca2 = jogador4Peca2.getSelectedIndex();
			
			if (indexPeca1 == indexPeca2) {
				int novoIndex = (indexPeca1 + 1) % pecasDoJogo.length;
				jogador4Peca2.setSelectedIndex(novoIndex);
			
			}
			
			partida.setPecaJ4(jogador4Peca1.getItemAt(indexPeca1), jogador4Peca2.getItemAt(indexPeca2));
		});
		
		jogador4Peca2.setBackground(new Color(255, 170, 170));
		painelSelecaoPecasVermelho2.add(jogador4Peca2);
		panelTimeVermelho.setLayout(gl_panelTimeVermelho);
		painelDelimitador.setLayout(gl_painelDelimitador);
		
	}
	
	private class ListenerTrocaTime implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int qualJogadorEuEra = qualJogadorSou;
			
			Usuario j1 = partida.getJ1();
			Usuario j2 = partida.getJ2();
			Usuario j3 = partida.getJ3();
			Usuario j4 = partida.getJ4();
			
			if (j1 == null) {
				partida.setJ1(euUsuario);
				qualJogadorSou = 1;
				
			} else if (j2 == null) {
				partida.setJ2(euUsuario);
				qualJogadorSou = 2;
				
			} else if (j3 == null) {
				partida.setJ3(euUsuario);
				qualJogadorSou = 3;
				
			} else if (j4 == null) {
				partida.setJ4(euUsuario);
				qualJogadorSou = 4;
				
			} else {
				return;
			}
			
			trocarPecasEntreJComboBox(qualJogadorSou, qualJogadorEuEra);
			
			if      (qualJogadorEuEra == 1) partida.setJ1(null);
			else if (qualJogadorEuEra == 2) partida.setJ2(null);
			else if (qualJogadorEuEra == 3) partida.setJ3(null);
			else    						partida.setJ4(null);
			
			ControladorPrincipal.atualizarPartidaParaTodos(partida);
			
		}
		
		private void trocarPecasEntreJComboBox(int jogadorAtual, int jogadorAntigo) {
			Peca peca1 = null;
			Peca peca2 = null;
			
			if (jogadorAntigo == 1) {
				peca1 = (Peca) jogador1Peca1.getSelectedItem();
				peca2 = (Peca) jogador1Peca2.getSelectedItem();
				
			} else if (jogadorAntigo == 2) {
				peca1 = (Peca) jogador2Peca1.getSelectedItem();
				peca2 = (Peca) jogador2Peca2.getSelectedItem();
				
			} else if (jogadorAntigo == 3) {
				peca1 = (Peca) jogador3Peca1.getSelectedItem();
				peca2 = (Peca) jogador3Peca2.getSelectedItem();
				
			} else if (jogadorAntigo == 4) {
				peca1 = (Peca) jogador4Peca1.getSelectedItem();
				peca2 = (Peca) jogador4Peca2.getSelectedItem();
			}
			
			if (jogadorAtual == 1) {
				partida.setPecaJ1(peca1, peca2);
				jogador1Peca1.setSelectedItem(peca1);
				jogador1Peca2.setSelectedItem(peca2);
				
			} else if (jogadorAtual == 2) {
				partida.setPecaJ2(peca1, peca2);
				jogador2Peca1.setSelectedItem(peca1);
				jogador2Peca2.setSelectedItem(peca2);
				
			} else if (jogadorAtual == 3) {
				partida.setPecaJ3(peca1, peca2);
				jogador3Peca1.setSelectedItem(peca1);
				jogador3Peca2.setSelectedItem(peca2);
				
			} else if (jogadorAtual == 4) {
				partida.setPecaJ4(peca1, peca2);
				jogador4Peca1.setSelectedItem(peca1);
				jogador4Peca2.setSelectedItem(peca2);
				
			}
			
		}
		
	}

	
	private class ListenerConectar implements ActionListener {

		public ListenerConectar() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void setPartida(Partida p) {
		partida = p;
		
		Usuario j1 = p.getJ1();
		Usuario j2 = p.getJ2();
		Usuario j3 = p.getJ3();
		Usuario j4 = p.getJ4();
		
		if (j1 != null) labelJogador1.setText(Usuario.staticToString(j1.getUsuario(), j1.getIP(), j1.getIP()));
		else 			labelJogador1.setText("J1 não está presente nesta partida");
		
		if (j2 != null) labelJogador2.setText(Usuario.staticToString(j2.getUsuario(), j2.getIP(), j2.getIP()));
		else  			labelJogador2.setText("J2 não está presente nesta partida");
		
		if (j3 != null) labelJogador3.setText(Usuario.staticToString(j3.getUsuario(), j3.getIP(), j3.getIP()));
		else 			labelJogador3.setText("J3 não está presente nesta partida");
		
		if (j4 != null) labelJogador4.setText(Usuario.staticToString(j4.getUsuario(), j4.getIP(), j4.getIP()));
		else 			labelJogador4.setText("J4 não está presente nesta partida");
		
		trancaEscolhasDePeca();
		
		painel.repaint();
	}
	
	private void trancaEscolhasDePeca() {
		boolean souJ1 = euUsuario.equals(partida.getJ1());
		boolean souJ2 = euUsuario.equals(partida.getJ2());
		boolean souJ3 = euUsuario.equals(partida.getJ3());
		boolean souJ4 = euUsuario.equals(partida.getJ4());
		
		if (souJ1 == false) {
			jogador1Peca1.setEnabled(false);
			jogador1Peca2.setEnabled(false);
			
			jogador1Peca1.setSelectedItem(partida.getCasteloInimigo().getPeca1J1());
			jogador1Peca2.setSelectedItem(partida.getCasteloInimigo().getPeca2J1());
		} else {
			jogador1Peca1.setEnabled(true);
			jogador1Peca2.setEnabled(true);
		}
		
		if (souJ2 == false) {
			jogador2Peca1.setEnabled(false);
			jogador2Peca2.setEnabled(false);
			
			jogador2Peca1.setSelectedItem(partida.getCasteloAliado().getPeca1J1());
			jogador2Peca2.setSelectedItem(partida.getCasteloAliado().getPeca2J1());
		} else {
			jogador2Peca1.setEnabled(true);
			jogador2Peca2.setEnabled(true);
		}
		
		if (souJ3 == false) {
			jogador3Peca1.setEnabled(false);
			jogador3Peca2.setEnabled(false);
			
			jogador3Peca1.setSelectedItem(partida.getCasteloInimigo().getPeca1J2());
			jogador3Peca2.setSelectedItem(partida.getCasteloInimigo().getPeca2J2());
		} else {
			jogador3Peca1.setEnabled(true);
			jogador3Peca2.setEnabled(true);
		}
		
		if (souJ4 == false) {
			jogador4Peca1.setEnabled(false);
			jogador4Peca2.setEnabled(false);
			
			jogador4Peca1.setSelectedItem(partida.getCasteloAliado().getPeca1J2());
			jogador4Peca2.setSelectedItem(partida.getCasteloAliado().getPeca2J2());
		} else {
			jogador4Peca1.setEnabled(true);
			jogador4Peca2.setEnabled(true);
		}
	}

	@Override
	public JPanel getPainel() {
		return painel;
	}

	@Override
	public void limparCampos() {
		partida = null;
	}

	
}

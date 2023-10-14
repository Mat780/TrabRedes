package telas;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

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
	private Partida partida;
	private JButton botaoConectar;
	private JButton botaoAtualizar;
	private JPanel painelTimes;
	private JPanel panelTimeVermelho;
	private JPanel painelTimeAzul;
	private JLabel labelTimeAzul;
	private JLabel labelTimeVermelho;
	private JLabel labelJogador2;
	private JPanel painelSelecaoPecasVermelho1;
	private JPanel painelSelecaoPecasVermelho2;
	private JComboBox<Peca> jogador2Peca1;
	private JComboBox<Peca> jogador2Peca2;
	private JComboBox<Peca> jogador4Peca1;
	private JComboBox<Peca> jogador4Peca2;
	private JPanel painelDivisaoAzul;
	private JLabel labelJogador1;
	private JPanel painelSelecaoPecasAzul1;
	private JComboBox<Peca> jogador1Peca1;
	private JComboBox<Peca> jogador1Peca2;
	private JLabel labelJogador3;
	private JPanel painelSelecaoPecasAzul2;
	private JComboBox<Peca> jogador3Peca1;
	private JComboBox<Peca> jogador3Peca2;
	
	private Peca[] pecasDoJogo = {
			new CavaleiroPeca(),
			new MagoPeca(),
			new ArqueiraPeca(),
			new ClerigoPeca(),
			new AssassinoPeca(),
			new ConstrutorPeca()
	};
	
	public PainelHubPartida(Painel painelAnterior) {
		painel = new JPanel();
		painel.setBackground(Color.GRAY);
		painel.setMaximumSize(new Dimension(800, 600));
		painel.setLayout(null);
		
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
		botaoConectar.addActionListener(e -> {
			//TODO
		});
		
		painelBotoes.add(botaoConectar);
		
		botaoAtualizar = new JButton("Trocar de time");
		botaoAtualizar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoAtualizar.addActionListener(e -> {
			//TODO
		});
		
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
		
		JLabel labelJogador4 = new JLabel("PlaceHolderName(IP:PORT)");
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
	
	public void setPartida(Partida p) {
		partida = p;
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

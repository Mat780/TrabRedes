package telas;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Cliente;
import main.Usuario;

import javax.swing.JList;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

import controladoras.ControladorPrincipal;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;

public class PainelListagemOnline implements Painel {

	private JPanel painel;
	private DefaultListModel<Usuario> listaUsuarios = new DefaultListModel<>();
	private JList<Usuario> listaNoPainel = new JList<>(listaUsuarios);
	private JButton botaoConectar;
	private JButton botaoAtualizar;
	private JLabel labelCadastrados;
	private JLabel labelOnline;
	private Cliente cliente;
	
	PainelListagemOnline(Cliente cliente) {
		painel = new JPanel();
		painel.setBackground(Color.GRAY);
		painel.setMaximumSize(new Dimension(800, 600));
		painel.setLayout(null);
		
		this.cliente = cliente;
		
		JPanel painelDelimitador = new JPanel();
		painelDelimitador.setBorder(new EmptyBorder(10, 20, 10, 20));
		painelDelimitador.setForeground(new Color(0, 0, 0));
		painelDelimitador.setBackground(Color.LIGHT_GRAY);
		painelDelimitador.setBounds(29, 23, 725, 521);
		painel.add(painelDelimitador);
		
		JPanel painelBotoes = new JPanel();
		painelBotoes.setBackground(Color.LIGHT_GRAY);
		painelBotoes.setLayout(new GridLayout(0, 2, 80, 0));
		
		// Definindo o botao de conectar e suas propriedades.
		botaoConectar = new JButton("Conectar");
		botaoConectar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoConectar.addActionListener(e -> {
			
			JOptionPane.showMessageDialog(null, "Iniciando requisicao com o jogador", "Requisição de jogo", JOptionPane.PLAIN_MESSAGE);
			
			//TODO Travar tudo
			
			if (listaNoPainel.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Primeiro selecione um jogador", "Erro: Nenhum jogador selecionado", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Usuario usr = listaNoPainel.getSelectedValue();
			
			boolean conexaoPossivel = ControladorPrincipal.conectarPeerToPeer(usr);
			if (conexaoPossivel == false) return; //TODO Destravar tudo
			
			boolean partidaAceita = ControladorPrincipal.convidarPartida();
			
			if (partidaAceita) {
				JOptionPane.showMessageDialog(null, "Jogador aceitou a partida", "Partida aceita", JOptionPane.INFORMATION_MESSAGE);
				ControladorPrincipal.criarHub(false, usr);
				
			} else {
				JOptionPane.showMessageDialog(null, "Jogador recusou sua partida", "Partida recusada", JOptionPane.INFORMATION_MESSAGE);
				ControladorPrincipal.encerrarTodasAsConexoes();
			}
			
		});
		
		painelBotoes.add(botaoConectar);
		
		// Definindo o botao de atualizar e suas propriedades.
		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoAtualizar.addActionListener(e -> {
			atualizarLista();
		});
		
		painelBotoes.add(botaoAtualizar);
		
		// Setando a forma, os componentes deste painel.
		JScrollPane painelComScroll = new JScrollPane();
		painelComScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel painelLabels = new JPanel();
		painelLabels.setBorder(new EmptyBorder(0, 10, 0, 0));
		painelLabels.setBackground(new Color(150, 150, 150));
		GroupLayout gl_painelDelimitador = new GroupLayout(painelDelimitador);
		gl_painelDelimitador.setHorizontalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_painelDelimitador.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelDelimitador.createParallelGroup(Alignment.TRAILING)
						.addComponent(painelLabels, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
						.addComponent(painelComScroll, GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
						.addComponent(painelBotoes, GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_painelDelimitador.setVerticalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addComponent(painelLabels, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelComScroll, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		painelLabels.setLayout(new GridLayout(0, 2, 50, 0));
		
		// Definindo e atribuindo as propriedades de "cadastrados".
		labelCadastrados = new JLabel("Cadastrados:");
		painelLabels.add(labelCadastrados);
		labelCadastrados.setFont(new Font("Arial", Font.BOLD, 18));
		
		// Definindo e atribuindo as propriedades de "online".
		labelOnline = new JLabel("Online:");
		labelOnline.setFont(new Font("Arial", Font.BOLD, 18));
		painelLabels.add(labelOnline);
		painelComScroll.setViewportView(listaNoPainel);
		listaNoPainel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaNoPainel.setFont(new Font("Arial", Font.PLAIN, 18));
		painelDelimitador.setLayout(gl_painelDelimitador);
	}
	
	// Metodo que atualiza lista dos usuarios mediante se estao cadastrados
	// ou se estao simplesmente online.
	public void atualizarLista(){
		listaUsuarios.clear();
		
		try {
			listaUsuarios.addAll(cliente.listarOnline());
			
			labelCadastrados.setText("Cadastrados: " + cliente.getQtdCadastrados());
			labelOnline.setText("Online: " + cliente.getQtdOnline());
			
		} catch (Exception e) {
			System.err.println("Erro: Ao atualizar lista de jogadores online");
			e.printStackTrace();
		}
		
		listaNoPainel.setModel(listaUsuarios);
	}
	
	// Metodo atualiza a lista e retorna painel. 
	@Override
	public JPanel getPainel() {
		atualizarLista();
		return painel;
	}

	// Metodo responsavel por limpar os campos.
	@Override
	public void limparCampos() {
		listaUsuarios.removeAllElements();
		listaNoPainel.setModel(listaUsuarios);
	}
}

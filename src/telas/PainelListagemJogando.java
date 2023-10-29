package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import main.Cliente;
import main.InfoPartida;

public class PainelListagemJogando implements Painel{
	
	private JPanel painel;
	private DefaultListModel<InfoPartida> listaPartidas = new DefaultListModel<>();
	private JList<InfoPartida> listaNoPainel = new JList<>(listaPartidas);
	private JButton botaoConectar;
	private JButton botaoAtualizar;
	private JLabel labelOnline;
	private JLabel labelJogando;
	private Cliente cliente;
	
	public PainelListagemJogando(Cliente cliente) {
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
			//TODO
		});
		
		// Definindo o botao de atualizar e suas propriedades.
		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoAtualizar.addActionListener(e -> 
			atualizarLista()
		);
		
		// Adicionando os botoes ao painel de botoes.
		painelBotoes.add(botaoConectar);
		painelBotoes.add(botaoAtualizar);
		
		JScrollPane painelComScroll = new JScrollPane();
		
		// Setando a forma, os componentes deste painel.
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
		
		// Definindo e atribuindo as propriedades de "online".
		labelOnline = new JLabel("Online: ");
		painelLabels.add(labelOnline);
		labelOnline.setFont(new Font("Arial", Font.BOLD, 18));
		
		// Definindo e atribuindo as propriedades de "jogando".
		labelJogando = new JLabel("Jogando:");
		labelJogando.setFont(new Font("Arial", Font.BOLD, 18));
		painelLabels.add(labelJogando);
		painelComScroll.setViewportView(listaNoPainel);
		listaNoPainel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaNoPainel.setFont(new Font("Arial", Font.PLAIN, 14));
		painelDelimitador.setLayout(gl_painelDelimitador);
	}
	
	// Metodo que atualiza lista dos usuarios mediante se estao jogando
	// ou se estao simplesmente online.
	private void atualizarLista() {
		listaPartidas.clear();
		
		try {
			listaPartidas.addAll(cliente.listarJogando());
			
			labelJogando.setText("Jogando: " + cliente.getQtdJogando());
			labelOnline.setText("Online: " + cliente.getQtdOnline());
			
		} catch (Exception e) {
			System.err.println("Erro: Ao atualizar a lista de jogadores que estão jogando");
			e.printStackTrace();
		}
		
		listaNoPainel.setModel(listaPartidas);
		listaNoPainel.repaint();
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
		listaPartidas.removeAllElements();
		listaNoPainel.setModel(listaPartidas);
	}
}

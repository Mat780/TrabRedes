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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import main.Partida;
import main.Usuario;

public class PainelListagemJogando implements Painel{
	
	private JPanel painel;
	private DefaultListModel<Partida> listaPartidas = new DefaultListModel<>();
	private JList<Partida> listaNoPainel = new JList<>(listaPartidas);
	private JButton botaoConectar;
	private JButton botaoAtualizar;
	private JLabel labelOnline;
	private JLabel labelJogando;
	
	public PainelListagemJogando() {
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
		
		botaoConectar = new JButton("Conectar");
		botaoConectar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoConectar.addActionListener(e -> {
			//TODO
		});
		
		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setFont(new Font("Arial", Font.PLAIN, 18));
		botaoAtualizar.addActionListener(e -> 
			atualizarLista()
		);
		
		painelBotoes.add(botaoConectar);
		painelBotoes.add(botaoAtualizar);
		
		JScrollPane painelComScroll = new JScrollPane();
		
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
		
		labelOnline = new JLabel("Online: ");
		painelLabels.add(labelOnline);
		labelOnline.setFont(new Font("Arial", Font.BOLD, 18));
		
		labelJogando = new JLabel("Jogando:");
		labelJogando.setFont(new Font("Arial", Font.BOLD, 18));
		painelLabels.add(labelJogando);
		painelComScroll.setViewportView(listaNoPainel);
		listaNoPainel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaNoPainel.setFont(new Font("Arial", Font.PLAIN, 14));
		painelDelimitador.setLayout(gl_painelDelimitador);
	}
	
	private void atualizarLista() {
		listaPartidas.removeAllElements();
		
		//TODO Chamar o banco de dados e registrar tudo denovo
		for(int i = 0; i < 30; i += 2) {
			listaPartidas.addElement(new Partida(new Usuario("Teste", "T" + i, "senhaManeira", 55555), new Usuario("Teste2", "T" + (i + 1), "senhaManeira", 55555)));			
		}
		
		//TODO Fazer as linha abaixo funcionar
		//labelCadastrados.setText("Cadastrados: " + getCadastrados());
		//labelOnline.setText("Online: " + getOnline());
		
		// Após a att é precisa setar novamente
		listaNoPainel.setModel(listaPartidas);
	}
	
	@Override
	public JPanel getPainel() {
		atualizarLista();
		return painel;
	}

	@Override
	public void limparCampos() {
		listaPartidas.removeAllElements();
		listaNoPainel.setModel(listaPartidas);
	}
}

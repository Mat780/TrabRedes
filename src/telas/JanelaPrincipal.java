package telas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import main.Usuario;

import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class JanelaPrincipal extends JFrame{

	private Painel painelAtual;
	private ArrayList<Painel> vetorPainel;
	
	private JMenuBar menuBar;
	private JMenu menuVisualizar;
	private JMenuItem menuItemOnline;
	private JMenuItem menuItemJogando;
	
	private Usuario user;
	
	public JanelaPrincipal(Usuario user) {
		super();
		setSize(new Dimension(800, 600));
		setResizable(false);
		getContentPane().setMinimumSize(new Dimension(800, 600));
		getContentPane().setMaximumSize(new Dimension(800, 600));
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
		
		this.user = user;

		configuraTelas();
		
		painelAtual = vetorPainel.get(3);
		
		getContentPane().add(painelAtual.getPainel(), BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		
		menuVisualizar = new JMenu("Visualizar");
		menuBar.add(menuVisualizar);
		
		menuItemOnline = new JMenuItem("Ver quem está online");
		menuItemOnline.addActionListener(new ListenerTrocaPainel(vetorPainel.get(0)));
		menuVisualizar.add(menuItemOnline);
		
		menuItemJogando = new JMenuItem("Ver quem está jogando");
		menuItemJogando.addActionListener(new ListenerTrocaPainel(vetorPainel.get(1)));
		menuVisualizar.add(menuItemJogando);
		setVisible(true);
		ativarMenuBar();
	}
	
	private void configuraTelas() {
		vetorPainel = new ArrayList<>();
		vetorPainel.add(new PainelListagemOnline());
		vetorPainel.add(new PainelListagemJogando());
		vetorPainel.add(new PainelHubPartida(vetorPainel.get(0)));
		vetorPainel.add(new PainelJogo(this, vetorPainel.get(0)));
	}
	
	public class ListenerTrocaPainel implements ActionListener {

		Painel painelDestino;
		
		public ListenerTrocaPainel(Painel p) {
			painelDestino = p;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			trocaPainel(painelDestino);
		}
		
	}
	
	public void trocaPainel(Painel p) {
		
		if (painelAtual != null) {
			painelAtual.limparCampos();
			getContentPane().remove(painelAtual.getPainel());
		}

		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i).getClass() == p.getClass()) {
				
				if (p.getClass() == PainelJogo.class) {
					desativarMenuBar();
					
				} else {
					ativarMenuBar();
				}
				
				painelAtual = vetorPainel.get(i);
				break;
			}
		}
		
		getContentPane().add(painelAtual.getPainel(), BorderLayout.CENTER);
		getContentPane().repaint();
		setVisible(true);
	}

	
	public void ativarMenuBar() {
		setJMenuBar(menuBar);
	}
	
	public void desativarMenuBar() {
		setJMenuBar(null);
	}
	
}


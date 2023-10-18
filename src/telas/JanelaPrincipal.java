package telas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import main.Cliente;
import main.ClientePeer;
import main.Partida;
import main.ServidorPeer;
import main.Usuario;

import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class JanelaPrincipal extends JFrame implements WindowListener{

	private static final long serialVersionUID = 5753254003603887493L;
	private Painel painelAtual;
	private ArrayList<Painel> vetorPainel;
	
	private JMenuBar menuBar;
	private JMenu menuVisualizar;
	private JMenuItem menuItemOnline;
	private JMenuItem menuItemJogando;
	
	private Usuario usuario;
	private Cliente cliente;
	
	private ClientePeer clientePeer;
	private ServidorPeer servidorPeer;
	
	private boolean isJogando = false;
	
	public JanelaPrincipal(Usuario user, Cliente cliente) {
		super();
		setSize(new Dimension(800, 600));
		setResizable(false);
		getContentPane().setMinimumSize(new Dimension(800, 600));
		getContentPane().setMaximumSize(new Dimension(800, 600));
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
		
		this.usuario = user;
		this.cliente = cliente;
				
		try {
			this.clientePeer = new ClientePeer();
			this.servidorPeer = new ServidorPeer(usuario);
			cliente.updatePort(Integer.toString(servidorPeer.getPort()));
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		configuraTelas();
		
		painelAtual = vetorPainel.get(0);
		
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
		vetorPainel.add(new PainelListagemOnline(cliente));
		vetorPainel.add(new PainelListagemJogando(cliente));
		vetorPainel.add(new PainelHubPartida(usuario));
		vetorPainel.add(new PainelJogo(vetorPainel.get(0)));
	}
	
	public class ListenerTrocaPainel implements ActionListener {

		private Painel painelDestino;
		
		public ListenerTrocaPainel(Painel p) {
			painelDestino = p;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				clientePeer.encerrarTodasAsConexoes();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
					isJogando = true;
					desativarMenuBar();
					
				} else {
					isJogando = false;
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

	public void cancelarPartida() throws IOException {
		clientePeer.encerrarTodasAsConexoes();
	}
	
	public void retornaPainelOnline() {
		isJogando = false;
		trocaPainel(vetorPainel.get(0));
	}
	
	public void ativarMenuBar() {
		setJMenuBar(menuBar);
	}
	
	public void desativarMenuBar() {
		setJMenuBar(null);
	}
	
	public void atualizarPartidaParaTodos(Partida partida) throws IOException {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				clientePeer.atualizarPartida(partida);
				hub.setPartida(partida);
			}
		}
	}
	
	public void atualizarPartidaParaMim(Partida partida) throws IOException {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				hub.setPartida(partida);
			}
		}
	}
	
	public ArrayList<Painel> getVetorPainel() {
		return vetorPainel;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// Vazio		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			if (isJogando) {
				clientePeer.cancelarPartida();
			}
					
			cliente.encerrarConexao();
			clientePeer.encerrarTodasAsConexoes();
			
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		} 
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// Vazio		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// Vazio		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// Vazio		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// Vazio
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// Vazio
		
	}
	
}


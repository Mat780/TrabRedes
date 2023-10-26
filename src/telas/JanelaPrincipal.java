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

import controladoras.ControladorPrincipal;
import main.Cliente;
import main.ClientePeer;
import main.InfoPartida;
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
	
	private ClientePeer clientePeer;
	private ServidorPeer servidorPeer;
	
	private Cliente cliente;
	
	private boolean isJogando = false;
	
	public JanelaPrincipal(Cliente cliente) {
		super();
		setSize(new Dimension(800, 600));
		setResizable(false);
		getContentPane().setMinimumSize(new Dimension(800, 600));
		getContentPane().setMaximumSize(new Dimension(800, 600));
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
		
		this.cliente = cliente;
				
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
		vetorPainel.add(new PainelHubPartida());
		vetorPainel.add(new PainelJogo());
	}
	
	public class ListenerTrocaPainel implements ActionListener {

		private Painel painelDestino;
		
		public ListenerTrocaPainel(Painel p) {
			painelDestino = p;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ControladorPrincipal.encerrarTodasAsConexoes();
			
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
		
		painelAtual.getPainel().repaint();
		getContentPane().add(painelAtual.getPainel(), BorderLayout.CENTER);
		getContentPane().repaint();
		setVisible(true);
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
	
	public void criarHubPartida(Usuario usuario, Usuario rival) {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				hub.criarPartida(usuario, rival);
				trocaPainel(hub);
			}
		}
	}
	
	public void atualizarPecas(int jogador, int indexPeca1, int indexPeca2) {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				hub.setPecas(jogador, indexPeca1, indexPeca2);
			}
		}
	}
	
	public void atualizarPronto(boolean pronto, int qualJogadorSou) {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				hub.atualizarPronto(pronto, qualJogadorSou);
			}
		}
	}
	
	public int[] getInfoStartPartida() {
		int[] info = {-1, -1, -1};
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				info[0] = hub.getQualJogadorSou();
				info[1] = hub.getIndexPeca1();
				info[2] = hub.getIndexPeca2();
				break;
			}
		}
		
		return info;
	}
	

	public InfoPartida getInfoPartida() {
		InfoPartida info = null;
		
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				info = hub.getInfoPartida();
				break;
			}
		}
		
		return info;
	}
	
	public void iniciarPecaDaPartida(int qualJogadorSou, int indexPeca1, int indexPeca2, int qtdJogadores) {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelJogo) {
				PainelJogo jogo = (PainelJogo) vetorPainel.get(i);
				jogo.iniciarPecaDaPartida(qualJogadorSou, indexPeca1, indexPeca2, qtdJogadores);
				trocaPainel(jogo);
				break;
			}
		}
		
	}
	
	public void setQualJogadorSou(int qualJogadorSou) {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				hub.setQualJogadorSou(qualJogadorSou);
			}
		}
	}
	
	public void receberJogada(int energiaEsq, int expEsq, int energiaDir, int expDir, int muro, int qualJogadorSou) {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelJogo) {
				PainelJogo jogo = (PainelJogo) vetorPainel.get(i);
				jogo.receberJogada(energiaEsq, expEsq, energiaDir, expDir, muro, qualJogadorSou);
			}
		}
	}
	
	public void receberPronto(boolean pronto, int qualJogadorSou) {
		for (int i = 0; i < vetorPainel.size(); i++) {
			if (vetorPainel.get(i) instanceof PainelHubPartida) {
				PainelHubPartida hub = (PainelHubPartida) vetorPainel.get(i);
				hub.setProntoIndex(pronto, qualJogadorSou);
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
				ControladorPrincipal.cancelarPartida();
			}
					
			cliente.encerrarConexao();
			ControladorPrincipal.encerrarTodasAsConexoes();
			
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
	
	public ServidorPeer getServidorPeer() {
		return servidorPeer;
	}
	
	public ClientePeer getClientePeer() {
		return clientePeer;
	}
	
}


package telas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.SwingConstants;

import main.Cliente;
import main.Usuario;
import telas.JanelaPrincipal.ListenerTrocaPainel;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

import controladoras.ControladorPrincipal;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class JanelaLogin extends JFrame {

	private JPanel painel;
	private JTextField inputNomeDeUsuario;
	private JTextField inputSenha;
	private Cliente cliente;
	
	private JPanel painelBotoes;
	private JButton botaoEntrar;
	private JButton botaoRegistrar;
	private JButton botaoTerminarCadastro;
	private JPanel painelInputs;
	private JPanel painelNomeDeUsuario;
	private JPanel painelSenha;
	private JPanel painelNomeReal;
	private JTextField inputNomeReal;
	private JPanel painelTerminarRegistro;
	
	public JanelaLogin() {
		super();
		setSize(new Dimension(800, 600));
		setTitle("Defenda o rei: Login");
		setResizable(false);
		getContentPane().setMinimumSize(new Dimension(800, 600));
		getContentPane().setMaximumSize(new Dimension(800, 600));
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// Tenta instanciar cliente.
		try {
			cliente = new Cliente();
		} catch (IOException e) {
			System.out.println("O cliente não conseguiu ser criado");
			e.printStackTrace();
		}
		
		// Instancia painel e define caracteristicas.
		painel = new JPanel();
		
		String fontLabel = "Arial Black";
		Font fontInput = new Font("Arial", Font.PLAIN, 18);
		
		JLabel labelTitulo = new JLabel("DEFENDA O REI"); // Titulo do jogo.
		labelTitulo.setBounds(100, 37, 561, 88);
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setFont(new Font(fontLabel, Font.PLAIN, 50));
		
		getContentPane().add(painel);
		
		// Define como os botoes serao.
		painelBotoes = new JPanel();
		painelBotoes.setBounds(169, 414, 430, 113);
		painelBotoes.setBorder(new EmptyBorder(10, 0, 10, 0));
		painelBotoes.setLayout(new GridLayout(0, 2, 60, 0));
		
		// Fazendo os recursos para entrar.
		botaoEntrar = new JButton("ENTRAR");
		painelBotoes.add(botaoEntrar);
		botaoEntrar.addActionListener(e -> {
			
			String[] infoUsr = {"Inicializando"};
			try {
				infoUsr = cliente.loginUsuario(inputNomeDeUsuario.getText(), inputSenha.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			if (infoUsr.length == 1) {
				JOptionPane.showMessageDialog(null, infoUsr[0]);
				
			} else {
				Usuario usuario = null;
				try {
					usuario = new Usuario(inputNomeDeUsuario.getText(), infoUsr[0], infoUsr[1], infoUsr[2], infoUsr[3]);
				} catch (UnknownHostException e1) {
					// Esse catch nunca deveria acontecer
					e1.printStackTrace();
				}
				
				ControladorPrincipal.entrarJanela(usuario, cliente);
				
			}			
		});
		botaoEntrar.setForeground(Color.BLACK);
		botaoEntrar.setFont(new Font("Arial Black", Font.PLAIN, 22));
		botaoEntrar.setBackground(new Color(128, 255, 128));
		
		// Definindo o botao de Registrar e suas propriedades.
		botaoRegistrar = new JButton("REGISTRAR");
		botaoRegistrar.addActionListener(e -> trocarEntreLoginERegistro());
		painelBotoes.add(botaoRegistrar);
		botaoRegistrar.setForeground(Color.BLACK);
		botaoRegistrar.setFont(new Font("Arial Black", Font.PLAIN, 22));
		botaoRegistrar.setBackground(new Color(128, 255, 128));
		painel.setLayout(null);
		painel.add(labelTitulo);
		painel.add(painelBotoes);
		
		// Definindo os inputs.
		painelInputs = new JPanel();
		painelInputs.setBounds(85, 158, 604, 245);
		painel.add(painelInputs);
		painelInputs.setLayout(new GridLayout(3, 1, 0, 0));
		
		painelNomeDeUsuario = new JPanel();
		painelInputs.add(painelNomeDeUsuario);
		painelNomeDeUsuario.setLayout(null);
		
		JLabel labelNomeDeUsuario = new JLabel("Nome de usuario\r\n");
		labelNomeDeUsuario.setBounds(10, 5, 166, 26);
		painelNomeDeUsuario.add(labelNomeDeUsuario);
		labelNomeDeUsuario.setFont(new Font(fontLabel, Font.PLAIN, 18));
		
		// Atribuindo as propriedades e os inputs a variavel correspondente.
		inputNomeDeUsuario = new JTextField();
		inputNomeDeUsuario.setBounds(10, 36, 584, 28);
		painelNomeDeUsuario.add(inputNomeDeUsuario);
		inputNomeDeUsuario.setFont(fontInput);
		inputNomeDeUsuario.setColumns(10);
		
		painelSenha = new JPanel();
		painelInputs.add(painelSenha);
		painelSenha.setLayout(null);
		
		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setBounds(10, 5, 61, 26);
		painelSenha.add(labelSenha);
		labelSenha.setFont(new Font(fontLabel, Font.PLAIN, 18));
		
		inputSenha = new JTextField();
		inputSenha.setBounds(10, 36, 584, 28);
		painelSenha.add(inputSenha);
		inputSenha.setFont(fontInput);
		inputSenha.setColumns(10);
		
		painelNomeReal = new JPanel();
		painelNomeReal.setLayout(null);
		
		inputNomeReal = new JTextField();
		inputNomeReal.setFont(new Font("Arial", Font.PLAIN, 18));
		inputNomeReal.setColumns(10);
		inputNomeReal.setBounds(10, 42, 584, 28);
		painelNomeReal.add(inputNomeReal);
		
		JLabel labelNomeReal = new JLabel("Nome real");
		labelNomeReal.setFont(new Font("Arial Black", Font.PLAIN, 18));
		labelNomeReal.setBounds(10, 11, 175, 26);
		painelNomeReal.add(labelNomeReal);
		painelInputs.add(painelNomeReal);
		
		painelTerminarRegistro = new JPanel();
		painelTerminarRegistro.setVisible(false);
		painelTerminarRegistro.setBorder(new EmptyBorder(20, 70, 20, 70));
		painelTerminarRegistro.setBounds(169, 414, 430, 113);
		painel.add(painelTerminarRegistro);
		painelTerminarRegistro.setLayout(new GridLayout(0, 1, 60, 0));
		
		// Botao e listener para terminar cadastro.
		botaoTerminarCadastro = new JButton("Terminar Cadastro");
		botaoTerminarCadastro.addActionListener(e -> {
			
			boolean nomeEstaVazio      = inputNomeDeUsuario.getText().isBlank() || inputNomeDeUsuario.getText().isEmpty();
			boolean senhaEstaVazia 	   = inputSenha.getText().isBlank() 		|| inputSenha.getText().isEmpty();
			boolean nomeRealEstaVazio  = inputNomeReal.getText().isBlank()		|| inputNomeReal.getText().isEmpty();
			
			if (nomeEstaVazio || senhaEstaVazia || nomeRealEstaVazio) {
				String[] opcoes = {"Sim", "Não"};
				int retornarAoLogin = JOptionPane.showInternalOptionDialog(null, "Parece que algum campo está vazio, deseja retornar ao menu?", "Retornar ao login?", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
				
				if (retornarAoLogin == 0) {
					trocarEntreLoginERegistro();
				}
				
				return;
			}
			
			boolean foiCadastrado = false;
			try {
				foiCadastrado = cliente.register(inputNomeDeUsuario.getText(), inputSenha.getText(), inputNomeReal.getText());
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			} 
			
			if (foiCadastrado == false) {
				JOptionPane.showMessageDialog(null, "Algum usuário já utiliza o nome de usuário escolhido");
				
			} else {
				JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
				trocarEntreLoginERegistro();
			}	
			
		});
		painelTerminarRegistro.add(botaoTerminarCadastro);
		botaoTerminarCadastro.setForeground(Color.BLACK);
		botaoTerminarCadastro.setFont(new Font("Arial Black", Font.PLAIN, 22));
		botaoTerminarCadastro.setBackground(new Color(128, 255, 128));
		painelNomeReal.setVisible(false);
		
		setVisible(true);
		
	}
	
	// Metodo responsavel trocar entre login e registro.
	private void trocarEntreLoginERegistro() {
		if (painelBotoes.isVisible()) {
			painelBotoes.setVisible(false);
			
			painelTerminarRegistro.setVisible(true);
			painelNomeReal.setVisible(true);
			
		} else {
			painelTerminarRegistro.setVisible(false);
			painelNomeReal.setVisible(false);
			
			painelBotoes.setVisible(true);
		}
		painel.repaint();
	}
}

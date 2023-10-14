package telas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

import main.Usuario;
import telas.JanelaPrincipal.ListenerTrocaPainel;

public class JanelaLogin extends JFrame {

	private JPanel painel;
	private JTextField inputNomeDeUsuario;
	private JTextField inputSenha;
	
	
	public JanelaLogin() {
		super();
		setSize(new Dimension(800, 600));
		setResizable(false);
		getContentPane().setMinimumSize(new Dimension(800, 600));
		getContentPane().setMaximumSize(new Dimension(800, 600));
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		painel = new JPanel();
		painel.setLayout(null);
		
		String fontLabel = "Arial Black";
		Font fontInput = new Font("Arial", Font.PLAIN, 18);
		
		JLabel labelTitulo = new JLabel("DEFENDA O REI");
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setFont(new Font(fontLabel, Font.PLAIN, 50));
		labelTitulo.setBounds(100, 37, 561, 88);
		painel.add(labelTitulo);
		
		JLabel labelNomeDeUsuario = new JLabel("Nome de usuario\r\n");
		labelNomeDeUsuario.setFont(new Font(fontLabel, Font.PLAIN, 18));
		labelNomeDeUsuario.setBounds(229, 218, 243, 14);
		painel.add(labelNomeDeUsuario);
		
		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setFont(new Font(fontLabel, Font.PLAIN, 18));
		labelSenha.setBounds(229, 309, 243, 14);
		painel.add(labelSenha);
		
		inputNomeDeUsuario = new JTextField();
		inputNomeDeUsuario.setFont(fontInput);
		inputNomeDeUsuario.setBounds(229, 243, 320, 33);
		inputNomeDeUsuario.setColumns(10);
		painel.add(inputNomeDeUsuario);
		
		inputSenha = new JTextField();
		inputSenha.setFont(fontInput);
		inputSenha.setBounds(229, 334, 320, 33);
		inputSenha.setColumns(10);
		painel.add(inputSenha);
		
		JButton botaoEntrar = new JButton("ENTRAR");
		botaoEntrar.addActionListener(e -> {
			
			Usuario usuario = new Usuario("Teste", "Testinho", "Dahora", 55555);
			
			dispose();
			JanelaPrincipal janelaPrincipal = new JanelaPrincipal(usuario);
		});
		botaoEntrar.setForeground(Color.BLACK);
		botaoEntrar.setFont(new Font("Arial Black", Font.PLAIN, 22));
		botaoEntrar.setBackground(new Color(128, 255, 128));
		botaoEntrar.setBounds(320, 452, 138, 62);
		painel.add(botaoEntrar);
		
		getContentPane().add(painel);
		
		setVisible(true);
		
	}
	
}

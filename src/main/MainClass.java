package main;

import telas.JanelaLogin;
import telas.JanelaPrincipal;

public class MainClass {
	
	public static void main(String[] args) {
		//JanelaLogin janelaLogin = new JanelaLogin();
		//JanelaPrincipal janelaPrincipal = new JanelaPrincipal(null);
		
		System.out.println("Entrando na main");
		
		try {
			Cliente cliente = new Cliente();
			
			Usuario usr = cliente.loginUsuario("Mats3", "123");
			
			if(usr != null) System.out.println("Sucesso");
			else System.out.println("Falha");
			
			
			if(usr != null) System.out.println("Sucesso");
			else System.out.println("Falha");
			
			cliente.encerrarConexao();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Finalizando cliente");
		
	}
	
}

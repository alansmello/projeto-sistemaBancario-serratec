package br.com.serratec.dominio;

import br.com.serratec.entidade.contas.SegurodeVida;

public class SistemaInterno {

	public static void main(String[] args) {
		SegurodeVida s1 = new SegurodeVida (100.00, "1648745125");
		System.out.println("Data de Expiração: " + s1.getDataExpiracao());

	}

	

}



/*		public void login(Autenticavel Pessoa ) {

String pegaSenha = JOptionPane.showInputDialog("Digite uma senha: ");
int senhaDigitada = Integer.parseInt(pegaSenha);
int senha = senhaDigitada;
boolean ok = Pessoa.autentica(senha);
*/
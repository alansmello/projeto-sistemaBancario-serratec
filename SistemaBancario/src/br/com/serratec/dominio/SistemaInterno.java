package br.com.serratec.dominio;

public class SistemaInterno {

import banco.util.Autenticavel;
import banco.pessoa.*;
import javax.swing.*;

	class SistemaInterno {

		public static void main(String[] args) {

		}

		public void login(Autenticavel Pessoa ) {

			String pegaSenha = JOptionPane.showInputDialog("Digite uma senha: ");
			int senhaDigitada = Integer.parseInt(pegaSenha);
			int senha = senhaDigitada;
			boolean ok = Pessoa.autentica(senha);

		}

	}

}

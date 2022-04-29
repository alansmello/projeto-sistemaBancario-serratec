package br.com.serratec.dominio;

import java.io.IOException;

import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.excecoes.cadastroExisteException;
import br.com.serratec.entidade.repositorios.RepositorioContas;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;

public class SistemaInterno {

	public static void main(String[] args) throws IOException, cadastroExisteException {
		/**SegurodeVida s1 = new SegurodeVida (100.00, "1648745125");
		System.out.println("Data de Expiração: " + s1.getDataExpiracao());

	}*/
		/*try {
			RepositorioUsuarios.usuarioLoader();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			System.out.println(RepositorioUsuarios.pesquisaUsuario("6161615"));
		} catch (CadastroInexistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			RepositorioContas.contasLoader();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			System.out.println(RepositorioContas.pesquisaContas("9515111551"));
		} catch (CadastroInexistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	

	}
}



/*		public void login(Autenticavel Pessoa ) {

String pegaSenha = JOptionPane.showInputDialog("Digite uma senha: ");
int senhaDigitada = Integer.parseInt(pegaSenha);
int senha = senhaDigitada;
boolean ok = Pessoa.autentica(senha);
*/
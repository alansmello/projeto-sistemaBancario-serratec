package br.com.serratec.dominio;

import java.io.IOException;

import br.com.serratec.entidade.contas.ContaCorrente;
import br.com.serratec.entidade.contas.ContaPoupanca;
import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.excecoes.CpfInexistenteException;
import br.com.serratec.entidade.excecoes.ValorInvalidoException;
import br.com.serratec.entidade.excecoes.ValorNegativoException;
import br.com.serratec.entidade.excecoes.cadastroExisteException;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;

public class SistemaInterno {

	public static void main(String[] args) throws IOException, cadastroExisteException, CadastroInexistenteException {
		try {
			RepositorioUsuarios.usuarioLoader();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		
		ContaCorrente c4 = new ContaCorrente (2541, 2545, "0913914", 10000);
		ContaCorrente c5 = new ContaCorrente (254, 541, "333333", 10000);
		c4.transferir(2501, c5);
		c4.sacar(100);
		c5.sacar(150);
		c4.ContratarSeguro(1000);
	
		ContaPoupanca alan = new ContaPoupanca (1111, 2222, "0123456", 300, 17);
		try {
			alan.simuladorPoupanca(1000, 365);
		} catch (ValorNegativoException | ValorInvalidoException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*try {
			System.out.println(RepositorioUsuarios.getUsuario("0913914").getNome());
		} catch (CpfInexistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
		
		try {
			try {
				c4.RelatorioTributos();
			} catch (CpfInexistenteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException | CadastroInexistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		/**SegurodeVida s1 = new SegurodeVida (100.00, "1648745125");
		System.out.println("Data de Expiração: " + s1.getDataExpiracao());

	}*/
		
		
		
		

	

	}}




/*		public void login(Autenticavel Pessoa ) {

String pegaSenha = JOptionPane.showInputDialog("Digite uma senha: ");
int senhaDigitada = Integer.parseInt(pegaSenha);
int senha = senhaDigitada;
boolean ok = Pessoa.autentica(senha);
*/
package br.com.serratec.dominio;

import java.io.IOException;
import java.util.Scanner;

import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.excecoes.cadastroExisteException;
import br.com.serratec.entidade.excecoes.senhaInvalidaException;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;
import br.com.serratec.entidade.usuarios.Cliente;
import br.com.serratec.entidade.usuarios.Diretor;
import br.com.serratec.entidade.usuarios.Gerente;
import br.com.serratec.entidade.usuarios.Presidente;
import br.com.serratec.entidade.usuarios.Usuario;

public class SistemaInterno {
	public static void main(String[] args){
		try {
			RepositorioUsuarios.usuarioLoader();
		} catch (IOException | cadastroExisteException e1) {
			System.out.println("Erro ao carregar os arquivos");
		}
		
		String cpf;
		String senha;
		Usuario usuario = null;
		Scanner leitor = new Scanner(System.in);
		
		
		
		System.out.println("============= LOG IN =============");
		
		do {
			System.out.print("Digite o CPF: ");
			cpf = leitor.nextLine();
			
			try {
				usuario = RepositorioUsuarios.pesquisaUsuario(cpf);
				break;
			} catch (CadastroInexistenteException e) {
				System.out.println("Usuário não encontrado");
			}
			
		}while(true);
		
		
		do {
			System.out.print("Digite a senha: ");
			senha = leitor.nextLine();
			
			try {
				usuario.autentica(senha);
				break;
			} catch (senhaInvalidaException e) {
				System.out.println("Senha inválida");
			}
		} while (true);
		
		
		if(usuario instanceof Cliente) {
			System.out.println("Bem vindo, " + usuario.getNome());
		}else if(usuario instanceof Gerente) {
			
		}else if(usuario instanceof Diretor) {
			
		}else if(usuario instanceof Presidente) {
			
		}
	}
}




/*		public void login(Autenticavel Pessoa ) {

String pegaSenha = JOptionPane.showInputDialog("Digite uma senha: ");
int senhaDigitada = Integer.parseInt(pegaSenha);
int senha = senhaDigitada;
boolean ok = Pessoa.autentica(senha);
*/
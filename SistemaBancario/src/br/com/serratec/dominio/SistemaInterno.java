package br.com.serratec.dominio;

import java.io.IOException;
import java.util.Scanner;

import br.com.serratec.entidade.contas.Conta;
import br.com.serratec.entidade.enums.TipoTaxa;
import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.excecoes.ValorInsuficienteException;
import br.com.serratec.entidade.excecoes.ValorNegativoException;
import br.com.serratec.entidade.excecoes.cadastroExisteException;
import br.com.serratec.entidade.excecoes.senhaInvalidaException;
import br.com.serratec.entidade.repositorios.RepositorioContas;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;
import br.com.serratec.entidade.usuarios.Cliente;
import br.com.serratec.entidade.usuarios.Diretor;
import br.com.serratec.entidade.usuarios.Gerente;
import br.com.serratec.entidade.usuarios.Presidente;
import br.com.serratec.entidade.usuarios.Usuario;

public class SistemaInterno {
	public static void main(String[] args) {
		Usuario usuario;
		Conta conta;
		Scanner leitor = new Scanner(System.in);
		
		carregarRepositorios();
		usuario = realizarLogin(leitor);
		conta = pegarConta(usuario);


		if (usuario instanceof Cliente) {
			int opcao;
			System.out.println("Bem vindo, " + usuario.getNome() + "\n");

			System.out.println("============== MENU INICIAL ==============");
			System.out.println("1 - Movimentação");
			System.out.println("2 - Mostrar saldo");
			System.out.println("3 - Relatórios");
			System.out.println("4 - Contratar seguro de vida");
			System.out.println("==========================================\n");
			System.out.print("Digite uma das opcoes acima: ");
			opcao = leitor.nextInt();
			leitor.nextLine();

			switch (opcao) {
			case 1:
				movimentacaoMenu(leitor, conta);
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:

				break;
			case 5:

				break;
			default:
				
				break;
			}

		} else if (usuario instanceof Gerente) {

		} else if (usuario instanceof Diretor) {

		} else if (usuario instanceof Presidente) {

		}
	}
	
	
	private static void carregarRepositorios() {
		try {
			RepositorioUsuarios.usuarioLoader();
			RepositorioContas.contasLoader();
		} catch (IOException | cadastroExisteException e1) {
			System.out.println("Erro ao carregar os arquivos");
		}

	}
	
	private static Usuario realizarLogin(Scanner leitor) {
		String cpf;
		String senha;
		Usuario usuario;
		
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

		} while (true);

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
		
		return usuario;
	}
	
	private static Conta pegarConta(Usuario usuario) {
		Conta conta = null;
		
		try {
			conta = RepositorioContas.pesquisaContas(usuario.getCpf());
		} catch (CadastroInexistenteException e) {
			e.printStackTrace();
		}
		
		return conta;
	}
	
	
	
	
	private static void movimentacaoMenu(Scanner leitor, Conta conta) {
		int opcao;
		
		System.out.println("================ MOVIMENTACOES ================");
		System.out.println("1 - saque");
		System.out.println("2 - deposito");
		System.out.println("3 - transferencia");
		System.out.println("4 - voltar");
		System.out.println("===============================================");
		
		System.out.print("\nEscolha uma das opcoes acima: ");
		opcao = leitor.nextInt();
		
		switch (opcao) {
		case 1:
			double valorSaque = 0;
			System.out.print("Digite o valor: ");
			valorSaque = leitor.nextDouble();
			try {
				conta.sacar(valorSaque);
			} catch (ValorNegativoException e) {
				System.out.println("Não é possível sacar um valor negativo");
			} catch (ValorInsuficienteException e) {
				System.out.println("Você não possui saldo suficiente para realizar o saque.");
				System.out.println("Verifique se também possui saldo suficiente para pagar a taxa de " + TipoTaxa.SAQUE.getValorTaxa());
			}
			break;
		case 2:
			double valorDep = 0;
			System.out.print("Digite o valor: ");
			valorDep = leitor.nextDouble();
			try {
				conta.depositar(valorDep);
			} catch (ValorNegativoException e) {
				System.out.println("Não é possível depositar um valor negativo");
			} catch (ValorInsuficienteException e) {
				System.out.println("Você não possui saldo suficiente para realizar o deposito.");
				System.out.println("Verifique se também possui saldo suficiente para pagar a taxa de " + TipoTaxa.DEPOSITO.getValorTaxa());
			}
			break;
		case 3:
			double valorTransf = 0;
			String cpfDestino;
			Conta contaDest = null;

			System.out.println("Digite o cpf do titular destino: ");
			cpfDestino = leitor.nextLine();
			System.out.print("Digite o valor: ");
			valorTransf = leitor.nextDouble();

			try {
				contaDest = RepositorioContas.pesquisaContas(cpfDestino);
			} catch (CadastroInexistenteException e1) {
				System.out.println("Conta destino não existe");
			}

			try {
				conta.transferir(valorTransf, contaDest);
			} catch (ValorNegativoException e) {
				System.out.println("Não é possível depositar um valor negativo");
			} catch (ValorInsuficienteException e) {
				System.out.println("Você não possui saldo suficiente para realizar o deposito.");
				System.out.println("Verifique se também possui saldo suficiente para pagar a taxa de " + TipoTaxa.TRANSFERENCIA.getValorTaxa());
			}
			break;
		case 4:
			
			break;

		default:
			System.out.println("Opcao inválida");
			break;
		}
	}
}
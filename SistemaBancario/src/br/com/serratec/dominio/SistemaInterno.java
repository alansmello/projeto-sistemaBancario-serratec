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

		do {
			limparTela();
			if (usuario instanceof Cliente) {
				int opcao;
				
				System.out.println("------------------------------------------");
				System.out.println("Bem vindo, " + usuario.getNome());
				System.out.println("------------------------------------------");

				System.out.println("============== MENU INICIAL ==============");
				System.out.println("1 - Menu de Movimentação");
				System.out.println("2 - Mostrar saldo");
				System.out.println("3 - Relatório de tributação da conta corrente");
				System.out.println("4 - Relatório de rendimento da poupança");
				System.out.println("5 - Contratar seguro de vida");
				System.out.println("6 - Finalizar programa");
				System.out.println("==========================================\n");
				System.out.print("Digite uma das opcoes acima: ");
				opcao = leitor.nextInt();
				leitor.nextLine();

				switch (opcao) {
				case 1:
					movimentacaoMenu(leitor, conta);
					break;
				case 2:
					mostrarSaldo(conta);
					System.out.println("Pressione ENTER para continuar");
					leitor.nextLine();
					break;
				case 3:

					break;
				case 4:

					break;
				case 5:

					break;
				case 6:
					System.out.println("Programa finalizado");
					System.exit(0);
				default:

					break;
				}

			} else if (usuario instanceof Gerente) {

			} else if (usuario instanceof Diretor) {

			} else if (usuario instanceof Presidente) {

			}
		} while (true);
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
			System.out.println("Esse usuário não possui uma conta bancária registrada no sistema.");
		}

		return conta;
	}

	private static void movimentacaoMenu(Scanner leitor, Conta conta) {
		int opcao;

		do {
			limparTela();
			System.out.println("================ MOVIMENTACOES ================");
			System.out.println("1 - saque");
			System.out.println("2 - deposito");
			System.out.println("3 - transferencia");
			System.out.println("4 - voltar");
			System.out.println("===============================================");

			System.out.print("\nEscolha uma das opcoes acima: ");
			opcao = leitor.nextInt();
			leitor.nextLine();
			limparTela();

			switch (opcao) {
			case 1:
				double valorSaque = 0;
				System.out.print("Digite o valor: ");
				valorSaque = leitor.nextDouble();
				leitor.nextLine();
				
				try {
					conta.sacar(valorSaque);
				} catch (ValorNegativoException e) {
					System.out.println("Não é possível sacar um valor negativo");
				} catch (ValorInsuficienteException e) {
					System.out.println("Você não possui saldo suficiente para realizar o saque.");
					System.out.println("Verifique se também possui saldo suficiente para pagar a taxa de "
							+ TipoTaxa.SAQUE.getValorTaxa());
				}
				break;
			case 2:
				double valorDep = 0;
				System.out.print("Digite o valor: ");
				valorDep = leitor.nextDouble();
				leitor.nextLine();
				
				try {
					conta.depositar(valorDep);
				} catch (ValorNegativoException e) {
					System.out.println("Não é possível depositar um valor negativo");
				} catch (ValorInsuficienteException e) {
					System.out.println("Você não possui saldo suficiente para realizar o deposito.");
					System.out.println("Verifique se também possui saldo suficiente para pagar a taxa de "
							+ TipoTaxa.DEPOSITO.getValorTaxa());
				}
				break;
			case 3:
				double valorTransf = 0;
				String cpfDestino;
				Conta contaDest = null;

				System.out.print("Digite o cpf do titular destino: ");
				cpfDestino = leitor.nextLine();

				try {
					contaDest = RepositorioContas.pesquisaContas(cpfDestino);
				} catch (CadastroInexistenteException e1) {
					System.out.println("Conta destino não existe");
					break;
				}

				System.out.print("Digite o valor: ");
				valorTransf = leitor.nextDouble();
				leitor.nextLine();

				try {
					conta.transferir(valorTransf, contaDest);
				} catch (ValorNegativoException e) {
					System.out.println("Não é possível depositar um valor negativo");
				} catch (ValorInsuficienteException e) {
					System.out.println("Você não possui saldo suficiente para realizar o deposito.");
					System.out.println("Verifique se também possui saldo suficiente para pagar a taxa de "
							+ TipoTaxa.TRANSFERENCIA.getValorTaxa());
				}
				break;
			case 4:
				return;

			default:
				System.out.println("Opcao inválida. Tente novamente.");
				break;
			}
			
			RepositorioContas.atualizarContas();
			System.out.println("Pressione ENTER para continuar");
			leitor.nextLine();
		} while (true);
	}
	
	private static void mostrarSaldo(Conta conta) {
		limparTela();
		System.out.println("================================");
		System.out.printf("Saldo atual: R$ %.2f\n", conta.getSaldo());
		System.out.println("================================\n");
	}
	
	private static void limparTela() {
		for(int i=0; i<100; i++)
			System.out.println("");
	}
}
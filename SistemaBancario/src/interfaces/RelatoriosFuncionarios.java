package interfaces;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Stream;

import br.com.serratec.entidade.contas.Conta;
import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.repositorios.RepositorioContas;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;
import br.com.serratec.entidade.usuarios.Usuario;

public interface RelatoriosFuncionarios {

	default void criaRelatorioDiretor() {

		TreeMap<String, Usuario> lista = RepositorioUsuarios.listaAlfabetica();
		Stream<Usuario> listaUsuarios = lista.values().stream()
				.sorted(Comparator.comparing(Usuario -> Usuario.getNome()));

		LocalDateTime momentoAtual = LocalDateTime.now();
		DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		System.out.println("Relatório de clientes no banco - " + formatoBrasileiro.format(momentoAtual));
		listaUsuarios.forEach(cliente -> {
			int idAgenciaContas;

			try {
				idAgenciaContas = RepositorioContas.pesquisaContas(cliente.getCpf()).getAgencia();
				System.out.println("Nome: " + cliente.getNome() + ", CPF: " + cliente.getCpf() + ", Agencia: "
						+ ((idAgenciaContas == -1) ? "" : idAgenciaContas));
			} catch (CadastroInexistenteException e) {
				e.printStackTrace();
			}
		});
	}

	default void criaRelatorioPresidente() {
		double valorTotal = Conta.getTotalSaldo();

		LocalDateTime momentoAtual = LocalDateTime.now();
		DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		System.out.println("****Relatório Saldo Total - " + momentoAtual.format(formatoBrasileiro) + "****");

		System.out.printf("O Banco tem um Saldo total de R$ %.2f.", valorTotal);
	}
}
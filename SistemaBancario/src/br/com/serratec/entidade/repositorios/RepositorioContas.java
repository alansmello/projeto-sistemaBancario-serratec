package br.com.serratec.entidade.repositorios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import br.com.serratec.entidade.contas.Conta;
import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.excecoes.cadastroExisteException;
import br.com.serratec.entidade.contas.ContaCorrente;
import br.com.serratec.entidade.contas.ContaPoupanca;

public class RepositorioContas {

	private static HashMap<String, Conta> contasLista = new HashMap<String, Conta>();

	public static void adicionaContaLista(Conta conta) throws cadastroExisteException {

		if (contasLista.containsKey(conta.getCpfTitular())) {
			throw new cadastroExisteException();
		}
		contasLista.put(conta.getCpfTitular(), conta);

	}

	public static void contasLoader() throws IOException, cadastroExisteException {
		File arquivoContas = new File("contas.csv");

		FileReader leitorContas = null;
		leitorContas = new FileReader(arquivoContas);

		BufferedReader leitorContasBff = new BufferedReader(leitorContas);

		do {
			String contaString = leitorContasBff.readLine();
			if (contaString == null) {
				break;
			}
			String[] contasVetor = contaString.split(";");

			int numero = Integer.parseInt(contasVetor[0]);
			int agencia = Integer.parseInt(contasVetor[1]);
			//char tipoConta = (contasVetor[2].charAt(0));
			int tipoConta = Integer.parseInt(contasVetor[2]);
			String cpfTitular = contasVetor[3];
			double saldo = Double.parseDouble(contasVetor[4]);
			int aniversarioConta = Integer.parseInt(contasVetor[5]);

			if (tipoConta == 0) {
				ContaCorrente conta = new ContaCorrente(numero, agencia, cpfTitular, saldo);
				adicionaContaLista(conta);
			} else {
				ContaPoupanca conta = new ContaPoupanca(numero, agencia, cpfTitular, saldo, aniversarioConta);
				adicionaContaLista(conta);
			}
		} while (true);
		
		System.out.println(contasLista);

		leitorContas.close();
		leitorContasBff.close();

	}
	public static Conta pesquisaContas(String cpfTitular) throws CadastroInexistenteException {
		Conta contaPesquisada = contasLista.get(cpfTitular);
		if (contaPesquisada == null) {
			throw new CadastroInexistenteException();
		}
		return contaPesquisada;
	}
}

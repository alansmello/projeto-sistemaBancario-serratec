package br.com.serratec.entidade.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.serratec.entidade.contas.Conta;
import br.com.serratec.entidade.contas.ContaCorrente;
import br.com.serratec.entidade.contas.ContaPoupanca;
import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.excecoes.cadastroExisteException;

public class RepositorioContas {

	private static HashMap<String, Conta> contasLista = new HashMap<String, Conta>();

	public static void adicionaContaLista(Conta conta) throws cadastroExisteException {

		if (contasLista.containsKey(conta.getCpfTitular())) {
			throw new cadastroExisteException();
		}
		contasLista.put(conta.getCpfTitular(), conta);

	}
	
	public static Conta pesquisaContas(String cpfTitular) throws CadastroInexistenteException {
		Conta contaPesquisada = contasLista.get(cpfTitular);
		if (contaPesquisada == null) {
			throw new CadastroInexistenteException();
		}
		return contaPesquisada;
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
		
		

		leitorContas.close();
		leitorContasBff.close();

	}
	
	public static void atualizarContas() {
		File arquivo = new File("contas.csv");
		FileWriter writer;
		BufferedWriter bffWriter;
		
		
		try {
			writer = new FileWriter(arquivo);
			bffWriter = new BufferedWriter(writer);
			
			for(Conta conta : contasLista.values()) {
				writer.write(Integer.toString(conta.getNumero()) + ";");
				writer.write(Integer.toString(conta.getAgencia()) + ";");
				
				if(conta instanceof ContaCorrente) {
					writer.write("0;");
				}else {
					writer.write("1;");
				}
				
				writer.write(conta.getCpfTitular() + ";");
				writer.write(Double.toString(conta.getSaldo()) + ";");
				
				if(conta instanceof ContaCorrente) {
					writer.write("0");
				}else {
					writer.write(Integer.toString(((ContaPoupanca)conta).getAniversarioConta()));
				}
				
				writer.write("\n");
			}
			
			writer.close();
			bffWriter.close();
		} catch (IOException e) {
			System.out.println("Não foi possível atualizar o arquivo das Contas.");
			System.out.println("Verifique se possui permissão de escrita no arquivo.");
		}
	}
	
	  public static List<Conta> getContaCorrenteAgencia(int agencia) {

	        // Criando a lista que vou retornar pro meu gerente
	        List<Conta> listaFiltrada = new ArrayList<>();

	        // Percorrendo cada uma das contas do banco
	        for (Conta conta : contasLista.values()) {
	            // Pra cada conta, vou percorrer todas as agências que eu pedi na chamada da função
	            //for (String ag : agencia) {
	                // Verificar se qualquer uma dela é igual a agência da conta corrente atual em que estou
	                if (conta.getAgencia()== agencia) {
	                    listaFiltrada.add(conta);
	                }
	            }
	        
	        return listaFiltrada;
	    }
	

}

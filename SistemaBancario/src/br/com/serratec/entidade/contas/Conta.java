package br.com.serratec.entidade.contas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.com.serratec.entidade.enums.TipoTaxa;
import br.com.serratec.entidade.excecoes.ValorInsuficienteException;
import br.com.serratec.entidade.excecoes.ValorNegativoException;

/**
 * Classe abstrata responsável por representar uma conta bancária genérica.
 * Serve de base para a criação das classes ContaCorrente e ContaPoupança.
 * 
 * @see ContaCorrente
 * @see ContaPoupanca
 */

public abstract class Conta {
	protected int numero;
	protected int agencia;
	protected char tipo;
	protected String cpfTitular;
	protected double saldo;
	protected static double totalSaldo = 0; 
	
	public Conta(int numero, int agencia, String cpfTitular, double saldo) {
		this.numero = numero;
		this.agencia = agencia;
		this.cpfTitular = cpfTitular;
		this.saldo = saldo;
		
		totalSaldo += saldo;
	}
	
	public static double getTotalSaldo() {
		return totalSaldo;
	}

	public int getNumero() {
		return numero;
	}

	public int getAgencia() {
		return agencia;
	}

	public char getTipo() {
		return tipo;
	}

	public String getCpfTitular() {
		return cpfTitular;
	}

	public double getSaldo() {
		return saldo;
	}
	
	/**
	 * Realiza um deposito na Conta.
	 * 
	 * @param valor
	 * @throws ValorNegativoException
	 * @throws ValorInsuficienteException
	 */
	
	public void depositar(double valor) throws ValorNegativoException, ValorInsuficienteException {
		double taxa = TipoTaxa.DEPOSITO.getValorTaxa();
		
		if(valor < 0) {
			throw new ValorNegativoException();
		}else if(valor < taxa) {
			throw new ValorInsuficienteException();
		}
		this.saldo += valor - taxa;
		try {
			registraTransacao(valor, "deposito");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Realiza um saque na Conta.
	 * 
	 * @param valor
	 * @throws ValorNegativoException 
	 * @throws ValorInsuficienteException 
	 */
	
	public void sacar(double valor) throws ValorNegativoException, ValorInsuficienteException {
		double taxa = TipoTaxa.SAQUE.getValorTaxa();
		
		if(valor < 0) {
			throw new ValorNegativoException();
		}else if(this.saldo < valor + taxa) {
			throw new ValorInsuficienteException();
		}
		
		this.saldo -= valor + taxa;
			
		try {
			registraTransacao(valor, "saque");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Realiza uma transferência na Conta.
	 * 
	 * @param valor
	 * @param contaDestino
	 * @throws ValorNegativoException 
	 * @throws ValorInsuficienteException 
	 */
	
	public void transferir(double valor, Conta contaDestino) throws ValorNegativoException, ValorInsuficienteException {
		double taxa = TipoTaxa.TRANSFERENCIA.getValorTaxa();
		
		if(valor < 0) {
			throw new ValorNegativoException();
		}else if(this.saldo < valor + taxa) {
			throw new ValorInsuficienteException();
		}
		
		this.saldo -= valor + taxa;
		contaDestino.saldo += valor;
		
		try {
			registraTransacao(valor, "transferencia", contaDestino.cpfTitular);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Identifica o tipo de transação (saque, deposito ou transferência)
	 * e gera um arquivo com informações de todas as transações até o momento
	 * atual.
	 * 
	 * @param valor
	 * @param tipoTransacao
	 * @param cpfDestinatario
	 * @throws IOException
	 */
	
	protected void registraTransacao(double valor, String tipoTransacao, String... cpfDestinatario) throws IOException {
        File pastaMovimentacao = new File ("RepositorioBanco");
        File HistoricoMovimentacao = new File (pastaMovimentacao.getAbsolutePath() + "/historicoMovimentacoRepositorio.txt");

        if (!pastaMovimentacao.exists())
        	pastaMovimentacao.mkdirs();

        if(!HistoricoMovimentacao.exists())
        	HistoricoMovimentacao.createNewFile();
        

        try(FileWriter historicoMovimentacaoEscrever = new FileWriter(HistoricoMovimentacao, true);
            BufferedWriter historicoMovimentacaoEscreverBFF = new BufferedWriter(historicoMovimentacaoEscrever)) {

        	historicoMovimentacaoEscreverBFF.append(tipoTransacao + ";" + this.cpfTitular + ";" + valor + ";");
            if (this instanceof ContaCorrente) {
            	historicoMovimentacaoEscreverBFF.append("c");
            } else if (this instanceof ContaPoupanca) {
            	historicoMovimentacaoEscreverBFF.append("p");
            }
            if (!(cpfDestinatario.length == 0)) {
            	historicoMovimentacaoEscreverBFF.append(";" + cpfDestinatario[0]);
            }
            historicoMovimentacaoEscreverBFF.newLine();
            
            System.out.println("transacao registrada");

        } catch (IOException e) {
            System.out.println("Erro de escrita de arquivos!");
        }
    }
}

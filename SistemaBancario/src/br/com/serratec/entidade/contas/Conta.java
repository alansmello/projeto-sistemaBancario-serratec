package br.com.serratec.entidade.contas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.com.serratec.entidade.enums.TipoTaxa;
import br.com.serratec.entidade.excecoes.ValorInsuficienteException;
import br.com.serratec.entidade.excecoes.ValorNegativoException;

public abstract class Conta {
	protected int numero;
	protected int agencia;
	protected char tipo;
	protected String cpfTitular;
	protected double saldo;
	
	public Conta(int numero, int agencia, String cpfTitular, double saldo) {
		this.numero = numero;
		this.agencia = agencia;
		this.cpfTitular = cpfTitular;
		this.saldo = saldo;
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
	
	public boolean sacar(double valor) {
		double taxa = TipoTaxa.SAQUE.getValorTaxa();
		
		if(this.saldo >= valor + taxa) {
			this.saldo -= valor + taxa;
			
			try {
				registraTransacao(valor, "saque");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return true;
		}
		
		return false;
	}
	
	public boolean transferir(double valor, Conta contaDestino) {
		double taxa = TipoTaxa.TRANSFERENCIA.getValorTaxa();
		
		if(this.saldo >= valor + taxa) {
			this.saldo -= valor + taxa;
			contaDestino.saldo += valor;
			
			try {
				registraTransacao(valor, "transferencia", contaDestino.cpfTitular);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		return false;
	}
	
	protected void registraTransacao(double valor, String tipoTransacao, String... cpfDestinatario) throws IOException {
        File pastaHistoricoMovimentacao = new File ("RepositorioBanco");
        File HistoricoMovimentacao = new File (pastaHistoricoMovimentacao.getAbsolutePath() + "/historicoMovimentacoRepositorio.txt");

        if (!pastaHistoricoMovimentacao.exists()) {
        	pastaHistoricoMovimentacao.mkdirs();
        }

        if(!HistoricoMovimentacao.exists()) {
        	HistoricoMovimentacao.createNewFile();
        }

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

package br.com.serratec.entidade.contas;

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
	}
	
	public boolean sacar(double valor) {
		double taxa = TipoTaxa.SAQUE.getValorTaxa();
		
		if(this.saldo >= valor + taxa) {
			this.saldo -= valor + taxa;
			return true;
		}
		
		return false;
	}
	
	public boolean transferir(double valor, Conta contaDestino) {
		double taxa = TipoTaxa.TRANSFERENCIA.getValorTaxa();
		
		if(this.saldo >= valor + taxa) {
			this.saldo -= valor + taxa;
			contaDestino.saldo += valor;
			return true;
		}
		
		return false;
	}
}

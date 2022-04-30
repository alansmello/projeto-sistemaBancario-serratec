package br.com.serratec.entidade.contas;

public class ContaPoupanca extends Conta {
	private final char tipoConta = 'p';
	private int aniversarioConta;
	private static int qtdContas = 1;
	public static int getQtdContas() {
		return qtdContas;
	}
	public ContaPoupanca(int numero, int agencia, String cpfTitular, double saldo, int aniversarioConta) {
		super(numero, agencia, cpfTitular, saldo);
		this.aniversarioConta = aniversarioConta;
	}
	
	
}

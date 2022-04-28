package br.com.serratec.entidade.contas;


import br.com.serratec.entidade.enums.TipoTaxa;

public class ContaCorrente extends Conta {
	private final char tipoConta = 'c';
	private SegurodeVida segurodeVida;

	public ContaCorrente(int numero, int agencia, String cpfTitular, double saldo) {
		super(numero, agencia, cpfTitular, saldo);
	}
	
	public boolean ContratarSeguro (double valorSegurado) {
		
		if((valorSegurado * TipoTaxa.SEGURO.getValorTaxa())  <= super.saldo) {
			this.segurodeVida = new SegurodeVida(valorSegurado, super.cpfTitular);
			super.saldo -= valorSegurado * TipoTaxa.SEGURO.getValorTaxa();
			return true;
		}else {
			return false;
		}
		
	}
	
	
}

package br.com.serratec.entidade.enums;

public enum TipoTaxa {
	SAQUE(0.10),
	DEPOSITO(0.10),
	TRANSFERENCIA(0.20),
	RENDIMENTO(0.11),
	SEGURO(0.20);
	
	private double valorTaxa;

	private TipoTaxa(double valorTaxa) {
		this.valorTaxa = valorTaxa;
	}

	public double getValorTaxa() {
		return valorTaxa;
	}
	
	
	

}

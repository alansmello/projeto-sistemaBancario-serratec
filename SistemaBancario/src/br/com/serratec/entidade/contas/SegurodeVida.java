package br.com.serratec.entidade.contas;

import java.time.LocalDate;

public class SegurodeVida {
	private double valorSegurado;
	private LocalDate dataExpiracao;
	private String cpfSegurado;
	
	
	public SegurodeVida(double valorSegurado,String cpfSegurado) {
		this.valorSegurado = valorSegurado;
		this.cpfSegurado = cpfSegurado;
		this.dataExpiracao = LocalDate.now().plusMonths(12);
	}


	public double getValorSegurado() {
		return valorSegurado;
	}


	public LocalDate getDataExpiracao() {
		return dataExpiracao;
	}


	public String getCpfSegurado() {
		return cpfSegurado;
	}
	

}

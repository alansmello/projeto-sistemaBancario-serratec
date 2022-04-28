package br.com.serratec.entidade.excecoes;

public class ValorInsuficienteException extends Exception {
	
	@Override
	public String getMessage() {
		return ("Valor Insuficiente");
	}

}

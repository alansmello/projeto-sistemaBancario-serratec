package br.com.serratec.entidade.excecoes;

public class ValorNegativoException extends Exception {

	@Override
	public String getMessage() {
		return ("Valor Informado Negativo");
	}
	

}

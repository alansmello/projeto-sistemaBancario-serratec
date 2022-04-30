package br.com.serratec.entidade.excecoes;

public class ValorInvalidoException extends Exception {
	@Override
	public String getMessage() {
		return ("Valor Invalido. Deve ser maior que 30 dias.");
	}
}

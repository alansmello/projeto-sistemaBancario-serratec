package br.com.serratec.entidade.excecoes;

public class senhaInvalidaException extends Exception {

	@Override
	public String getMessage() {
		return "Senha inválida";
	}
	
}

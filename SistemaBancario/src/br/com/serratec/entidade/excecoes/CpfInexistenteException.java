package br.com.serratec.entidade.excecoes;

public class CpfInexistenteException extends Exception {
	@Override
	public String getMessage() {
		return ("CPF Inexistente");
	}
}

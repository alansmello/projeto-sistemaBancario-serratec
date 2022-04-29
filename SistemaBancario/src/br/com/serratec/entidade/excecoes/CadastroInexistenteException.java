package br.com.serratec.entidade.excecoes;

public class CadastroInexistenteException extends Exception {
	@Override
	public String getMessage() {
		return ("Cadastro Inexistente");
	}
}

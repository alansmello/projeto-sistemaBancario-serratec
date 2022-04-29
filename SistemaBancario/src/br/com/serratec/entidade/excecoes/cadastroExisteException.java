package br.com.serratec.entidade.excecoes;

public class cadastroExisteException extends Exception {
		
		@Override
		public String getMessage() {
			return ("Cadastro já existe");
		}

	}




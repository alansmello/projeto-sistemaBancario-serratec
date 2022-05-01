package br.com.serratec.entidade.excecoes;

public class SeguroExistenteException extends Exception {
	
		@Override
		public String getMessage() {
			return ("Seguro de Vida existente no Map.");
		}
}

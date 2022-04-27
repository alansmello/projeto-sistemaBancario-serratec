package br.com.serratec.entidade;

public class Diretor extends Funcionario {

	     protected final TipoCargo cargo = TipoCargo.DIRETOR;

         public Diretor(String cpf, String nome, String senha, TipoCargo cargo) {
			super(cpf, nome, senha, cargo);
		}
                

}

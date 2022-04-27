package br.com.serratec.entidade;

public class Presidente extends Funcionario {

        protected final TipoCargo cargo = TipoCargo.PRESIDENTE;

         public Presidente(String cpf, String nome, String senha, TipoCargo cargo) {
			super(cpf, nome, senha, cargo);
		}
                
}

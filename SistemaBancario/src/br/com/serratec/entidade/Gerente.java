package br.com.serratec.entidade;

public class Gerente extends Funcionario {
    
        protected int agencia;
        protected final TipoCargo cargo = TipoCargo.GERENTE;

         public Gerente(String cpf, String nome, String senha, TipoCargo cargo, int agencia) {
			super(cpf, nome, senha, cargo);
			this.agencia = agencia;
		}
                
         
	}

package br.com.serratec.entidade.usuarios;

import br.com.serratec.entidade.enums.TipoCargo;

public class Gerente extends Funcionario {
    
        protected int agencia;
        protected final TipoCargo cargo = TipoCargo.GERENTE;

         public Gerente(String cpf, String nome, String senha, int agencia) {
			super(cpf, nome, senha);
			this.agencia = agencia;
		}
                
         
	}

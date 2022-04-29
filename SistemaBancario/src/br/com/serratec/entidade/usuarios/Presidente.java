package br.com.serratec.entidade.usuarios;

import br.com.serratec.entidade.enums.TipoCargo;

public class Presidente extends Funcionario {

        protected final TipoCargo cargo = TipoCargo.PRESIDENTE;

         public Presidente(String cpf, String nome, String senha) {
			super(cpf, nome, senha);
		}
                
}

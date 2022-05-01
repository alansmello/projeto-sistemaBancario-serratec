package br.com.serratec.entidade.usuarios;

import br.com.serratec.entidade.enums.TipoCargo;
import br.com.serratec.entidade.interfaces.RelatorioDiretorPresidente;

public class Diretor extends Funcionario implements RelatorioDiretorPresidente {

	     protected final TipoCargo cargo = TipoCargo.DIRETOR;

         public Diretor(String cpf, String nome, String senha) {
			super(cpf, nome, senha);
		}
                

}

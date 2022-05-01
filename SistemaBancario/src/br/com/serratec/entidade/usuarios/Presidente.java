package br.com.serratec.entidade.usuarios;

import br.com.serratec.entidade.enums.TipoCargo;
import interfaces.RelatoriosFuncionarios;

public class Presidente extends Funcionario implements RelatoriosFuncionarios{

        protected final TipoCargo cargo = TipoCargo.PRESIDENTE;

         public Presidente(String cpf, String nome, String senha) {
			super(cpf, nome, senha);
		}
}

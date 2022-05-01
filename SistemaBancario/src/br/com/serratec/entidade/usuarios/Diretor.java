package br.com.serratec.entidade.usuarios;

import br.com.serratec.entidade.enums.TipoCargo;
import interfaces.RelatoriosFuncionarios;

public class Diretor extends Funcionario implements RelatoriosFuncionarios{

	protected final TipoCargo cargo = TipoCargo.DIRETOR;

	public Diretor(String cpf, String nome, String senha) {
		super(cpf, nome, senha);
	}
}

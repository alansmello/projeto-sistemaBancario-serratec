package br.com.serratec.entidade.usuarios;

import br.com.serratec.entidade.enums.TipoCargo;

// A formatação desse arquivo não ficou muito legal. É bom rodar um ctrl+shift+f
public abstract class Funcionario extends Usuario {
    public Funcionario(String cpf, String nome, String senha) {
        super(cpf, nome, senha);
    }
}

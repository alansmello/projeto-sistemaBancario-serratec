package br.com.serratec.entidade.usuarios;

import br.com.serratec.entidade.excecoes.senhaInvalidaException;

public abstract class Usuario implements Comparable<Usuario>{

	protected String cpf;
	protected String nome;
	protected String senha;

	public Usuario(String cpf, String nome, String senha) {
		this.cpf = cpf;
		this.nome = nome;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getSenha() {
		return senha;
	}

  public void autentica(String senha) throws senhaInvalidaException {
		
		if(!this.senha.equals(senha)) {
			throw new senhaInvalidaException();
		}
	}

	@Override
	public int compareTo(Usuario o) {
		// TODO Auto-generated method stub
		return this.nome.compareTo(o.nome);
	}
}

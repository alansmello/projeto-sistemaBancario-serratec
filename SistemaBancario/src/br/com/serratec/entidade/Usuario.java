package br.com.serratec.entidade;

public abstract class Usuario {

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
	
	 public boolean autentica(String senha) {
	        if(this.senha.equals(senha) ) {
	            System.out.println("Acesso permitido !");
	            return true;
	        } else {
	            System.out.println("Acesso não permitido !");
	            return false;
	        }

	 	}
}

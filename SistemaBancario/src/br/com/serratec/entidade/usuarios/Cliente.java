package br.com.serratec.entidade.usuarios;

public class Cliente extends Usuario{

	public Cliente(String cpf, String nome, String senha) {
		super(cpf, nome, senha);
		
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", senha=" + senha + "]";
	}
	
	
   
}

package br.com.serratec.entidade;

public abstract class Funcionario extends Usuario {
	
	protected TipoCargo cargo;
	
        public Funcionario(String cpf, String nome, String senha, TipoCargo cargo) {
		super(cpf, nome, senha);
		this.cargo = cargo;
	
	}

		public TipoCargo getCargo() {
			return cargo;
		}

		public void setCargo(TipoCargo cargo) {
			this.cargo = cargo;
		}

	

    }

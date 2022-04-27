package banco.pessoa;

import banco.pessoa.Funcionario;

public class FuncionarioAutenticavel extends Funcionario {

    int senha;

    public FuncionarioAutenticavel(String nome) {
        super(nome);
        // TODO Auto-generated constructor stub
    }

    public boolean autentica(int senha) {
        if(this.senha == senha) {
            System.out.println("Acesso permitido !");
            return true;
        } else {
            System.out.println("Acesso não permitido !");
            return false;
        }
    }

}

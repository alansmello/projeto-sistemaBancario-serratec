package br.com.serratec.entidade;

public class Presidente extends Funcionario {

    import banco.util.Autenticavel;

    public class Presidente extends FuncionarioAutenticavel implements Autenticavel {

        private int senha;

        public Presidente(String nome) {
            super(nome);
        }

        @Override
        public int getSenha() {
            return senha;
        }

        @Override
        public void setSenha(int senha) {
            this.senha = senha;
        }

        public boolean autentica(int senha) {
            if (this.senha == senha) {
                System.out.println("Acesso permitido !");
                return true;
            } else {
                System.out.println("Acesso negado !");
                return false;
            }
        }

}

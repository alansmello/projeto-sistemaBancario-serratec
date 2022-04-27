package br.com.serratec.entidade;

public class Diretor extends Funcionario {

    import banco.util.Autenticavel;

    public class Diretor extends FuncionarioAutenticavel implements Autenticavel {

        private int senha;

        public Diretor(String nome) {

            super(nome);
        }

        public int getSenha() {

            return senha;
        }

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

package br.com.serratec.entidade;

public class Gerente extends Funcionario {
    import banco.util.Autenticavel;

    public class Gerente extends FuncionarioAutenticavel implements Autenticavel {

        int senha;
        int numeroDeFuncionariosGerenciados;
        int idAgencia;

        public int getIdAgencia() {

            return idAgencia;
        }

        public void setIdAgencia(int idAgencia) {

            this.idAgencia = idAgencia;
        }

        public Gerente(String nome) {

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
                System.out.println("Acesso permitido!");
                return true;
            } else {
                System.out.println("Acesso negado!");
                return false;
            }
        }
}

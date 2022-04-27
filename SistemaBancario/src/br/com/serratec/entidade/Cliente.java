package br.com.serratec.entidade;
public class Cliente {

import banco.util.Autenticavel;

    public class Cliente implements Autenticavel {
        public String nome;
        public String sobreNome;
        public int senha;
        public int cpf;

        public Cliente() {

        }

        public Cliente (String nome, int senha, int cpf) {
            this.nome = nome;
            this.senha = senha;
            this.cpf = cpf;
        }

        @Overrideboolean
        public  autentica(int senha) {
            if(this.senha == senha) {
                System.out.println("Acesso permitido !");
                return true;
            } else {
                System.out.println("Acesso negado !");
                return false;
            }
        }

        public String getNome() {

            return nome;
        }
        public void setNome(String nome) {

            this.nome = nome;
        }

        public int getSenha() {

            return senha;
        }

        public void setSenha(int senha) {

            this.senha = senha;
        }

        public int getCpf() {

            return cpf;
        }

        public void setCpf(int cpf) {

            this.cpf = cpf;
        }
}

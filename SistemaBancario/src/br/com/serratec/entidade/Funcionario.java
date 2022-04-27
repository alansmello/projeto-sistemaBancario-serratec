package br.com.serratec.entidade;

public class Funcionario {
    public abstract class Funcionario extends Cliente {
        protected double salario;

        public void setSalario(double salario) {

            this.salario = salario;
        }

        public Funcionario(String nome) {

            this.nome = nome;
        }

        public String getNome() {

            return nome;
        }

        public void setNome(String nome) {

            this.nome = nome;
        }

        public int getCpf() {

            return cpf;
        }

        public void setCpf(int cpf) {

            this.cpf = cpf;
        }

        public double getSalario() {

            return salario;
        }


    }

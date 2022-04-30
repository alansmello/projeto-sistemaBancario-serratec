package br.com.serratec.entidade.usuarios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.serratec.entidade.contas.Conta;
import br.com.serratec.entidade.enums.TipoCargo;

public class Presidente extends Funcionario {

        protected final TipoCargo cargo = TipoCargo.PRESIDENTE;

         public Presidente(String cpf, String nome, String senha) {
			super(cpf, nome, senha);
		}
         
         public void criaRelatorioPresidente() {
 			
 			double valorTotal = Conta.getTotalSaldo();
 			
 			 LocalDateTime momentoAtual = LocalDateTime.now();
 		     DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
 		     
 		     
 			System.out.println("****Relatório Saldo Total - " + momentoAtual.format(formatoBrasileiro) + "****");
 			
 			System.out.printf("O Banco tem um Saldo total de R$ %.2f." , valorTotal );
 			
 		}
                
}

package br.com.serratec.entidade.usuarios;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.serratec.entidade.enums.TipoCargo;
import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.repositorios.RepositorioContas;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;

public class Gerente extends Funcionario {
    
        protected int agencia;
        protected final TipoCargo cargo = TipoCargo.GERENTE;

         public Gerente(String cpf, String nome, String senha, int agencia) {
			super(cpf, nome, senha);
			this.agencia = agencia;
		}

		public int getAgencia() {
			return agencia;
		}
         
		public  void criaRelatorioGerente(String cpf) throws IOException {
		        
				Usuario gerente = null;
					try {
					gerente = RepositorioUsuarios.pesquisaUsuario(cpf);
					} catch (CadastroInexistenteException e) {
					
					e.printStackTrace();
					}
				
					int agencia = ((Gerente) gerente).getAgencia();
				
					int qtdContas = RepositorioContas.getContaCorrenteAgencia(agencia).size();
				
		        
					LocalDateTime momentoAtual = LocalDateTime.now();
					DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		       
		        
		        
					System.out.println("****Relatório  de Contas - " + momentoAtual.format(formatoBrasileiro)+"****\n");
		        
					System.out.println("Existe um total de  "  + qtdContas + " contas na agencia ("  + agencia + ") de " + gerente.getNome()+".");      


			}
                
         
	}

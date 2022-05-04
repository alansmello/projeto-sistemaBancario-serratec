package br.com.serratec.entidade.contas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.serratec.entidade.enums.TipoTaxa;
import br.com.serratec.entidade.excecoes.CpfInexistenteException;
import br.com.serratec.entidade.excecoes.ValorInvalidoException;
import br.com.serratec.entidade.excecoes.ValorNegativoException;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;

public class ContaPoupanca extends Conta {
    // Esse atributo poderia ser estático já que todas as contas poupança vão ter tipo 'p'
    // Poderia ser também um enum para limitar melhor as opções
	private final char tipoConta = 'p';
	private int aniversarioConta;
	private static int qtdContas = 1;
	public static int getQtdContas() {
		return qtdContas;
	}
	public ContaPoupanca(int numero, int agencia, String cpfTitular, double saldo, int aniversarioConta) {
		super(numero, agencia, cpfTitular, saldo);
		this.aniversarioConta = aniversarioConta;
	}
	
	public int getAniversarioConta() {
		return aniversarioConta;
	}
	
	public void simuladorPoupanca(double valor, int qtdDias) throws ValorNegativoException, ValorInvalidoException, IOException {
        if (valor <= 0) {
            throw new ValorNegativoException();
        }
        if (qtdDias < 30) {
            throw new ValorInvalidoException();
        }
        int qtdMeses = qtdDias / 30;
        double valorFinal = valor * Math.pow((1 + TipoTaxa.RENDIMENTO.getValorTaxa()), qtdMeses);
        System.out.printf("Simulação: R$ %.2f por %d dias(%d meses) renderá o valor = R$ %.2f.\n", valor, qtdDias, qtdMeses, valorFinal);


        LocalDateTime hoje = LocalDateTime.now();
        DateTimeFormatter brasilForma = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter bdForma = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

        File pastaSimulaPoupanca = new File ("RepositorioBanco/Relatorios/Simulacoes/");
        File SimulaPoupanca = new File(pastaSimulaPoupanca.getAbsolutePath() + "/" + this.cpfTitular + " " + bdForma.format(hoje) + ".txt");

        if (!pastaSimulaPoupanca.exists()) {
        	pastaSimulaPoupanca.mkdirs();
        }

        if (!SimulaPoupanca.exists()) {
        	SimulaPoupanca.createNewFile();
        }

        try (FileWriter SimulaPoupancaEscrita = new FileWriter(SimulaPoupanca);
             BufferedWriter SimulaPoupancaEscritaBuffer = new BufferedWriter(SimulaPoupancaEscrita)) {

        	SimulaPoupancaEscritaBuffer.append("Relatório de simulação de rendimento conta poupança - " + brasilForma.format(hoje));
        	SimulaPoupancaEscritaBuffer.newLine();
        	SimulaPoupancaEscritaBuffer.append("Nome: " + RepositorioUsuarios.getUsuario(this.cpfTitular).getNome() + " / CPF: " + this.cpfTitular);
        	SimulaPoupancaEscritaBuffer.newLine();
        	SimulaPoupancaEscritaBuffer.newLine();
        	SimulaPoupancaEscritaBuffer.append("O rendimento do depósito de R$ " + String.format("%.2f", valor) + " por " + qtdDias + " dias(" + qtdMeses + " meses) foi R$ " + String.format("%.2f", valorFinal) + ".\n");
        } catch (IOException | CpfInexistenteException e) {
            System.out.println("Erro de escrita de arquivos");
        }
    }
	
}

package br.com.serratec.entidade.contas;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.serratec.entidade.enums.TipoTaxa;
import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;

public class ContaCorrente extends Conta {
	private final char tipoConta = 'c';
	private SegurodeVida segurodeVida;

	public ContaCorrente(int numero, int agencia, String cpfTitular, double saldo) {
		super(numero, agencia, cpfTitular, saldo);
	}
	
	public boolean ContratarSeguro (double valorSegurado) {
		
		if((valorSegurado * TipoTaxa.SEGURO.getValorTaxa())  <= super.saldo) {
			this.segurodeVida = new SegurodeVida(valorSegurado, super.cpfTitular);
			super.saldo -= valorSegurado * TipoTaxa.SEGURO.getValorTaxa();
			return true;
		}else {
			return false;
		}
		
	}
	
	 public void RelatorioTributos() throws IOException, CadastroInexistenteException {
		 
	        LocalDateTime hora = LocalDateTime.now();
	        DateTimeFormatter brasilForma = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	        DateTimeFormatter bdForma = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

	        File pastahitoricoMovimentacao = new File("RepositorioBanco");
	        File historicoMovimentacao = new File(pastahitoricoMovimentacao.getAbsolutePath() + "/historicoMovimentacoRepositorio.txt");

	        if (!pastahitoricoMovimentacao.exists()) {
	        	pastahitoricoMovimentacao.mkdirs();
	        }

	        if (!historicoMovimentacao.exists()) {
	        	historicoMovimentacao.createNewFile();
	        }

	        double totalTaxasSaque = 0;
	        double totalTaxasDeposito = 0;
	        double totalTaxasTransferencia = 0;

	        try (FileReader historicoMovimentacaoLeitura = new FileReader(historicoMovimentacao);
	             BufferedReader historicoMovimentacaoLeituraBuff = new BufferedReader(historicoMovimentacaoLeitura)) {

	            String linha;
	            while (((linha = historicoMovimentacaoLeituraBuff.readLine()) != null)) {
	                String[] itens = linha.split(";");
	                if (itens[1].equals(this.cpfTitular) && itens[3].equals("c")) {
	                    switch (itens[0]) {
	                        case "saque":
	                            totalTaxasSaque += TipoTaxa.SAQUE.getValorTaxa();
	                            break;
	                        case "deposito":
	                            totalTaxasDeposito += TipoTaxa.DEPOSITO.getValorTaxa();
	                            break;
	                        case "transferencia":
	                            totalTaxasTransferencia += TipoTaxa.TRANSFERENCIA.getValorTaxa();
	                            break;
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Erro de leitura de arquivos");
	        }

	        File pastaRelatorioContaCorrenteTributos = new File("C:\\RepositorioBanco\\Relatorios\\Clientes\\");
	        File relatorioContaCorrenteTributos = new File(pastaRelatorioContaCorrenteTributos.getAbsolutePath() + "/" + this.cpfTitular + " " + bdForma.format(hora) + ".txt");

	        if (!pastaRelatorioContaCorrenteTributos.exists()) {
	        	pastaRelatorioContaCorrenteTributos.mkdirs();
	        }

	        if (!relatorioContaCorrenteTributos.exists()) {
	        	relatorioContaCorrenteTributos.createNewFile();
	        }

	        try (FileWriter relatorioContaCorrenteTributosEscrita = new FileWriter(relatorioContaCorrenteTributos);
	             BufferedWriter relatorioContaCorrenteTributosEscritaBuffer = new BufferedWriter(relatorioContaCorrenteTributosEscrita)) {

	        	relatorioContaCorrenteTributosEscritaBuffer.append("Relatório Conta Corrente - Tributos - " + brasilForma.format(hora));
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("Nome: " + RepositorioUsuarios.pesquisaUsuario(this.cpfTitular).getNome()+ "/ CPF: " + this.cpfTitular);
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("Total gasto com taxas de saque: R$ " + String.format("%.2f", totalTaxasSaque));
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("Total gasto com taxas de depósito: R$ " + String.format("%.2f", totalTaxasDeposito));
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("Total gasto com taxas de transferência: R$ " + String.format("%.2f", totalTaxasTransferencia));
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("--------//--------");
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
/*
	            try {
	                SegurodeVida seguroVidaAtual = SeguroVidaRepositorio.getSeguroVida(this.cpfTitular);
	                relatorioTributacaoContaCorrenteWriterBuff.append("Valor pago até o momento do seguro de vida: R$ " + String.format("%.2f", seguroVidaAtual.getValorPago()));
	                relatorioTributacaoContaCorrenteWriterBuff.newLine();
	                relatorioTributacaoContaCorrenteWriterBuff.append("Mensalidade do seguro de vida: R$ " + String.format("%.2f", seguroVidaAtual.calculaMensalidade()));
	                relatorioTributacaoContaCorrenteWriterBuff.newLine();
	                relatorioTributacaoContaCorrenteWriterBuff.append("Valor contratado do seguro de vida: R$ " + String.format("%.2f", seguroVidaAtual.getValorSegurado()));
	                relatorioTributacaoContaCorrenteWriterBuff.newLine();
	                relatorioTributacaoContaCorrenteWriterBuff.append("--------//--------");
	                relatorioTributacaoContaCorrenteWriterBuff.newLine();
	            } catch (CpfInexistenteException e) {

	            }*/

	        	relatorioContaCorrenteTributosEscritaBuffer.append("Taxas:");
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("Taxa para saque: R$ " + String.format("%.2f", TipoTaxa.SAQUE.getValorTaxa()));
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("Taxa para depósito: R$ " + String.format("%.2f", TipoTaxa.DEPOSITO.getValorTaxa()));
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();
	        	relatorioContaCorrenteTributosEscritaBuffer.append("Taxa para transferência: R$ " + String.format("%.2f", TipoTaxa.TRANSFERENCIA));
	        	relatorioContaCorrenteTributosEscritaBuffer.newLine();

	        } catch (IOException  e) {
	            System.out.println("Erro de escrita de arquivos");
	        }

	    }

	
	
}

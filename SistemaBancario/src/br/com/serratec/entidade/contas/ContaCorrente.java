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
import br.com.serratec.entidade.excecoes.CpfInexistenteException;
import br.com.serratec.entidade.excecoes.SeguroExistenteException;
import br.com.serratec.entidade.excecoes.ValorInsuficienteException;
import br.com.serratec.entidade.excecoes.ValorNegativoException;
import br.com.serratec.entidade.repositorios.RepositorioSeguroVida;
import br.com.serratec.entidade.repositorios.RepositorioUsuarios;

public class ContaCorrente extends Conta {
	private final char tipoConta = 'c';

	public ContaCorrente(int numero, int agencia, String cpfTitular, double saldo) {
		super(numero, agencia, cpfTitular, saldo);
	}

	public void contratarSeguro(double valorSegurado) throws ValorInsuficienteException, ValorNegativoException {

		if (this.saldo < valorSegurado * TipoTaxa.SEGURO.getValorTaxa()) {
			throw new ValorInsuficienteException();
		}
		
		if (valorSegurado < 0) {
			throw new ValorNegativoException();
		}
		
		SegurodeVida segurodeVida = new SegurodeVida(valorSegurado, super.cpfTitular);
		super.saldo -= valorSegurado * TipoTaxa.SEGURO.getValorTaxa();
		try {
			registraTransacao(valorSegurado, "segurovida");
			RepositorioSeguroVida.adicionaSeguroVida(segurodeVida);
		} catch (IOException e) {
			System.out.println("Erro ao acessar o arquivo. Verifique as permissões");
		} catch (SeguroExistenteException e) {
			System.out.println("seguro de vida já cotratado");
		}
	}

	public void relatorioTributos() throws IOException, CpfInexistenteException {

		LocalDateTime hoje = LocalDateTime.now();
		DateTimeFormatter brasilForma = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		DateTimeFormatter bdForma = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

		File pastahitoricoMovimentacao = new File("RepositorioBanco");
		File historicoMovimentacao = new File(
				pastahitoricoMovimentacao.getAbsolutePath() + "/historicoMovimentacoRepositorio.txt");

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

		File pastaRelatorioContaCorrenteTributos = new File("RepositorioBanco/Relatorios/Clientes/");
		File relatorioContaCorrenteTributos = new File(pastaRelatorioContaCorrenteTributos.getAbsolutePath() + "/"
				+ this.cpfTitular + " " + bdForma.format(hoje) + ".txt");

		if (!pastaRelatorioContaCorrenteTributos.exists()) {
			pastaRelatorioContaCorrenteTributos.mkdirs();
		}

		if (!relatorioContaCorrenteTributos.exists()) {
			relatorioContaCorrenteTributos.createNewFile();
		}

		try (FileWriter relatorioContaCorrenteTributosEscrita = new FileWriter(relatorioContaCorrenteTributos);
				BufferedWriter relatorioContaCorrenteTributosEscritaBuffer = new BufferedWriter(
						relatorioContaCorrenteTributosEscrita)) {

			relatorioContaCorrenteTributosEscritaBuffer
					.append("Relatório Conta Corrente - Tributos - " + brasilForma.format(hoje));
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer.append(
					"Nome: " + RepositorioUsuarios.getUsuario(this.cpfTitular).getNome() + "/ CPF: " + this.cpfTitular);
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer.append("Total taxas:");
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer
					.append("Taxas saque: R$ " + String.format("%.2f", totalTaxasSaque));
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer
					.append("Taxas depósito: R$ " + String.format("%.2f", totalTaxasDeposito));
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer
					.append("Taxas transferência: R$ " + String.format("%.2f", totalTaxasTransferencia));
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer.append("--------//--------");
			relatorioContaCorrenteTributosEscritaBuffer.newLine();

			try {
				SegurodeVida seguroVidaAtual = RepositorioSeguroVida.getSeguroVida(this.cpfTitular);
				relatorioContaCorrenteTributosEscritaBuffer.append("SEGURO DE VIDA");
				relatorioContaCorrenteTributosEscritaBuffer.newLine();
				relatorioContaCorrenteTributosEscritaBuffer
						.append("Valor contratado: R$ " + String.format("%.2f", seguroVidaAtual.getValorSegurado()));
				relatorioContaCorrenteTributosEscritaBuffer.newLine();
				relatorioContaCorrenteTributosEscritaBuffer
						.append("Data Expiração: R$ " + seguroVidaAtual.getDataExpiracao());
				relatorioContaCorrenteTributosEscritaBuffer.newLine();
				relatorioContaCorrenteTributosEscritaBuffer.append("Taxa de Seguro de Vida: R$ "
						+ String.format("%.2f", seguroVidaAtual.getValorSegurado() * TipoTaxa.SEGURO.getValorTaxa()));
				relatorioContaCorrenteTributosEscritaBuffer.newLine();
				relatorioContaCorrenteTributosEscritaBuffer.append("--------//--------");
				relatorioContaCorrenteTributosEscritaBuffer.newLine();
			} catch (CpfInexistenteException e) {

			}

			relatorioContaCorrenteTributosEscritaBuffer.append("Taxas:");
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer
					.append("Taxa para saque: R$ " + String.format("%.2f", TipoTaxa.SAQUE.getValorTaxa()));
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer
					.append("Taxa para depósito: R$ " + String.format("%.2f", TipoTaxa.DEPOSITO.getValorTaxa()));
			relatorioContaCorrenteTributosEscritaBuffer.newLine();
			relatorioContaCorrenteTributosEscritaBuffer.append(
					"Taxa para transferência: R$ " + String.format("%.2f", TipoTaxa.TRANSFERENCIA.getValorTaxa()));
			relatorioContaCorrenteTributosEscritaBuffer.newLine();

		} catch (IOException e) {
			System.out.println("Erro de escrita de arquivos");
		}

	}

}

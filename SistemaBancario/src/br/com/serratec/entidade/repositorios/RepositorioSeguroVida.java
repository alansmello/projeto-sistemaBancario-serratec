package br.com.serratec.entidade.repositorios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import br.com.serratec.entidade.contas.SegurodeVida;
import br.com.serratec.entidade.excecoes.CpfInexistenteException;
import br.com.serratec.entidade.excecoes.SeguroExistenteException;

public class RepositorioSeguroVida {
	private static HashMap<String, SegurodeVida> listaSeguro = new HashMap<>();
	
	public static void adicionaSeguroVida(SegurodeVida seguro) throws SeguroExistenteException, IOException {
        
		if(listaSeguro.containsKey(seguro.getCpfSegurado())) {
            throw new SeguroExistenteException();
        }
		listaSeguro.put(seguro.getCpfSegurado(), seguro);

        File PastaSeguroVida = new File ("RepositorioBanco");
        File seguroVida = new File(PastaSeguroVida.getAbsolutePath() + "/seguroVidaRepositorio.txt");

        if(!PastaSeguroVida.exists()) {
        	PastaSeguroVida.mkdirs();
        }

        if(!seguroVida.exists()) {
        	seguroVida.createNewFile();
        }

        try (FileWriter seguraVidaEscrita = new FileWriter(seguroVida, true);
             BufferedWriter seguraVidaEscritaBuffer = new BufferedWriter(seguraVidaEscrita)) {

        	seguraVidaEscritaBuffer.append(seguro.getCpfSegurado() + ";" +
                    seguro.getValorSegurado() + ";" +
                    seguro.getDataExpiracao());

        	seguraVidaEscritaBuffer.newLine();

        } catch (IOException e) {
            System.out.println("Erro de escrita de arquivos!");
        }
    }
	
	public static SegurodeVida getSeguroVida(String cpf) throws CpfInexistenteException {
        if(!listaSeguro.containsKey(cpf)) {
            throw new CpfInexistenteException();
        }
        return listaSeguro.get(cpf);
    }


}

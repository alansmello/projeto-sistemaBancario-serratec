package br.com.serratec.entidade.repositorios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import br.com.serratec.entidade.excecoes.CadastroInexistenteException;
import br.com.serratec.entidade.excecoes.CpfInexistenteException;
import br.com.serratec.entidade.excecoes.cadastroExisteException;
import br.com.serratec.entidade.usuarios.Cliente;
import br.com.serratec.entidade.usuarios.Diretor;
import br.com.serratec.entidade.usuarios.Gerente;
import br.com.serratec.entidade.usuarios.Presidente;
import br.com.serratec.entidade.usuarios.Usuario;

public class RepositorioUsuarios {

	private static HashMap<String, Usuario> usuarioLista = new HashMap<String, Usuario>();
	private static TreeMap<String, Usuario> clienteLista = new TreeMap<String, Usuario>();

	public static void adicionaUsuarioLista(Usuario usuario) throws cadastroExisteException {

		if (usuarioLista.containsKey(usuario.getCpf())) {
			throw new cadastroExisteException();
		}

		usuarioLista.put(usuario.getCpf(), usuario);

		if (usuario instanceof Cliente) {
			clienteLista.put(usuario.getCpf(), usuario);
		}

	}

	public static void usuarioLoader() throws IOException, cadastroExisteException {
		File arquivoUsuarios = new File("usuarios.csv");

		FileReader leitorUsuario = null;
		leitorUsuario = new FileReader(arquivoUsuarios);

		BufferedReader leitorUsuarioBff = new BufferedReader(leitorUsuario);

		do {
			String usuarioString = leitorUsuarioBff.readLine();
			if (usuarioString == null) {
				break;
			}
			String[] usuarioVetor = usuarioString.split(";");

			String cargo = usuarioVetor[0];
			String cpf = usuarioVetor[1];
			String nome = usuarioVetor[2];
			String senha = usuarioVetor[3];
			int agencia = Integer.parseInt(usuarioVetor[4]);

			if (cargo.equals("cliente")) {
				Cliente cliente = new Cliente(cpf, nome, senha);
				adicionaUsuarioLista(cliente);
			} else if (cargo.equals("gerente")) {
				Gerente gerente = new Gerente(cpf, nome, senha, agencia);
				adicionaUsuarioLista(gerente);
			} else if (cargo.equals("diretor")) {
				Diretor diretor = new Diretor(cpf, nome, senha);
				adicionaUsuarioLista(diretor);
			} else if (cargo.equals("presidente")) {
				Presidente presidente = new Presidente(cpf, nome, senha);
				adicionaUsuarioLista(presidente);
			}

		} while (true);

		leitorUsuario.close();
		leitorUsuarioBff.close();

	}

	public static List<Usuario> getUsuarios() {
		return (List<Usuario>) usuarioLista.values();
	}

	public static Usuario pesquisaUsuario(String cpf) throws CadastroInexistenteException {
		Usuario usuarioPesquisado = usuarioLista.get(cpf);
		if (usuarioPesquisado == null) {
			throw new CadastroInexistenteException();
		}
		return usuarioPesquisado;
	}

	// Bem legal, pessoal. Uma abordagem diferente do padrão
	public static TreeMap<String, Usuario> listaAlfabetica() {
		return clienteLista;
	}

	public static Usuario getUsuario(String cpfExt) throws CpfInexistenteException {
		if (!usuarioLista.containsKey(cpfExt)) {
			throw new CpfInexistenteException();
		}
		return usuarioLista.get(cpfExt);
	}
}
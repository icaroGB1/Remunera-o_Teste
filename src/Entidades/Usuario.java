package Entidades;

import enums.Cargo;
import exceptions.EmailInvalidoException;
import exceptions.NomeIncompletoException;
import exceptions.SenhaInvalidaException;

public class Usuario {
	private int id;
	private String nome;
	private String email;
	private String senha;
	private Cargo cargo;

	public Usuario() {
	}

	public Usuario(String nome, String email, String senha, Cargo cargo) throws Exception{
		if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cargo == null) {
	        throw new Exception("Campos vazios");
	    }
		this.nome = nome;
		if (nome.length() < 10) {
			throw new NomeIncompletoException("Nome Incopleto");
		}
		this.email = email;
		if (!email.contains("@")) {
			throw new EmailInvalidoException("Email invalido " + email);
		}
		this.senha = senha;
		if (senha.length() < 6) {
			throw new SenhaInvalidaException("Senha muito curta");
		}
		this.cargo = cargo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + "]";
	}

}

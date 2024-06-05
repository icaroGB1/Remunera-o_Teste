package Entidades;

import exceptions.EmailInvalidoException;

public class Usuario {
	private int id;
	private String name;
	private String email;
	private String senha;
	
	public Usuario() {
	}
	public Usuario(String name, String email, String senha) throws Exception {
		this.name = name;
		this.email = email;
		if(!email.contains("@")) {
			throw new EmailInvalidoException ("Email invalido " + email);
		}
		this.senha  = senha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}

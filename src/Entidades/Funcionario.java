package Entidades;

import java.math.BigDecimal;

import exceptions.SalarioInvalidoException;

public class Funcionario {
	private int id;
	private int userId;
	private String nome;
	private String cargo;
	private BigDecimal salario;

	public Funcionario() {
	}

	public Funcionario(int id, int userId, String nome, String cargo, BigDecimal salario) throws Exception {
		this.id = id;
		this.userId = userId;
		this.nome = nome;
		this.cargo = cargo;
		this.salario = salario;
		if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SalarioInvalidoException("O salário do funcionário deve ser um valor positivo");
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

}

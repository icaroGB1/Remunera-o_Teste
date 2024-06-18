package Entidades;

import java.math.BigDecimal;

import enums.CategoriaProduto;
import exceptions.PrecoInvalidoException;

public class Produto {
	private int id;
	private String nome;
	private String descricao;
	private CategoriaProduto categoria;
	private BigDecimal preco;

	public Produto() {
	}

	public Produto(String nome, String descricao, CategoriaProduto categoria, BigDecimal preco) throws Exception {
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.preco = preco;
		if (preco.compareTo(BigDecimal.ZERO) <= 0) {
			throw new PrecoInvalidoException("O preço do produto deve ser maior que zero.");
		}
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

	public CategoriaProduto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

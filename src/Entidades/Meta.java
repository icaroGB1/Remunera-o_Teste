package Entidades;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Meta {
	private int id;
	private String descricao;
	private BigDecimal valorMeta;
	private BigDecimal valorAtingido;
	private Produto produto;
	private int funcionarioId;

	public Meta() {
	}

	public Meta(int id, String descricao, BigDecimal valorMeta, BigDecimal valorAtingido, Produto produto,
			int funcionarioId) {
		this.id = id;
		this.descricao = descricao;
		this.valorMeta = valorMeta;
		this.valorAtingido = valorAtingido;
		this.produto = produto;
		this.funcionarioId = funcionarioId;
	}

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorMeta() {
		return valorMeta;
	}

	public void setValorMeta(BigDecimal valorMeta) {
		this.valorMeta = valorMeta;
	}

	public BigDecimal getValorAtingido() {
		return valorAtingido;
	}

	public void setValorAtingido(BigDecimal valorAtingido) {
		this.valorAtingido = valorAtingido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(int funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public BigDecimal calcularPorcentagemAtingida() {
		if (valorMeta.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		} else {

			BigDecimal porcentagem = valorAtingido.divide(valorMeta, 4, RoundingMode.HALF_UP)
					.multiply(new BigDecimal("100"));
			return porcentagem.setScale(2, RoundingMode.HALF_UP);
		}

	}

}

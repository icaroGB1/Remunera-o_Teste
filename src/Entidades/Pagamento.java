package Entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {
	private int id;
	private int funcionarioId;
	private BigDecimal salarioBase;
	private BigDecimal valorAdicional;
	private BigDecimal salarioTotal;
	private LocalDate dataPagamento;

	public Pagamento() {
	}

	public Pagamento(int funcionarioId, BigDecimal salarioBase, BigDecimal valorAdicional, BigDecimal salarioTotal,
			LocalDate dataPagamento) {
		this.funcionarioId = funcionarioId;
		this.salarioBase = salarioBase;
		this.valorAdicional = valorAdicional;
		this.salarioTotal = salarioTotal;
		this.dataPagamento = dataPagamento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(int funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public BigDecimal getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(BigDecimal salarioBase) {
		this.salarioBase = salarioBase;
	}

	public BigDecimal getValorAdicional() {
		return valorAdicional;
	}

	public void setValorAdicional(BigDecimal valorAdicional) {
		this.valorAdicional = valorAdicional;
	}

	public BigDecimal getSalarioTotal() {
		return salarioTotal;
	}

	public void setSalarioTotal(BigDecimal salarioTotal) {
		this.salarioTotal = salarioTotal;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}

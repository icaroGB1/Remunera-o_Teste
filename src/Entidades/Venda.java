package Entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Venda {
	private int id;
	private LocalDate datae;
	private int idFuncionario;
	private BigDecimal total;

	public Venda() {

	}

	public Venda(int id, LocalDate datae, int idFuncionario, BigDecimal total) {
		this.id = id;
		this.datae = datae;
		this.idFuncionario = idFuncionario;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDatae() {
		return datae;
	}

	public void setDatae(LocalDate datae) {
		this.datae = datae;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}

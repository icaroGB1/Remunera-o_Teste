package Entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venda {
	private int id;
	private LocalDate data;
	private int idFuncionario;
	private BigDecimal total;
	private List<ItemVenda> itens;

	public Venda() {

	}

	public Venda(int idFuncionario, BigDecimal total) {
		this.data = LocalDate.now();
		this.idFuncionario = idFuncionario;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
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

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}

	public void adicionarItem(ItemVenda item) {
		this.itens.add(item);
	}

	public void calcularTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (ItemVenda item : itens) {
			total = total.add(item.getSubtotal());
		}
		this.total = total;
	}

}

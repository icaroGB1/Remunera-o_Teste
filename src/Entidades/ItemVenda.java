package Entidades;

import java.math.BigDecimal;
import java.util.List;

public class ItemVenda {
	private int id;
	private int idVenda;
	private int idProduto;
	private int quantidade;
	private BigDecimal subtotal;
	private List<ItemVenda>itens;
	
	private Produto produto;


	public ItemVenda() {
	}

	public ItemVenda(int idVenda, int idProduto, int quantidade, BigDecimal subtotal) {
		this.idVenda = idVenda;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.subtotal = subtotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	
	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	  public void calcularSubtotal(Produto produto) {
	        BigDecimal precoUnitario = produto.getPreco();
	        BigDecimal quantidadeBigDecimal = new BigDecimal(quantidade);
	        subtotal = precoUnitario.multiply(quantidadeBigDecimal);
	    }

}

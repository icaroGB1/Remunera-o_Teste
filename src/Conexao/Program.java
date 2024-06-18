package Conexao;

import java.math.BigDecimal;

import Entidades.Produto;
import crud.ProdutoCRUDImpl;
import enums.CategoriaProduto;

public class Program {
	public static void main(String[] args) throws Exception {
		Produto prod = new Produto("Agua", "Agua mineral 500ML", CategoriaProduto.ALIMENTOS_E_BEBIDAS, new BigDecimal("2.50"));
		try {
			ProdutoCRUDImpl prodImpl = new ProdutoCRUDImpl();
			prodImpl.cadastrar(prod);
		}catch (Exception e) {
			System.out.println("Ocorreu um erro ao atualizar o Produto: " + e.getMessage());
		}
	}
}

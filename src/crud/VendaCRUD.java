package crud;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import Entidades.Venda;

public interface VendaCRUD {
	void cadastrar(Venda venda);
	void atualizar(Venda venda);
	void excluir(Venda venda);
	List<Venda> consultar();
	BigDecimal calcularTotalVenda(int idVenda);
	List<Venda> consultarPorData(LocalDate data);
	List<Venda> consultarPorFuncionario(int idFuncionario);

}

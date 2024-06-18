package crud;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import Entidades.Funcionario;
import Entidades.ItemVenda;
import Entidades.Venda;

public interface VendaCRUD {
	void cadastrar(Venda venda, Funcionario funcionario, List<ItemVenda> itensVenda);
	void excluir(Venda venda);
	List<Venda> consultar();
	BigDecimal calcularTotalVenda(int idVenda);
	List<Venda> consultarPorData(LocalDate data);
	List<Venda> consultarPorFuncionario(int idFuncionario);

}

package crud;

import java.math.BigDecimal;
import java.util.List;

import Entidades.Funcionario;
import Entidades.Pagamento;


public interface PagamentoCRUD {
	BigDecimal calcularValorAdicional(int funcionarioId) throws Exception;
	BigDecimal calcularSalarioTotal(int funcionarioId, Funcionario funcionario) throws Exception;
	void registrarPagamento(Funcionario funcionario, BigDecimal salarioBase, BigDecimal valorAdicional,
			BigDecimal salarioTotal) throws Exception;
	 List<Pagamento> consultarPagamentos();
	 List<Pagamento> consultarPagamentosPorFuncionario(int funcionarioId) throws Exception;
}

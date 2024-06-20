package crud;

import java.math.BigDecimal;
import java.util.List;

import Entidades.Meta;

public interface metaCRUD {
	void cadastrar(Meta meta);
	void atualizar(Meta meta);
	void excluir(int idMeta);
	Meta consultarPorId(int idMeta);
	List<Meta> consultarTodas() throws Exception;
	List<Meta> consultarPorFuncionario(int idFuncionario) throws Exception;
	BigDecimal calcularPorcentagemAtingida(int idMeta);
	BigDecimal calcularPorcentagemAtingidaFuncionario(int idFuncionario);
}

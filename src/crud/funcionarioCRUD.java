package crud;

import java.util.List;

import Entidades.Funcionario;
import Entidades.Meta;
import Entidades.Usuario;

public interface funcionarioCRUD  {
	void cadastrar(Funcionario funcionario, Usuario usuario);
	void atualizar(Funcionario funcionario);
	void excluir(Funcionario funcionario);
	List<Funcionario> consultar();
	void lancarProdutividade(Funcionario funcionario, Meta meta);
	void consultarPerfomace(Funcionario funcionario, Meta meta) throws Exception;
	void consultarPagamento();
	void consultarId(int id);
}	

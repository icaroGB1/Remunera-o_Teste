package crud;

import java.util.List;

import Entidades.Funcionario;
import Entidades.Usuario;

public interface funcionarioCRUD {
	void cadastrar(Funcionario funcionario, Usuario usuario);
	void atualizar(Funcionario funcionario);
	void excluir(Funcionario funcionario);
	List<Funcionario> consultar();
	void lancarProdutividade();
	void consultarPerfomace();
	void consultarPagamento();
}	

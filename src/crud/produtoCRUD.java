package crud;

import java.util.List;

import Entidades.Produto;

public interface produtoCRUD {
	void cadastrar(Produto produto);
	void atualizar(Produto produto);
	void excluir(Produto produto);
	List<Produto> consultar() throws Exception;
}

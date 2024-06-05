package crud;

import java.util.List;

import Entidades.Usuario;

public interface usuarioCRUD {
	void cadastrar(Usuario usuario);
	void atualizar(Usuario usuario);
	void excluir (Usuario usuario);
	void logar (Usuario usuario) throws Exception;
	List<Usuario> consultar();

}

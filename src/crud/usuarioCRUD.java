package crud;

import java.sql.Connection;
import java.util.List;

import Entidades.Usuario;

public interface usuarioCRUD {
	void cadastrar(Usuario usuario) throws Exception;
	void atualizar(Usuario usuario);
	void excluir (Usuario usuario);
	void logar (Usuario usuario) throws Exception; 
	List<Usuario> consultar();
	boolean EmailJaCadastrado(String email, Connection connection);
}

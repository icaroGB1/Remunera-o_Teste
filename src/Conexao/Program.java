package Conexao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Entidades.Usuario;
import crud.UsuarioCRUDImpl;
import crud.usuarioCRUD;

public class Program {
	public static void main(String[] args) throws Exception {
		Usuario usuario = new Usuario("Jane Doe", "jane@example.com", "password456");
		Usuario usuario1 = new Usuario("icaro", "icaro@", "13456icaro");
		Usuario usuario2 = new Usuario("icaro", "icaro@", "13456icaro8910");
		try {
			UsuarioCRUDImpl usuarioCrud = new UsuarioCRUDImpl(DB.getConnection());
			usuarioCrud.logar(usuario2);
		}catch (Exception e) {
			System.out.println("Ocorreu um erro ao atualizar o usuário: " + e.getMessage());
		}finally{
			DB.closeConnection();
		}

	}

}

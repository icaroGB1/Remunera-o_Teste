package Conexao;

import java.sql.SQLException;

import Entidades.Usuario;
import crud.usuarioCRUDImpl;

public class Program {
	public static void main(String[] args) throws Exception {
		Usuario usuario = new Usuario("icaro", "icaro@", "13456icaro");
		try {
			usuarioCRUDImpl usuarioCrud = new usuarioCRUDImpl(DB.getConnection());
			usuarioCrud.logar(usuario);
		}catch (SQLException e) {
			System.out.println("Ocorreu um erro ao atualizar o usuário: " + e.getMessage());
		}
	}
}

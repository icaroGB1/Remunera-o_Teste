package Conexao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
	public static void main(String[] args) throws IOException {
		Statement st = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			Connection connection = DB.getConnection();
			System.out.println("Conexão estabelecida com sucesso!");
			DB.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}

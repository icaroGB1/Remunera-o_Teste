package Conexao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Entidades.Produto;
import crud.ProdutoCRUDImpl;
import enums.CategoriaProduto;

public class Program {
	public static void main(String[] args) throws Exception {
		 String url = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?allowPublicKeyRetrieval=true&useSSL=false";
	        String user = "seu_usuario";
	        String password = "sua_senha";

	        try {
	            Class.forName("org.postgresql.Driver");
	            try (Connection connection = DriverManager.getConnection(url, user, password)) {
	                System.out.println("Conexão estabelecida com sucesso!");
	            } catch (SQLException e) {
	                throw new RuntimeException("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage(), e);
	            }
	        } catch (ClassNotFoundException e) {
	            throw new RuntimeException("Driver JDBC do PostgreSQL não encontrado.", e);
	        }
	    }
	}

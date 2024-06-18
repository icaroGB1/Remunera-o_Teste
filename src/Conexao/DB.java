package Conexao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	private static Properties loadProperties() {
		try (InputStream fs = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (fs == null) {
				throw new DbException("db.properties (O sistema não pode encontrar o arquivo especificado)");
			}
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	public static Connection getConnection() {
		Properties props = loadProperties();
		String url = props.getProperty("dburl") + "?allowPublicKeyRetrieval=true&useSSL=false";
		try {
			return DriverManager.getConnection(url, props.getProperty("user"), props.getProperty("password"));
		} catch (SQLException e) {
			throw new DbException("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage(), e);
		}
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException("Erro ao fechar conexão: " + e.getMessage(), e);
			}
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException("Erro ao fechar Statement: " + e.getMessage(), e);
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException("Erro ao fechar ResultSet: " + e.getMessage(), e);
			}
		}
	}

	public static void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				throw new DbException("Erro ao fechar PreparedStatement: " + e.getMessage(), e);
			}
		}
	}
}

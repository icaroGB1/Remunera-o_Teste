package crud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Conexao.DB;
import Entidades.Funcionario;
import Entidades.Usuario;

public class funcionarioCRUDimpl implements funcionarioCRUD {

	Statement st = null;
	Connection connection = null;
	ResultSet rs = null;
	Usuario usuario;

	public funcionarioCRUDimpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrar(Funcionario funcionario, Usuario usuario) {
		PreparedStatement cad = null;
		try {
			connection = DB.getConnection();
			cad = connection
					.prepareStatement("INSERT INTO funcionarios (user_id, nome, cargo, salario) values (?, ?, ?, ?)");
			cad.setInt(1, usuario.getId());
			cad.setString(2, usuario.getName());
			cad.setString(3, funcionario.getCargo());
			cad.setBigDecimal(4, funcionario.getSalario());
			cad.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(cad);
			DB.closeConnection();
		}

	}

	@Override
	public void atualizar(Funcionario funcionario) {
		PreparedStatement atualizar = null;
		Scanner sc = new Scanner(System.in);
		try {
			connection = DB.getConnection();
			System.out.println("Informe o que deseja atualizar");
			System.out.println("1-cargo\r\n" + "2-salario");
			int op = sc.nextInt();
			sc.nextLine();
			switch (op) {
			case 1:
				System.out.println("Informe o novo cargo do funcionaro");
				String cargoNovo = sc.nextLine();
				atualizar = connection.prepareStatement("UPDATE funcionarios set cargo = ? WHERE ID = ?");
				atualizar.setString(1, cargoNovo);
				atualizar.setInt(2, funcionario.getId());
				atualizar.executeUpdate();
				break;
			case 2:
				System.out.println("Informe o novo salario do funcionaro");
				BigDecimal salarioNovo = sc.nextBigDecimal();
				atualizar = connection.prepareStatement("UPDATE funcionarios set salario = ? WHERE ID = ?");
				atualizar.setBigDecimal(1, salarioNovo);
				atualizar.setInt(2, funcionario.getId());
				atualizar.executeUpdate();
				break;
			default:
				System.out.println("Opção invalida");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sc.close();
			DB.closePreparedStatement(atualizar);
			DB.closeConnection();
		}
	}

	@Override
	public void excluir(Funcionario funcionario) {
		PreparedStatement excluir = null;
		try {
			connection = DB.getConnection();
			excluir = connection.prepareStatement("DELETE FROM funcionarios WHERE id = ?");
			excluir.setInt(1, funcionario.getId());
			excluir.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(excluir);
			DB.closeConnection();
		}
	}

	public List<Funcionario> consultar() {
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			connection = DB.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT nome, cargo, salario  FROM funcionarios");
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setNome(rs.getString("nome"));
				funcionario.setCargo(rs.getString("cargo"));
				funcionario.setSalario(rs.getBigDecimal("salario"));
				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

		return funcionarios;
	}

	@Override
	public void lancarProdutividade() {

	}

	@Override
	public void consultarPerfomace() {

	}

	@Override
	public void consultarPagamento() {

	}

}

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
import Entidades.Meta;
import Entidades.Usuario;

public class funcionarioCRUDimpl implements funcionarioCRUD {

	private Usuario usuario;

	@Override
	public void cadastrar(Funcionario funcionario, Usuario usuario) {
		try (Connection connection = DB.getConnection();
				PreparedStatement cad = connection.prepareStatement(
						"INSERT INTO funcionarios (user_id, nome, cargo, salario) values (?, ?, ?, ?)")) {
			cad.setInt(1, usuario.getId());
			cad.setString(2, usuario.getNome());
			cad.setString(3, usuario.getCargo().name());
			cad.setBigDecimal(4, funcionario.getSalario());
			cad.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(Funcionario funcionario) {
		Scanner sc = new Scanner(System.in);
		try (Connection connection = DB.getConnection()) {
			System.out.println("Informe o que deseja atualizar");
			System.out.println("1-cargo\r\n" + "2-salario");
			int op = sc.nextInt();
			sc.nextLine();
			switch (op) {
			case 1:
				System.out.println("Informe o novo cargo do funcionaro");
				String cargoNovo = sc.nextLine();
				try (PreparedStatement atualizar = connection
						.prepareStatement("UPDATE funcionarios set cargo = ? WHERE ID = ?")) {
					atualizar.setString(1, cargoNovo);
					atualizar.setInt(2, funcionario.getId());
					atualizar.executeUpdate();
				}
				break;
			case 2:
				System.out.println("Informe o novo salario do funcionaro");
				BigDecimal salarioNovo = sc.nextBigDecimal();
				try (PreparedStatement atualizar = connection
						.prepareStatement("UPDATE funcionarios set salario = ? WHERE ID = ?")) {
					atualizar.setBigDecimal(1, salarioNovo);
					atualizar.setInt(2, funcionario.getId());
					atualizar.executeUpdate();
				}
				break;
			default:
				System.out.println("Opção invalida");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void excluir(Funcionario funcionario) {
		try (Connection connection = DB.getConnection();
				PreparedStatement excluir = connection.prepareStatement("DELETE FROM funcionarios WHERE id = ?")) {
			excluir.setInt(1, funcionario.getId());
			excluir.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Funcionario> consultar() {
		List<Funcionario> funcionarios = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT nome, cargo, salario FROM funcionarios")) {
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setNome(rs.getString("nome"));
				funcionario.setCargo(rs.getString("cargo"));
				funcionario.setSalario(rs.getBigDecimal("salario"));
				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return funcionarios;
	}

	@Override
	public void lancarProdutividade(Funcionario funcionario, Meta meta) {
		MetaCRUDimpl metas = new MetaCRUDimpl();
		metas.calcularPorcentagemAtingidaFuncionario(funcionario.getId());
	}

	@Override
	public void consultarPerfomace(Funcionario funcionario, Meta meta) throws Exception {
		MetaCRUDimpl metas = new MetaCRUDimpl();
		metas.consultarPorFuncionario(funcionario.getId());
	}

	@Override
	public void consultarPagamento() {

	}

	@Override
	public void consultarId(int id) {
		try (Connection connection = DB.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM funcionarios where id = ?")) {
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("id"));
				funcionario.setNome(rs.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

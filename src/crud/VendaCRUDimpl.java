package crud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Conexao.DB;
import Entidades.Funcionario;
import Entidades.ItemVenda;
import Entidades.Venda;
import exceptions.VendaNaoPermitidaException;

public class VendaCRUDimpl implements VendaCRUD {
	Scanner sc = new Scanner(System.in);

	@Override
	public void cadastrar(Venda venda, Funcionario funcionario, List<ItemVenda> itensVenda) throws Exception {
		Connection connection = null;
		try {
			connection = DB.getConnection();
			connection.setAutoCommit(false);

			try (PreparedStatement cad = connection.prepareStatement(
					"INSERT INTO vendas(data, id_funcionario, total) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
					PreparedStatement atualizarTotal = connection
							.prepareStatement("UPDATE vendas SET total = ? WHERE id = ?");
					PreparedStatement consultaItemVendas = connection
							.prepareStatement("SELECT * FROM item_venda WHERE id_venda = ?")) {

				cad.setDate(1, java.sql.Date.valueOf(venda.getData()));
				cad.setInt(2, funcionario.getId());
				cad.setBigDecimal(3, venda.getTotal());
				cad.executeUpdate();

				try (ResultSet rs = cad.getGeneratedKeys()) {
					if (rs.next()) {
						int ultimoId = rs.getInt(1);
						venda.setId(ultimoId);
					}
				}

				ItemVendaCRUDImpl itemVendasCRUD = new ItemVendaCRUDImpl();
				itemVendasCRUD.cadastrar(null, venda, itensVenda);

				venda.calcularTotal();
				atualizarTotal.setBigDecimal(1, venda.getTotal());
				atualizarTotal.setInt(2, venda.getId());
				atualizarTotal.executeUpdate();

				connection.commit();

			} catch (SQLException e) {
				if (connection != null) {
					connection.rollback();
				}
				throw new VendaNaoPermitidaException("Erro ao cadastrar venda: " + e.getMessage(), e);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao obter conexão com o banco de dados: " + e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.err.println("Erro ao fechar a conexão: " + e.getMessage());
				}
			}
		}
	}

	@Override
	public void excluir(Venda venda) {
		try (Connection connection = DB.getConnection();
				PreparedStatement excluir = connection.prepareStatement("DELETE FROM vendas WHERE id = ?")) {
			excluir.setInt(1, venda.getId());
			excluir.executeUpdate();

		} catch (SQLException e) {
			System.err.println("Erro ao excluir venda: " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public List<Venda> consultar() {
		List<Venda> vendas = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("Select * from vendas")) {
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				LocalDate data = rs.getDate("data").toLocalDate();
				venda.setData(data);
				venda.setIdFuncionario(rs.getInt("id_funcionario"));
				venda.setTotal(rs.getBigDecimal("total"));
				vendas.add(venda);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao consultar vendas: " + e.getMessage());
			e.printStackTrace();
		}
		return vendas;
	}

	@Override
	public BigDecimal calcularTotalVenda(int idVenda) {
		BigDecimal total = BigDecimal.ZERO;
		try (Connection connection = DB.getConnection();
				PreparedStatement total1 = connection.prepareStatement("SELECT SUM(total) from vendas")) {
			try (ResultSet rs = total1.executeQuery()) {
				if (rs.next()) {
					total = rs.getBigDecimal("total");
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao calcular total de vendas: " + e.getMessage());
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public List<Venda> consultarPorData(LocalDate dataInicial) {
		List<Venda> vendas = new ArrayList<>();
		LocalDate dataFinal = LocalDate.now();

		try (Connection connection = DB.getConnection();
				PreparedStatement consultaData = connection
						.prepareStatement("SELECT * FROM vendas WHERE data BETWEEN ? AND ?")) {

			consultaData.setDate(1, java.sql.Date.valueOf(dataInicial));
			consultaData.setDate(2, java.sql.Date.valueOf(dataFinal));

			try (ResultSet rs = consultaData.executeQuery()) {
				while (rs.next()) {
					Venda venda = new Venda();
					venda.setId(rs.getInt("id"));
					LocalDate data = rs.getDate("data").toLocalDate();
					venda.setData(data);
					venda.setIdFuncionario(rs.getInt("id_funcionario"));
					venda.setTotal(rs.getBigDecimal("total"));
					vendas.add(venda);
				}
			}

		} catch (SQLException e) {
			System.err.println("Erro ao consultar vendas por data: " + e.getMessage());
			e.printStackTrace();
		}

		return vendas;
	}

	@Override
	public List<Venda> consultarPorFuncionario(int idFuncionario) {
		List<Venda> vendas = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				PreparedStatement consultaFunc = connection
						.prepareStatement("SELECT id, data, total FROM vendas WHERE id_funcionario = ?")) {
			consultaFunc.setInt(1, idFuncionario);

			try (ResultSet rs = consultaFunc.executeQuery()) {
				while (rs.next()) {
					Venda venda = new Venda();
					venda.setId(rs.getInt("id"));
					LocalDate data = rs.getDate("data").toLocalDate();
					venda.setData(data);
					venda.setIdFuncionario(rs.getInt("id_funcionario"));
					venda.setTotal(rs.getBigDecimal("total"));
					vendas.add(venda);
				}
			}

		} catch (SQLException e) {
			System.err.println("Erro ao consultar vendas por funcionário: " + e.getMessage());
			e.printStackTrace();
		}

		return vendas;
	}
}

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

import Conexao.DB;
import Entidades.Funcionario;
import Entidades.ItemVenda;
import Entidades.Produto;
import Entidades.Venda;

public class VendaCRUDimpl implements VendaCRUD {

	@Override
	public void cadastrar(Venda venda, Funcionario funcionario, List<ItemVenda> itensVenda) {
		try (Connection connection = DB.getConnection();
				PreparedStatement cad = connection.prepareStatement(
						"INSERT INTO vendas(data, id_funcionario, total) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				PreparedStatement atualizarTotal = connection
						.prepareStatement("UPDATE vendas SET total = ? WHERE id = ?")) {

			connection.setAutoCommit(false);

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
			try {
				DB.getConnection().rollback();
			} catch (SQLException ex) {
				System.err.println("Erro ao cadastrar venda: " + e.getMessage());
				ex.printStackTrace();
			}
			e.printStackTrace();
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
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM vendas");
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				LocalDate data = rs.getDate("data").toLocalDate();
				int idFuncionario = rs.getInt("id_funcionario");
				BigDecimal total = rs.getBigDecimal("total");

				Funcionario funcionario = funcionarioCRUDimpl
				List<ItemVenda> itensVenda = ItemVendaCRUDImpl

				Venda venda = new Venda(id, data, funcionario, itensVenda, total);
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
				PreparedStatement stmt = connection.prepareStatement("SELECT total FROM vendas WHERE id = ?")) {

			stmt.setInt(1, idVenda);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				total = rs.getBigDecimal("total");
			}

		} catch (SQLException e) {
			System.err.println("Erro ao calcular total da venda: " + e.getMessage());
			e.printStackTrace();
		}

		return total;
	}

	@Override
	public List<Venda> consultarPorData(LocalDate data) {
		List<Venda> vendas = new ArrayList<>();

		try (Connection connection = DB.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM vendas WHERE data = ?")) {

			stmt.setDate(1, java.sql.Date.valueOf(data));
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				LocalDate dataVenda = rs.getDate("data").toLocalDate();
				int idFuncionario = rs.getInt("id_funcionario");
				BigDecimal total = rs.getBigDecimal("total");

				funcionarioCRUDimpl funcionario = funcionario.consultarId(idFuncionario);
				List<ItemVenda> itensVenda = ItemVendaCRUDImpl.consultar(id);

				Venda venda = new Venda(id, dataVenda, funcionario, itensVenda, total);
				vendas.add(venda);
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
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM vendas WHERE id_funcionario = ?")) {

			stmt.setInt(1, idFuncionario);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				LocalDate data = rs.getDate("data").toLocalDate();
				BigDecimal total = rs.getBigDecimal("total");

				funcionarioCRUDimpl funcionario = funcionario.consultarId(idFuncionario);
				List<ItemVenda> itensVenda = ItemVendaCRUDImpl.consultar(id);

				Venda venda = new Venda(data, funcionario, itensVenda, total);
				vendas.add(venda);
			}

		} catch (SQLException e) {
			System.err.println("Erro ao consultar vendas por funcionário: " + e.getMessage());
			e.printStackTrace();
		}

		return vendas;
	}

}

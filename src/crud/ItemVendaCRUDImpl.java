package crud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Conexao.DB;
import Entidades.ItemVenda;
import Entidades.Produto;
import Entidades.Venda;

public class ItemVendaCRUDImpl implements ItemVendaCRUD {

	@Override
	public void cadastrar(ItemVenda itemVenda, Venda venda, List<ItemVenda> itensVenda) {
		try (Connection connection = DB.getConnection()) {
			for (ItemVenda itens : itensVenda) {
				Produto produto = itens.getProduto();
				itens.calcularSubtotal();
				try (PreparedStatement cad = connection.prepareStatement(
						"INSERT INTO itemvenda (id_venda, id_produto, quantidade, subtotal) values(?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {
					cad.setInt(1, itemVenda.getIdVenda());
					cad.setInt(2, produto.getId());
					cad.setInt(3, itens.getQuantidade());
					cad.setBigDecimal(4, itens.getSubtotal());
					cad.executeUpdate();

					ResultSet rs = cad.getGeneratedKeys();
					if (rs.next()) {
						int generatedId = rs.getInt(1);
						itemVenda.setId(generatedId);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(ItemVenda itemVenda) {
		try (Connection connection = DB.getConnection();
				PreparedStatement atualizar = connection
						.prepareStatement("UPDATE itemvenda SET quantidade = ?, subtotal = ? WHERE id = ?")) {
			atualizar.setInt(1, itemVenda.getQuantidade());
			atualizar.setBigDecimal(2, itemVenda.getSubtotal());
			atualizar.setInt(3, itemVenda.getId());
			atualizar.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void excluir(int idItemVenda) {
		try (Connection connection = DB.getConnection();
				PreparedStatement excluir = connection.prepareStatement("DELETE FROM itemvenda WHERE ID = ?")) {
			excluir.setInt(1, idItemVenda);
			excluir.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ItemVenda> consultar() {
		List<ItemVenda> itemVendas = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT quantidade, subtotal FROM itemvenda")) {
			while (rs.next()) {
				ItemVenda vendas = new ItemVenda();
				vendas.setQuantidade(rs.getInt("quantidade"));
				vendas.setSubtotal(rs.getBigDecimal("subtotal"));
				itemVendas.add(vendas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemVendas;
	}

	@Override
	public ItemVenda consultarPorId(int id) throws SQLException {
		ItemVenda itemVenda = null;
		try (Connection connection = DB.getConnection();
				PreparedStatement consultarPorId = connection
						.prepareStatement("SELECT * FROM itemvenda WHERE ID = ?")) {

			consultarPorId.setInt(1, id);
			ResultSet rs = consultarPorId.executeQuery();

			if (rs.next()) {
				int idVenda = rs.getInt("id_venda");
				int idProduto = rs.getInt("id_produto");
				int quantidade = rs.getInt("quantidade");
				BigDecimal subtotal = rs.getBigDecimal("subtotal");
				itemVenda = new ItemVenda(id, idProduto, quantidade, subtotal);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemVenda;
	}
	@Override
	public List<ItemVenda> consultarPorVenda(int idVenda) {
		List<ItemVenda> itemVendas = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				PreparedStatement consultaVenda = connection
						.prepareStatement("SELECT id_produto, quantidade, subtotal FROM itemvenda WHERE id_venda = ?")) {
			consultaVenda.setInt(1, idVenda);

			try (ResultSet rs = consultaVenda.executeQuery()) {
				while (rs.next()) {
					ItemVenda itemVenda = new ItemVenda();
					Produto produto = new Produto();
					itemVenda.setProduto(produto);
					itemVenda.setQuantidade(rs.getInt("quantidade"));
					itemVenda.setSubtotal(rs.getBigDecimal("subtotal"));
					itemVendas.add(itemVenda);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemVendas;
	}
}


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
				itens.calcularSubtotal(produto);
				try (PreparedStatement cad = connection.prepareStatement(
						"INSERT INTO itemvenda (id_venda, id_produto, quantidade, subtotal) values(?, ?, ?, ?)")) {
					cad.setInt(1, venda.getId());
					cad.setInt(2, produto.getId());
					cad.setInt(3, itens.getQuantidade());
					cad.setBigDecimal(4, itens.getSubtotal());
					cad.executeUpdate();
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
						.prepareStatement("UPDATE itemvenda set quantidade = ? WHERE ID = ?")) {
			atualizar.setInt(1, itemVenda.getQuantidade());
			atualizar.setInt(2, itemVenda.getId());
			atualizar.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void excluir(ItemVenda itemVenda) {
		try (Connection connection = DB.getConnection();
				PreparedStatement excluir = connection.prepareStatement("DELETE FROM itemvenda WHERE ID = ?")) {
			excluir.setInt(1, itemVenda.getId());
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

	public void consultarPorId(int id) throws SQLException {
		ItemVenda itemVenda = null;
		try (Connection connection = DB.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM itemvenda WHERE ID = ?")) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int idVenda = rs.getInt("id_venda");
				int idProduto = rs.getInt("id_produto");
				int quantidade = rs.getInt("quantidade");
				BigDecimal subtotal = rs.getBigDecimal("subtotal");

				itemVenda = new ItemVenda(id, idProduto, quantidade, subtotal);
			}

		}catch(SQLException e ) {
			e.printStackTrace();
		}
	}
}

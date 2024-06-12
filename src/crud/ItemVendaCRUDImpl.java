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
	Statement st = null;
	Connection connection = null;
	ResultSet rs = null;

	@Override
	public void cadastrar(ItemVenda itemVenda, Venda venda, Produto produto) {
		PreparedStatement cad = null;
		try {
			connection = DB.getConnection();
			cad = connection.prepareStatement(
					"INSERT INTO itemvenda (id_venda, id_produto, quantidade, subtotal) values(?, ?, ?, ?)");
			cad.setInt(1, venda.getId());
			cad.setInt(2, produto.getId());
			cad.setInt(3, itemVenda.getQuantidade());
			cad.setBigDecimal(4, itemVenda.getSubtotal());
			cad.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(cad);
			DB.closeConnection();
		}
	}

	@Override
	public void atualizar(ItemVenda itemVenda) {
		PreparedStatement atualizar = null;
		try {
			connection = DB.getConnection();
			atualizar = connection.prepareStatement("UPDATE itemvenda set quantidade = ? WHERE ID = ?");
			atualizar.setInt(1, itemVenda.getQuantidade());
			atualizar.setInt(2, itemVenda.getId());
			atualizar.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(atualizar);
			DB.closeConnection();
		}

	}

	@Override
	public void excluir(ItemVenda itemVenda) {
		PreparedStatement excluir = null;
		try {
			connection = DB.getConnection();
			excluir = connection.prepareStatement("DELETE FROM itemvenda WHERE ID = ?");
			excluir.setInt(1, itemVenda.getId());
			excluir.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(excluir);
			DB.closeConnection();
		}

	}

	@Override
	public List<ItemVenda> consultar() {
		List<ItemVenda> itemVendas = new ArrayList<>();
		try {
			connection = DB.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT quantidade, subtotal  FROM  itemvenda");
			while (rs.next()) {
				ItemVenda vendas = new ItemVenda();
				vendas.setQuantidade(rs.getInt("quantidade"));
				vendas.setSubtotal(rs.getBigDecimal("subtotal"));
				itemVendas.add(vendas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
		return itemVendas;
	}

}

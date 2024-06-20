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
import Entidades.Produto;
import enums.CategoriaProduto;
import exceptions.ProdutoNaoEncontradoException;

public class ProdutoCRUDImpl implements produtoCRUD {
	private Scanner sc = new Scanner(System.in);

	@Override
	public void cadastrar(Produto produto) {
		try (Connection connection = DB.getConnection();
				PreparedStatement cad = connection.prepareStatement(
						"INSERT INTO produtos (nome, descricao, categoria, preco) VALUES (?, ?, ?, ?)")) {
			cad.setString(1, produto.getNome());
			cad.setString(2, produto.getDescricao());
			cad.setString(3, produto.getCategoria().name());
			cad.setBigDecimal(4, produto.getPreco());
			cad.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao cadastrar produto: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(Produto produto) {
		try (Connection connection = DB.getConnection()) {
			try (PreparedStatement atualizar = connection.prepareStatement(
					"UPDATE produtos SET nome = ?, descricao = ?, categoria = ?, preco = ? WHERE id = ?")) {
				atualizar.setString(1, produto.getNome());
				atualizar.setString(2, produto.getDescricao());
				atualizar.setString(3, produto.getCategoria().name());
				atualizar.setBigDecimal(4, produto.getPreco());
				atualizar.setInt(5, produto.getId());
				atualizar.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar produto: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void excluir(Produto produto) {
		try (Connection connection = DB.getConnection();
				PreparedStatement excluir = connection.prepareStatement("DELETE FROM produtos WHERE id = ?")) {
			excluir.setInt(1, produto.getId());
			excluir.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao excluir produto: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<Produto> consultar() {
		List<Produto> produtos = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT id, nome, descricao, categoria, preco FROM produtos")) {
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCategoria(CategoriaProduto.valueOf(rs.getString("categoria")));
				produto.setPreco(rs.getBigDecimal("preco"));
				produtos.add(produto);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao consultar produtos: " + e.getMessage());
			e.printStackTrace();
		}
		return produtos;
	}

	@Override
	public Produto consultarPorId(int idProduto) throws Exception {
		try (Connection connection = DB.getConnection();
				PreparedStatement st = connection.prepareStatement("SELECT * FROM produtos WHERE id = ?")) {
			st.setInt(1, idProduto);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Produto produto = new Produto();
					produto.setId(rs.getInt("id"));
					produto.setNome(rs.getString("nome"));
					produto.setDescricao(rs.getString("descricao"));
					produto.setCategoria(CategoriaProduto.valueOf(rs.getString("categoria")));
					produto.setPreco(rs.getBigDecimal("preco"));
					return produto;
				} else {
					throw new ProdutoNaoEncontradoException("Produto nao encontrado");
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao Consultar Produto: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}

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
			System.out.println("Informe o que deseja atualizar");
			System.out.println("1-nome\r\n" + "2-descrição\r\n " + "3-categoria\r\n" + "4-preço");
			int op = sc.nextInt();
			sc.nextLine();
			switch (op) {
			case 1:
				System.out.println("Informe o novo nome");
				String novoNome = sc.nextLine();
				try (PreparedStatement atualizar = connection
						.prepareStatement("UPDATE produtos SET nome = ? WHERE id = ?")) {
					atualizar.setString(1, novoNome);
					atualizar.setInt(2, produto.getId());
					atualizar.executeUpdate();
				}
				break;
			case 2:
				System.out.println("Informe a nova Descrição");
				String novaDescricao = sc.nextLine();
				try (PreparedStatement atualizar = connection
						.prepareStatement("UPDATE produtos SET descricao = ? WHERE id = ?")) {
					atualizar.setString(1, novaDescricao);
					atualizar.setInt(2, produto.getId());
					atualizar.executeUpdate();
				}
				break;
			case 3:
				System.out.println("Informe a nova categoria");
				String novaCategoria = sc.nextLine();
				try (PreparedStatement atualizar = connection
						.prepareStatement("UPDATE produtos SET categoria = ? WHERE id = ?")) {
					atualizar.setString(1, novaCategoria);
					atualizar.setInt(2, produto.getId());
					atualizar.executeUpdate();
				}
				break;
			case 4:
				System.out.println("Informe o novo preço");
				BigDecimal novoPreco = sc.nextBigDecimal();
				try (PreparedStatement atualizar = connection
						.prepareStatement("UPDATE produtos SET preco = ? WHERE id = ?")) {
					atualizar.setBigDecimal(1, novoPreco);
					atualizar.setInt(2, produto.getId());
					atualizar.executeUpdate();
				}
				break;
			default:
				System.out.println("Opção inválida");
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

	public Produto consultarPorNome(String nomeProduto) {
		// TODO Auto-generated method stub
		return null;
	}
}

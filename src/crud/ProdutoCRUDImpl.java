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
	Statement st = null;
	Connection connection = null;
	ResultSet rs = null;

	@Override
	public void cadastrar(Produto produto) {
		PreparedStatement cad = null;
		try {
			connection = DB.getConnection();
			cad = connection
					.prepareStatement("INSERT INTO produtos (nome,descricao,categoria,preco) values (?, ?, ?,?)");
			cad.setString(1, produto.getNome());
			cad.setString(2, produto.getDescricao());
			cad.setString(3, produto.getCategoria().name());
			cad.setBigDecimal(4, produto.getPreco());
			cad.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(cad);
			DB.closeConnection();
		}
	}

	@Override
	public void atualizar(Produto produto) {
		PreparedStatement atualizar = null;
		Scanner sc = new Scanner(System.in);
		try {
			connection = DB.getConnection();
			System.out.println("Informe o que deseja atualizar");
			System.out.println("1-nome\r\n" + "2-descrição\r\n " + "3-categoria\r\n" + "4-preço");
			int op = sc.nextInt();
			sc.nextLine();
			switch (op) {
			case 1:
				System.out.println("Informe o novo nome");
				String novoNome = sc.nextLine();
				atualizar = connection.prepareStatement("UPDATE produtos set nome = ? WHERE ID =?");
				atualizar.setString(1, novoNome);
				atualizar.setInt(2, produto.getId());
				atualizar.executeUpdate();
				break;
			case 2:
				System.out.println("Informe a nova Descrição");
				String novaDescricao = sc.nextLine();
				atualizar = connection.prepareStatement("UPDATE produtos set descricao = ? WHERE ID =?");
				atualizar.setString(1, novaDescricao);
				atualizar.setInt(2, produto.getId());
				atualizar.executeUpdate();
				break;
			case 3:
				System.out.println("Qual categoria você deseja botar");
				System.out.println(produto.getCategoria().name());
				atualizar = connection.prepareStatement("UPDATE produtos set categoria = ? WHERE ID =?");
				atualizar.setString(1, produto.getCategoria().name());
				atualizar.setInt(2, produto.getId());
				atualizar.executeUpdate();
				break;
			case 4:
				System.out.println("Informe o novo preço");
				BigDecimal novoPreco = sc.nextBigDecimal();
				atualizar = connection.prepareStatement("UPDATE produtos set preco = ? WHERE ID =?");
				atualizar.setBigDecimal(1, novoPreco);
				atualizar.setInt(2, produto.getId());
				atualizar.executeUpdate();
				break;
			default:
				System.out.println("Opção invalida");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(atualizar);
			DB.closeConnection();
		}

	}

	@Override
	public void excluir(Produto produto) {
		PreparedStatement excluir = null;
		try {
			connection = DB.getConnection();
			excluir = connection.prepareStatement("DELETE FROM produtos WHERE ID = ?");
			excluir.setInt(1, produto.getId());
			excluir.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(excluir);
			DB.closeConnection();
		}
	}

	@Override
	public List<Produto> consultar() {
		List<Produto> produtos = new ArrayList<>();
		try {
			connection = DB.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT nome,descricao,categoria,preco  FROM  produtos");
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCategoria(CategoriaProduto.valueOf(rs.getString("categoria")));
				produto.setPreco(rs.getBigDecimal("preco"));
				produtos.add(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
		return produtos;
	}
}

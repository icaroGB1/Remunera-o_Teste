package crud;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Conexao.DB;
import Entidades.Meta;
import Entidades.Produto;

public class MetaCRUDimpl implements metaCRUD {

	@Override
	public void cadastrar(Meta meta) {
		try (Connection connection = DB.getConnection();
				PreparedStatement cad = connection.prepareStatement(
						"INSERT INTO metas  (descricao, valor_meta, valor_atingido, produto_id, funcionario_id) VALUES (?, ?, 0, ?, ?)")) {
			cad.setString(1, meta.getDescricao());
			cad.setBigDecimal(2, meta.getValorMeta());
			cad.setInt(3, meta.getProduto().getId());
			cad.setInt(4, meta.getFuncionarioId());
			cad.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao cadastrar metas: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(Meta meta) {
		try (Connection connection = DB.getConnection();
				PreparedStatement atualizar = connection.prepareStatement(
						"UPDATE metas SET descricao = ?, valor_meta = ?, valor_atingido = ?, produto_id = ?, funcionario_id = ? WHERE id = ?")) {
			atualizar.setString(1, meta.getDescricao());
			atualizar.setBigDecimal(2, meta.getValorMeta());
			atualizar.setBigDecimal(3, meta.getValorAtingido());
			atualizar.setInt(4, meta.getProduto().getId());
			atualizar.setInt(5, meta.getFuncionarioId());
			atualizar.setInt(6, meta.getId());
			atualizar.executeUpdate();

		} catch (SQLException e) {
			System.err.println("Erro ao atualizar meta: " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(int idMeta) {
		try (Connection connection = DB.getConnection();
				PreparedStatement excluir = connection.prepareStatement("DELETE FROM metas WHERE id = ?")) {
			excluir.setInt(1, idMeta);
			excluir.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao excluir meta: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Meta consultarPorId(int idMeta) {
		try (Connection connection = DB.getConnection();
				PreparedStatement consultaId = connection.prepareStatement("SELECT * FROM metas where id = ?")) {
			consultaId.setInt(1, idMeta);
			try (ResultSet rs = consultaId.executeQuery()) {
				if (rs.next()) {
					Meta meta = new Meta();
					meta.setId(rs.getInt("id"));
					meta.setDescricao(rs.getString("descricao"));
					meta.setValorMeta(rs.getBigDecimal("valor_meta"));
					meta.setValorAtingido(rs.getBigDecimal("valor_atingido"));
					return meta;
				} else {
					System.err.println("Meta não encontrada para o ID: " + idMeta);
				}
			}

		} catch (SQLException e) {
			System.err.println("Erro ao Consultar Metas" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Meta> consultarTodas() throws Exception {
		List<Meta> metas = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM METAS")) {
			while (rs.next()) {
				Meta meta = new Meta();
				meta.setId(rs.getInt("id"));
				meta.setDescricao(rs.getString("descricao"));
				meta.setValorMeta(rs.getBigDecimal("valor_meta"));
				meta.setValorAtingido(rs.getBigDecimal("valor_atingido"));
				int produtoId = rs.getInt("produto_id");
				ProdutoCRUDImpl ProdutoCrud = new ProdutoCRUDImpl();
				Produto produto = ProdutoCrud.consultarPorId(produtoId);
				meta.setProduto(produto);
				meta.setFuncionarioId(rs.getInt("funcionario_id"));
				metas.add(meta);
			}

		} catch (SQLException e) {
			System.err.println("Erro ao Consultar Metas" + e.getMessage());
			e.printStackTrace();
		}
		return metas;
	}

	@Override
	public List<Meta> consultarPorFuncionario(int idFuncionario) throws Exception {
		List<Meta> metas = new ArrayList<>();
		try (Connection connection = DB.getConnection();
				PreparedStatement consultaId = connection
						.prepareStatement("SELECT * FROM metas where funcionario_id = ?")) {
			consultaId.setInt(1, idFuncionario);
			try (ResultSet rs = consultaId.executeQuery()) {
				while (rs.next()) {
					Meta meta = new Meta();
					meta.setId(rs.getInt("id"));
					meta.setDescricao(rs.getString("descricao"));
					meta.setValorMeta(rs.getBigDecimal("valor_meta"));
					meta.setValorAtingido(rs.getBigDecimal("valor_atingido"));
					int produtoId = rs.getInt("produto_id");
					ProdutoCRUDImpl ProdutoCrud = new ProdutoCRUDImpl();
					Produto produto = ProdutoCrud.consultarPorId(produtoId);
					meta.setProduto(produto);
					meta.setFuncionarioId(rs.getInt("funcionario_id"));
					metas.add(meta);
					return metas;
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao Consultar Metas" + e.getMessage());
			e.printStackTrace();
		}
		return metas;
	}

	@Override
	public BigDecimal calcularPorcentagemAtingida(int idMeta) {
		BigDecimal valorAtingido = BigDecimal.ZERO;
		BigDecimal valorMeta = BigDecimal.ZERO;

		try (Connection connection = DB.getConnection();
				PreparedStatement consultaMeta = connection
						.prepareStatement("SELECT valor_atingido, valor_meta FROM metas WHERE id = ?")) {

			consultaMeta.setInt(1, idMeta);
			try (ResultSet rs = consultaMeta.executeQuery()) {
				if (rs.next()) {
					valorAtingido = rs.getBigDecimal("valor_atingido");
					valorMeta = rs.getBigDecimal("valor_meta");
				}
			}

		} catch (SQLException e) {
			System.err.println("Erro ao calcular porcentagem atingida: " + e.getMessage());
			e.printStackTrace();
		}

		if (valorMeta.compareTo(BigDecimal.ZERO) > 0) {
			return valorAtingido.divide(valorMeta, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
		} else {
			return BigDecimal.ZERO;
		}
	}

	public BigDecimal calcularPorcentagemAtingidaFuncionario(int idFuncionario) {
		BigDecimal valorAtingido = BigDecimal.ZERO;
		BigDecimal valorMeta = BigDecimal.ZERO;

		try (Connection connection = DB.getConnection();
				PreparedStatement consultaMeta = connection
						.prepareStatement("SELECT valor_atingido, valor_meta FROM metas WHERE funcionario_id = ?")) {
			consultaMeta.setInt(1, idFuncionario);
			consultaMeta.executeUpdate();
			try (ResultSet rs = consultaMeta.executeQuery()) {
				if (rs.next()) {
					valorAtingido = rs.getBigDecimal("valor_atingido");
					valorMeta = rs.getBigDecimal("valor_meta");
				}
			}

		} catch (SQLException e) {
			System.err.println("Erro ao calcular porcentagem atingida: " + e.getMessage());
			e.printStackTrace();
		}

		if (valorMeta.compareTo(BigDecimal.ZERO) > 0) {
			return valorAtingido.divide(valorMeta, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
		} else {
			return BigDecimal.ZERO;
		}
	}
}

package crud;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Conexao.DB;
import Entidades.Funcionario;
import Entidades.Pagamento;

public class PagamentoCRUDimpl implements PagamentoCRUD {

	@Override
	public BigDecimal calcularValorAdicional(int funcionarioId) throws Exception {
		BigDecimal valorAdicional = BigDecimal.ZERO;

		try (Connection connection = DB.getConnection();
				PreparedStatement consultaMetas = connection
						.prepareStatement("SELECT valor_atingido, valor_meta FROM metas WHERE funcionario_id = ?")) {

			consultaMetas.setInt(1, funcionarioId);
			try (ResultSet rs = consultaMetas.executeQuery()) {
				while (rs.next()) {
					BigDecimal valorAtingido = rs.getBigDecimal("valor_atingido");
					BigDecimal valorMeta = rs.getBigDecimal("valor_meta");
					System.out.println("valorAtingido: " + valorAtingido + ", valorMeta: " + valorMeta);

					if (valorMeta.compareTo(BigDecimal.ZERO) > 0) {
						BigDecimal porcentagemAtingida = valorAtingido.divide(valorMeta, 2, RoundingMode.HALF_UP);
						BigDecimal adicionalMeta = porcentagemAtingida.multiply(valorMeta); // ou qualquer lógica de
																							// cálculo de adicional
						valorAdicional = valorAdicional.add(adicionalMeta);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao calcular valor adicional: " + e.getMessage());
			e.printStackTrace();
		}

		return valorAdicional;
	}

	@Override
	public BigDecimal calcularSalarioTotal(int funcionarioId, Funcionario funcionario) throws Exception {
		BigDecimal salarioBase = funcionario.getSalario();
		BigDecimal valorAdicional = calcularValorAdicional(funcionarioId);
		return salarioBase.add(valorAdicional);
	}

	@Override
	public void registrarPagamento(Funcionario funcionario, BigDecimal salarioBase, BigDecimal valorAdicional,
			BigDecimal salarioTotal) throws Exception {
		try (Connection connection = DB.getConnection();
				PreparedStatement registrarPagamento = connection.prepareStatement(
						"INSERT INTO pagamentos(funcionario_id, salario_base, valor_adicional, salario_total, data_pagamento) VALUES (?, ?, ?, ?, ?)")) {
			registrarPagamento.setInt(1, funcionario.getId());
			registrarPagamento.setBigDecimal(2, funcionario.getSalario());
			registrarPagamento.setBigDecimal(3, valorAdicional);
			registrarPagamento.setBigDecimal(4, salarioTotal);
			registrarPagamento.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
			registrarPagamento.executeUpdate();

		} catch (SQLException e) {
			System.err.println("Erro no registrarPagamento");
			e.printStackTrace();
		}

	}

	@Override
	public List<Pagamento> consultarPagamentos() {
		List<Pagamento> pagamentos = new ArrayList<>();

		try (Connection connection = DB.getConnection();
				PreparedStatement consultaPagamentos = connection.prepareStatement("SELECT * FROM pagamentos")) {
			try (ResultSet rs = consultaPagamentos.executeQuery()) {
				while (rs.next()) {
					Pagamento pagamento = new Pagamento();
					pagamento.setId(rs.getInt("id"));
					pagamento.setFuncionarioId(rs.getInt("funcionario_id"));
					pagamento.setSalarioBase(rs.getBigDecimal("salario_base"));
					pagamento.setValorAdicional(rs.getBigDecimal("valor_adicional"));
					pagamento.setSalarioTotal(rs.getBigDecimal("salario_total"));
					pagamento.setDataPagamento(rs.getDate("data_pagamento").toLocalDate());

					pagamentos.add(pagamento);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao consultar pagamentos: " + e.getMessage());
			e.printStackTrace();
		}

		return pagamentos;
	}

	@Override
	public List<Pagamento> consultarPagamentosPorFuncionario(int funcionarioId) throws Exception {
		List<Pagamento> pagamentos = new ArrayList<>();

		try (Connection connection = DB.getConnection();
				PreparedStatement consultaPagamentos = connection
						.prepareStatement("SELECT * FROM pagamentos WHERE funcionario_id = ?")) {
			consultaPagamentos.setInt(1, funcionarioId);
			try (ResultSet rs = consultaPagamentos.executeQuery()) {
				while (rs.next()) {
					Pagamento pagamento = new Pagamento();
					pagamento.setId(rs.getInt("id"));
					pagamento.setFuncionarioId(rs.getInt("funcionario_id"));
					pagamento.setSalarioBase(rs.getBigDecimal("salario_base"));
					pagamento.setValorAdicional(rs.getBigDecimal("valor_adicional"));
					pagamento.setSalarioTotal(rs.getBigDecimal("salario_total"));
					pagamento.setDataPagamento(rs.getDate("data_pagamento").toLocalDate());

					pagamentos.add(pagamento);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao consultar pagamentos por funcionário: " + e.getMessage());
			e.printStackTrace();
		}

		return pagamentos;
	}
}

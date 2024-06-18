package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Conexao.DB;
import Entidades.Usuario;
import enums.Cargo;
import exceptions.EmailInvalidoException;
import exceptions.EmailJaCadastradoException;
import exceptions.senhaIncorretaException;
import exceptions.usuarioOuSenhaIncorretaException;

public class usuarioCRUDImpl implements usuarioCRUD {

	private Connection getConnection() throws SQLException {
		return DB.getConnection();
	}

	@Override
	public void cadastrar(Usuario usuario) throws Exception {
		try (Connection connection = getConnection();
				PreparedStatement cad = connection
						.prepareStatement("INSERT INTO usuarios (nome, email, senha, cargo) VALUES (?, ?, ?, ?)")) {
			if (EmailJaCadastrado(usuario.getEmail(), connection)) {
				throw new EmailJaCadastradoException("Email já cadastrado");
			}
			cad.setString(1, usuario.getNome());
			cad.setString(2, usuario.getEmail());
			cad.setString(3, usuario.getSenha());
			cad.setString(4, usuario.getCargo().name());
			cad.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao cadastrar usuário: " + e.getMessage());
		}
	}

	@Override
	public void atualizar(Usuario usuario) {
		try (Connection connection = getConnection(); Scanner sc = new Scanner(System.in)) {
			System.out.println("Informe o que deseja atualizar");
			System.out.println("1-Nome\r\n" + "2-Email\r\n" + "3-Senha\r\n" + "4-Cargo");
			int op = sc.nextInt();
			sc.nextLine();
			PreparedStatement atualizar;
			switch (op) {
			case 1:
				System.out.println("Informe o novo nome");
				String novoNome = sc.nextLine();
				atualizar = connection.prepareStatement("UPDATE usuarios SET nome = ? WHERE id = ?");
				atualizar.setString(1, novoNome);
				atualizar.setInt(2, usuario.getId());
				atualizar.executeUpdate();
				break;
			case 2:
				System.out.println("Informe o novo email");
				String novoEmail = sc.next();
				if (!novoEmail.contains("@")) {
					throw new EmailInvalidoException("Email inválido " + novoEmail);
				}
				atualizar = connection.prepareStatement("UPDATE usuarios SET email = ? WHERE id = ?");
				atualizar.setString(1, novoEmail);
				atualizar.setInt(2, usuario.getId());
				atualizar.executeUpdate();
				break;
			case 3:
				System.out.println("Informe a nova senha");
				String novaSenha = sc.nextLine();
				if (!novaSenha.equals(usuario.getSenha())) {
					throw new senhaIncorretaException("Senha incorreta");
				}
				atualizar = connection.prepareStatement("UPDATE usuarios SET senha = ? WHERE id = ?");
				atualizar.setString(1, novaSenha);
				atualizar.setInt(2, usuario.getId());
				atualizar.executeUpdate();
				break;
			case 4:
				System.out.println("Informe o novo cargo");
				String novoCargo = sc.nextLine();
				atualizar = connection.prepareStatement("UPDATE usuarios SET cargo = ? WHERE id = ?");
				atualizar.setString(1, novoCargo);
				atualizar.setInt(2, usuario.getId());
				atualizar.executeUpdate();
				break;
			default:
				System.out.println("Opção Inválida");
			}
		} catch (SQLException e) {
			System.err.println("Erro ao cadastrar atualizar: " + e.getMessage());
			e.printStackTrace();
		} catch (EmailInvalidoException | senhaIncorretaException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void excluir(Usuario usuario) {
		try (Connection connection = getConnection();
				PreparedStatement excluir = connection.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
			excluir.setInt(1, usuario.getId());
			excluir.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao excluir usuario: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void logar(Usuario usuario) throws Exception {
		try (Connection connection = getConnection();
				PreparedStatement logar = connection
						.prepareStatement("SELECT nome, senha FROM usuarios WHERE nome = ? AND senha = ?")) {
			logar.setString(1, usuario.getNome());
			logar.setString(2, usuario.getSenha());
			try (ResultSet rs = logar.executeQuery()) {
				if (!rs.next()) {
					throw new usuarioOuSenhaIncorretaException("Usuário e/ou senha incorretos");
				} else {
					System.out.println("Login bem-sucedido!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Usuario> consultar() {
		List<Usuario> usuarios = new ArrayList<>();
		try (Connection connection = getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT nome, email, cargo FROM usuarios")) {
			while (rs.next()) {
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				String cargoStr = rs.getString("cargo");
				Usuario usuario = new Usuario(nome, email, null, Cargo.valueOf(cargoStr));
				usuarios.add(usuario);
			}
		} catch (Exception e) {
			System.err.println("Erro ao consultar usuario: " + e.getMessage());
			e.printStackTrace();
		}
		return usuarios;
	}

	@Override
	public boolean EmailJaCadastrado(String email, Connection connection) {
		try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE email = ?")) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

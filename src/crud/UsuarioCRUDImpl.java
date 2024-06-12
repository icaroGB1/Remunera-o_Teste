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
import exceptions.EmailInvalidoException;
import exceptions.EmailJaCadastradoException;
import exceptions.senhaIncorretaException;
import exceptions.usuarioOuSenhaIncorretaException;

public class usuarioCRUDImpl implements usuarioCRUD {
	Statement st = null;
	Connection connection = null;
	ResultSet rs = null;

	public usuarioCRUDImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrar(Usuario usuario) throws Exception {
		PreparedStatement cad = null;
		try {
			connection = DB.getConnection();
			if (EmailJaCadastrado(usuario.getEmail())) {
				throw new EmailJaCadastradoException("Email ja Cadastrado");
			}
			cad = connection.prepareStatement("INSERT INTO usuarios  (name, email, senha)  VALUES (?, ?, ?)");
			cad.setString(1, usuario.getName());
			cad.setString(2, usuario.getEmail());
			cad.setString(3, usuario.getSenha());

			cad.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro ao cadastrar usuário: " + e.getMessage());
		} finally {
			DB.closePreparedStatement(cad);
			DB.closeConnection();
		}
	}

	@Override
	public void atualizar(Usuario usuario) {
		PreparedStatement atualizar = null;
		Scanner sc = new Scanner(System.in);
		try {
			connection = DB.getConnection();
			System.out.println("Informe o que deseja atualizar");
			System.out.println("1-Nome\r\n" + "2-Email\r\n" + "3-senha");
			int op = sc.nextInt();
			sc.nextLine();
			switch (op) {
			case 1:
				System.out.println("Informe o novo nome");
				String novoNome = sc.nextLine();
				atualizar = connection.prepareStatement("UPDATE usuarios SET name = ? WHERE id = ?");
				atualizar.setString(1, novoNome);
				atualizar.setInt(2, usuario.getId());
				atualizar.executeUpdate();
				break;

			case 2:
				System.out.println("Informe o novo email");
				String novoEmail = sc.next();
				if (!novoEmail.contains("@")) {
					throw new EmailInvalidoException("Email invalido " + novoEmail);
				}
				atualizar = connection.prepareStatement("UPDATE usuarios SET email = ? WHERE  + id= ? ");
				atualizar.setNString(1, novoEmail);
				atualizar.setInt(2, usuario.getId());
				atualizar.executeUpdate();
				break;
			case 3:
				System.out.println("Informe a nova senha");
				String novaSenha = sc.nextLine();
				if (!novaSenha.equals(usuario.getSenha())) {
					throw new senhaIncorretaException("Senha Incorreta");
				}
				atualizar = connection.prepareStatement("UPDATE usuarios SET senha = ? WHERE  + id= ? ");
				atualizar.setString(1, novaSenha);
				atualizar.setInt(2, usuario.getId());
				atualizar.executeUpdate();
				break;
			default:
				System.out.println("Opção Invalida");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EmailInvalidoException e) {
			System.out.println("Email invalido");
		} catch (senhaIncorretaException e) {
			System.out.println("Senha incorreta");
		} finally {
			DB.closePreparedStatement(atualizar);
			DB.closeConnection();
			sc.close();
		}
	}

	@Override
	public void excluir(Usuario usuario) {
		PreparedStatement excluir = null;
		try {
			connection = DB.getConnection();
			excluir = connection.prepareStatement("DELETE FROM usuarios WHERE id = ?");
			excluir.setInt(1, usuario.getId());
			excluir.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(excluir);
			DB.closeConnection();
		}
	}

	@Override
	public void logar(Usuario usuario) throws Exception {
		boolean loginSucesso = false;
		PreparedStatement logar = null;
		try {
			connection = DB.getConnection();
			logar = connection
					.prepareStatement("SELECT Name, senha" + " FROM usuarios" + " where name = ? and senha = ?");
			logar.setString(1, usuario.getName());
			logar.setString(2, usuario.getSenha());
			rs = logar.executeQuery();
			if (!rs.next()) {
				throw new usuarioOuSenhaIncorretaException("Usuario e/ou Senha Incorreta");
			} else {
				loginSucesso = true;
				System.out.println("Login bem-sucedido!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(logar);
			DB.closeConnection();
		}

	}

	@Override
	public List<Usuario> consultar() {
		List<Usuario> usuarios = new ArrayList<>();
		try {
			connection = DB.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT name, email  FROM usuarios");
			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString("name"), rs.getString("email"), null);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
		return usuarios;
	}

	@Override
	public boolean EmailJaCadastrado(String email) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean emailExists = false;
		try {
			connection = DB.getConnection();
			ps = connection.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE email = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				emailExists = rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(ps);
			DB.closeConnection();
		}
		return emailExists;
	}
}

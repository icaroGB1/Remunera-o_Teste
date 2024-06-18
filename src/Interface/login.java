package Interface;

import javax.swing.*;

import Conexao.DB;
import Entidades.Usuario;
import crud.usuarioCRUDImpl;
import enums.Cargo;
import exceptions.usuarioOuSenhaIncorretaException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class login {

	private static JFrame frame;
	private static JTextField entryUsername;
	private static JPasswordField entryPassword;
	private static ButtonGroup userTypeGroup;
	private static JRadioButton radioFuncionario;
	private static JRadioButton radioGestor;

	private static usuarioCRUDImpl usuarioCRUD = new usuarioCRUDImpl();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(login::createAndShowGUI);
	}

	private static void createAndShowGUI() {
		frame = new JFrame("Acessar Sistema");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 350);
		centerWindow(frame);

		frame.setLayout(new BorderLayout());

		// Painel do título
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBackground(new Color(52, 152, 219));
		JLabel labelTitle = new JLabel("Acessar Sistema", SwingConstants.CENTER);
		labelTitle.setFont(new Font("Arial", Font.BOLD, 24));
		labelTitle.setForeground(Color.WHITE);
		titlePanel.add(labelTitle, BorderLayout.CENTER);
		frame.add(titlePanel, BorderLayout.NORTH);

		// Painel dos campos de entrada
		JPanel panelInputs = new JPanel(new GridBagLayout());
		panelInputs.setBackground(new Color(236, 240, 241));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		JLabel labelUsername = new JLabel("Nome:");
		c.gridx = 0;
		c.gridy = 0;
		panelInputs.add(labelUsername, c);

		entryUsername = new JTextField(15);
		c.gridx = 1;
		c.gridy = 0;
		panelInputs.add(entryUsername, c);

		JLabel labelPassword = new JLabel("Senha:");
		c.gridx = 0;
		c.gridy = 1;
		panelInputs.add(labelPassword, c);

		entryPassword = new JPasswordField(15);
		c.gridx = 1;
		c.gridy = 1;
		panelInputs.add(entryPassword, c);

		JLabel labelUserType = new JLabel("Tipo de Usuário:");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		panelInputs.add(labelUserType, c);

		userTypeGroup = new ButtonGroup();
		radioFuncionario = new JRadioButton("Funcionário");
		radioFuncionario.setBackground(new Color(236, 240, 241));
		radioGestor = new JRadioButton("Gestor");
		radioGestor.setBackground(new Color(236, 240, 241));
		userTypeGroup.add(radioFuncionario);
		userTypeGroup.add(radioGestor);

		JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelRadios.setBackground(new Color(236, 240, 241));
		panelRadios.add(radioFuncionario);
		panelRadios.add(radioGestor);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		panelInputs.add(panelRadios, c);

		frame.add(panelInputs, BorderLayout.CENTER);

		// Painel dos botões
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelButtons.setBackground(new Color(52, 152, 219));

		JButton buttonLogin = new JButton("Login");
		buttonLogin.setBackground(new Color(41, 128, 185));
		buttonLogin.setForeground(Color.WHITE);
		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		panelButtons.add(buttonLogin);

		JButton buttonRegister = new JButton("Cadastro");
		buttonRegister.setBackground(new Color(39, 174, 96));
		buttonRegister.setForeground(Color.WHITE);
		buttonRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		panelButtons.add(buttonRegister);

		frame.add(panelButtons, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private static void centerWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	private static void login() throws Exception {
		String username = entryUsername.getText();
		String password = new String(entryPassword.getPassword());
		String userType = "";
		if (radioFuncionario.isSelected()) {
			userType = "Funcionario";
		} else if (radioGestor.isSelected()) {
			userType = "Gestor";
		}

		if (!username.isEmpty() && !password.isEmpty() && !userType.isEmpty()) {
			try {
				Usuario usuario = new Usuario(username, "icarowp45@gmail.com", password, Cargo.valueOf(userType));
				usuarioCRUD.logar(usuario);

				switch (userType) {
				case "Funcionario":
					JOptionPane.showMessageDialog(frame, "Bem-vindo, Funcionário " + username);
					break;
				case "Gestor":
					JOptionPane.showMessageDialog(frame, "Olá, Gestor " + username);
					break;
				default:
					JOptionPane.showMessageDialog(frame, "Tipo de usuário inválido", "Erro",
							JOptionPane.WARNING_MESSAGE);
				}

			} catch (usuarioOuSenhaIncorretaException e) {
				JOptionPane.showMessageDialog(frame, "Usuário e/ou senha incorretos", "Erro",
						JOptionPane.WARNING_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Erro ao conectar ao banco de dados", "Erro",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "Erro desconhecido", "Erro", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos e selecione o tipo de usuário",
					"Erro", JOptionPane.WARNING_MESSAGE);
		}
	}

	private static void register() {
		JDialog cadastroDialog = new JDialog(frame, "Cadastro", true);
		cadastroDialog.setSize(400, 350);
		cadastroDialog.getContentPane().setBackground(new Color(52, 152, 219));
		centerWindow(cadastroDialog);

		JPanel panelCadastro = new JPanel(new GridBagLayout());
		panelCadastro.setBackground(new Color(236, 240, 241));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		JLabel labelNome = new JLabel("Nome:");
		c.gridx = 0;
		c.gridy = 0;
		panelCadastro.add(labelNome, c);

		JTextField entryNome = new JTextField(15);
		c.gridx = 1;
		c.gridy = 0;
		panelCadastro.add(entryNome, c);

		JLabel labelEmail = new JLabel("E-mail:");
		c.gridx = 0;
		c.gridy = 2;
		panelCadastro.add(labelEmail, c);

		JTextField entryEmail = new JTextField(15);
		c.gridx = 1;
		c.gridy = 2;
		panelCadastro.add(entryEmail, c);

		JLabel labelCargo = new JLabel("Cargo:");
		c.gridx = 0;
		c.gridy = 3;
		panelCadastro.add(labelCargo, c);

		JComboBox<Cargo> comboCargo = new JComboBox<>(Cargo.values());
		c.gridx = 1;
		c.gridy = 3;
		panelCadastro.add(comboCargo, c);

		JLabel labelSenha = new JLabel("Senha:");
		c.gridx = 0;
		c.gridy = 4;
		panelCadastro.add(labelSenha, c);

		JPasswordField entrySenha = new JPasswordField(15);
		c.gridx = 1;
		c.gridy = 4;
		panelCadastro.add(entrySenha, c);

		JButton buttonCadastrar = new JButton("Cadastrar");
		buttonCadastrar.setBackground(new Color(39, 174, 96));
		buttonCadastrar.setForeground(Color.WHITE);
		buttonCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Código de cadastro aqui
				String nome = entryNome.getText();
				String email = entryEmail.getText();
				String senha = new String(entrySenha.getPassword());
				Cargo cargo = (Cargo) comboCargo.getSelectedItem();
				try {
					Usuario novoUsuario = new Usuario(nome, email, senha, cargo);
					usuarioCRUD.cadastrar(novoUsuario);
					JOptionPane.showMessageDialog(cadastroDialog, "Cadastro realizado com sucesso!");
					cadastroDialog.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		panelCadastro.add(buttonCadastrar, c);

		cadastroDialog.add(panelCadastro);
		cadastroDialog.setVisible(true);
	}
}

package Interface;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Entidades.Produto;
import crud.ProdutoCRUDImpl;
import enums.CategoriaProduto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CadastroDeProdutosPanel extends JPanel {
	private ProdutoCRUDImpl produtoCRUD = new ProdutoCRUDImpl();
	private JTextField txtNomeProduto = new JTextField(20);
	private JTextField txtDescricaoProduto = new JTextField(20);
	private JTextField txtPreco = new JTextField(10);
	private JComboBox<CategoriaProduto> cmbCategoria = new JComboBox<>(CategoriaProduto.values());
	private DefaultListModel<String> produtosListModel = new DefaultListModel<>();
	private JList<String> produtosList = new JList<>(produtosListModel);

	public CadastroDeProdutosPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Panel para os campos e botões
		JPanel inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		// Labels e Campos de Entrada
		JLabel lblNomeProduto = new JLabel("Nome Produto:");
		JLabel lblDescricaoProduto = new JLabel("Descrição do Produto:");
		JLabel lblPreco = new JLabel("Preço:");
		JLabel lblCategoria = new JLabel("Categoria:");

		estilizarLabel(lblNomeProduto);
		estilizarLabel(lblDescricaoProduto);
		estilizarLabel(lblPreco);
		estilizarLabel(lblCategoria);

		// Configuração dos componentes de entrada
		gbc.gridx = 0;
		gbc.gridy = 0;
		inputPanel.add(lblNomeProduto, gbc);

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		inputPanel.add(txtNomeProduto, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		inputPanel.add(lblDescricaoProduto, gbc);

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		inputPanel.add(txtDescricaoProduto, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		inputPanel.add(lblPreco, gbc);

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		inputPanel.add(txtPreco, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		inputPanel.add(lblCategoria, gbc);

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		inputPanel.add(cmbCategoria, gbc);

		// Adicionar o painel de entrada ao norte (topo)
		add(inputPanel, BorderLayout.NORTH);

		// Configuração da lista de produtos
		produtosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		produtosList.setBackground(new Color(240, 248, 255));
		produtosList.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(produtosList);
		scrollPane.setPreferredSize(new Dimension(400, 200));

		// Adicionar a lista de produtos ao centro
		add(scrollPane, BorderLayout.CENTER);

		// Painel de Botões
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		JButton btnCadastrar = new JButton("Cadastrar");
		JButton btnExcluir = new JButton("Excluir");
		JButton btnAtualizar = new JButton("Atualizar");

		estilizarBotao(btnCadastrar);
		estilizarBotao(btnExcluir);
		estilizarBotao(btnAtualizar);

		buttonPanel.add(btnCadastrar);
		buttonPanel.add(btnExcluir);
		buttonPanel.add(btnAtualizar);

		// Adicionar o painel de botões ao sul (rodapé)
		add(buttonPanel, BorderLayout.SOUTH);

		// Configuração do campo txtPreco para aceitar apenas números e um ponto decimal
		((AbstractDocument) txtPreco.getDocument()).setDocumentFilter(new DecimalFilter());

		// Listar os produtos inicialmente
		listarProdutos();

		// Adicionar ActionListener para o botão Cadastrar
		btnCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cadastrarProduto();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(CadastroDeProdutosPanel.this,
							"Erro ao cadastrar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Adicionar ActionListener para o botão Excluir
		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					excluirProduto();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Adicionar ActionListener para o botão Atualizar
		btnAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					atualizarProduto();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	private void estilizarLabel(JLabel label) {
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(new Color(50, 50, 50));
	}

	private void estilizarBotao(JButton button) {
		button.setBackground(new Color(30, 144, 255));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setFont(new Font("Arial", Font.BOLD, 14));
	}

	private void listarProdutos() {
		List<Produto> produtos = produtoCRUD.consultar();
		produtosListModel.clear();
		for (Produto p : produtos) {
			String produtoInfo = String.format("ID: %d | Nome: %s | Categoria: %s | Preço: R$ %.2f", p.getId(),
					p.getNome(), p.getCategoria().name(), p.getPreco());
			produtosListModel.addElement(produtoInfo);
		}
	}

	private void cadastrarProduto() throws Exception {
		String nomeProduto = txtNomeProduto.getText().trim();
		String descricaoProduto = txtDescricaoProduto.getText().trim();
		String precoTexto = txtPreco.getText().replace(",", ".");
		BigDecimal preco = new BigDecimal(precoTexto);
		CategoriaProduto categoria = (CategoriaProduto) cmbCategoria.getSelectedItem();

		Produto novoProduto = new Produto(nomeProduto, descricaoProduto, categoria, preco);

		produtoCRUD.cadastrar(novoProduto);

		JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");

		limparCampos();
		listarProdutos();
	}

	private void excluirProduto() throws Exception {
		int selectedIndex = produtosList.getSelectedIndex();
		if (selectedIndex != -1) {
			String produtoInfo = produtosListModel.getElementAt(selectedIndex);
			int idProduto = Integer.parseInt(produtoInfo.split(" ")[1]);

			Produto produto = produtoCRUD.consultarPorId(idProduto);

			if (produto != null) {
				try {
					produtoCRUD.excluir(produto);
					JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
					listarProdutos();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + e.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
		}
	}

	private void atualizarProduto() throws Exception {
		int selectedIndex = produtosList.getSelectedIndex();
		if (selectedIndex != -1) {
			String produtoInfo = produtosListModel.getElementAt(selectedIndex);
			int idProduto = Integer.parseInt(produtoInfo.split(" ")[1]);

			Produto produto = produtoCRUD.consultarPorId(idProduto);

			if (produto != null) {
				try {
					String escolha = JOptionPane.showInputDialog(this,
							"Informe o que deseja atualizar:\n1 - Nome\n2 - Descrição\n3 - Categoria\n4 - Preço");

					if (escolha != null) {
						int op = Integer.parseInt(escolha);

						switch (op) {
						case 1:
							String novoNome = JOptionPane.showInputDialog(this, "Informe o novo nome:");
							produto.setNome(novoNome);
							break;
						case 2:
							String novaDescricao = JOptionPane.showInputDialog(this, "Informe a nova descrição:");
							produto.setDescricao(novaDescricao);
							break;
						case 3:
							CategoriaProduto novaCategoria = (CategoriaProduto) JOptionPane.showInputDialog(this,
									"Escolha a nova categoria:", "Atualizar Categoria", JOptionPane.PLAIN_MESSAGE, null,
									CategoriaProduto.values(), produto.getCategoria());
							produto.setCategoria(novaCategoria);
							break;
						case 4:
							String novoPrecoTexto = JOptionPane.showInputDialog(this, "Informe o novo preço:");
							BigDecimal novoPreco = new BigDecimal(novoPrecoTexto.replace(",", "."));
							produto.setPreco(novoPreco);
							break;
						default:
							JOptionPane.showMessageDialog(this, "Opção inválida", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}

						// Atualiza o produto diretamente no banco de dados
						produtoCRUD.atualizar(produto);
						JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
						listarProdutos();
					}
				} catch (NumberFormatException | NullPointerException e) {
					JOptionPane.showMessageDialog(this, "Opção inválida", "Erro", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + e.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Produto não encontrado para atualização.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.");
		}
	}

	private void limparCampos() {
		txtNomeProduto.setText("");
		txtDescricaoProduto.setText("");
		txtPreco.setText("");
		cmbCategoria.setSelectedIndex(0);
	}

	private class DecimalFilter extends DocumentFilter {
		@Override
		public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
				throws BadLocationException {
			if (isValidDecimal(text)) {
				super.insertString(fb, offset, text, attr);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {
			if (isValidDecimal(text)) {
				super.replace(fb, offset, length, text, attrs);
			}
		}

		private boolean isValidDecimal(String text) {
			return text.matches("\\d*(\\.\\d*)?");
		}
	}
}

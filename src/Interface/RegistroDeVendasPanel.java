package Interface;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Entidades.Venda;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegistroDeVendasPanel extends JPanel {
	private JTextField txtNomeCliente = new JTextField(20);
	private JTextField txtProdutoVendido = new JTextField(20);
	private JTextField txtQuantidade = new JTextField(10);
	private JTextField txtPrecoUnitario = new JTextField(10);
	private JTextField txtDataVenda = new JTextField(10);
	private JTextField txtTotalVenda = new JTextField(10);
	private DefaultListModel<String> vendasListModel = new DefaultListModel<>();
	private JList<String> vendasList = new JList<>(vendasListModel);
	private List<Venda> vendas = new ArrayList<>();

	public RegistroDeVendasPanel() {
		setLayout(new BorderLayout(10, 10));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel inputPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		addInputFields(inputPanel, gbc);
		add(inputPanel, BorderLayout.CENTER);

		add(createListPanel(), BorderLayout.EAST);

		JPanel buttonPanel = createButtonPanel();
		add(buttonPanel, BorderLayout.SOUTH);

		// Configure o txtPrecoUnitario para aceitar apenas números e um ponto decimal
		((AbstractDocument) txtPrecoUnitario.getDocument()).setDocumentFilter(new DecimalFilter());

		// Preenche a data de venda com a data atual
		txtDataVenda.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

	private void addInputFields(JPanel inputPanel, GridBagConstraints gbc) {
		JLabel lblNomeCliente = createLabel("Nome Cliente:");
		JLabel lblProdutoVendido = createLabel("Produto Vendido:");
		JLabel lblQuantidade = createLabel("Quantidade:");
		JLabel lblPrecoUnitario = createLabel("Preço Unitário:");
		JLabel lblDataVenda = createLabel("Data de Venda:");
		JLabel lblTotalVenda = createLabel("Total Venda:");

		gbc.gridx = 0;
		gbc.gridy = 0;
		inputPanel.add(lblNomeCliente, gbc);

		gbc.gridx = 1;
		inputPanel.add(txtNomeCliente, gbc);

		gbc.gridy++;
		inputPanel.add(lblProdutoVendido, gbc);

		gbc.gridx = 1;
		inputPanel.add(txtProdutoVendido, gbc);

		gbc.gridy++;
		inputPanel.add(lblQuantidade, gbc);

		gbc.gridx = 1;
		inputPanel.add(txtQuantidade, gbc);

		gbc.gridy++;
		inputPanel.add(lblPrecoUnitario, gbc);

		gbc.gridx = 1;
		inputPanel.add(txtPrecoUnitario, gbc);

		gbc.gridy++;
		inputPanel.add(lblDataVenda, gbc);

		gbc.gridx = 1;
		inputPanel.add(txtDataVenda, gbc);

		gbc.gridy++;
		inputPanel.add(lblTotalVenda, gbc);

		gbc.gridx = 1;
		inputPanel.add(txtTotalVenda, gbc);
	}

	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(new Color(50, 50, 50));
		return label;
	}

	private JPanel createListPanel() {
		JPanel listPanel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(vendasList);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		vendasList.setBackground(new Color(240, 248, 255));
		vendasList.setFont(new Font("Arial", Font.PLAIN, 14));
		listPanel.add(scrollPane, BorderLayout.CENTER);
		listPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		return listPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		JButton btnRegistrar = new JButton("Registrar Venda");
		JButton btnLimpar = new JButton("Limpar");

		estilizarBotao(btnRegistrar);
		estilizarBotao(btnLimpar);

		btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarVenda();
			}
		});

		btnLimpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});

		buttonPanel.add(btnRegistrar);
		buttonPanel.add(btnLimpar);
		return buttonPanel;
	}

	private void estilizarBotao(JButton button) {
		button.setBackground(new Color(30, 144, 255));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setFont(new Font("Arial", Font.BOLD, 14));
	}

	private void registrarVenda() {
		try {
			String nomeCliente = txtNomeCliente.getText();
			String produtoVendido = txtProdutoVendido.getText();
			int quantidade = Integer.parseInt(txtQuantidade.getText());
			BigDecimal precoUnitario = new BigDecimal(txtPrecoUnitario.getText().replace(",", "."));
			LocalDate dataVenda = LocalDate.parse(txtDataVenda.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			BigDecimal totalVenda = precoUnitario.multiply(new BigDecimal(quantidade));

			Venda venda = new Venda();
			vendas.add(venda);
			JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");
			listarVendas();
			limparCampos();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao registrar venda: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limparCampos() {
		txtNomeCliente.setText("");
		txtProdutoVendido.setText("");
		txtQuantidade.setText("");
		txtPrecoUnitario.setText("");
		txtDataVenda.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtTotalVenda.setText("");
	}

	private void listarVendas() {
		vendasListModel.clear();
		for (Venda v : vendas) {
		}
	}

	class DecimalFilter extends DocumentFilter {
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {
			if (isValidDecimal(string)) {
				super.insertString(fb, offset, string, attr);
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
			return text.matches("[0-9]*\\.?[0-9]*");
		}
	}
}



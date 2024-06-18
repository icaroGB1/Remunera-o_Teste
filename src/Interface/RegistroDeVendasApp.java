                  package Interface;

import javax.swing.*;
import javax.swing.text.*;

import Entidades.Venda;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RegistroDeVendasApp extends JFrame {
    private JTextField txtNomeCliente = new JTextField(20);
    private JTextField txtProdutoVendido = new JTextField(20);
    private JTextField txtQuantidade = new JTextField(10);
    private JTextField txtPrecoUnitario = new JTextField(10);
    private JTextField txtDataVenda = new JTextField(10);
    private JTextField txtTotalVenda = new JTextField(10);
    private DefaultListModel<String> vendasListModel = new DefaultListModel<>();
    private JList<String> vendasList = new JList<>(vendasListModel);

    private List<Venda> vendas = new ArrayList<>();

    public RegistroDeVendasApp() {
        setTitle("Registro de Vendas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNomeCliente = new JLabel("Nome do Cliente:");
        JLabel lblProdutoVendido = new JLabel("Produto Vendido:");
        JLabel lblQuantidade = new JLabel("Quantidade:");
        JLabel lblPrecoUnitario = new JLabel("Preço Unitário:");
        JLabel lblDataVenda = new JLabel("Data da Venda:");
        JLabel lblTotalVenda = new JLabel("Total da Venda:");

        estilizarLabel(lblNomeCliente);
        estilizarLabel(lblProdutoVendido);
        estilizarLabel(lblQuantidade);
        estilizarLabel(lblPrecoUnitario);
        estilizarLabel(lblDataVenda);
        estilizarLabel(lblTotalVenda);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNomeCliente, gbc);

        gbc.gridx = 1;
        panel.add(txtNomeCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblProdutoVendido, gbc);

        gbc.gridx = 1;
        panel.add(txtProdutoVendido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblQuantidade, gbc);

        gbc.gridx = 1;
        panel.add(txtQuantidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblPrecoUnitario, gbc);

        gbc.gridx = 1;
        panel.add(txtPrecoUnitario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblDataVenda, gbc);

        gbc.gridx = 1;
        panel.add(txtDataVenda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblTotalVenda, gbc);

        gbc.gridx = 1;
        panel.add(txtTotalVenda, gbc);
        txtTotalVenda.setEditable(false); // Campo calculado, não editável

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        gbc.insets = new Insets(5, 20, 5, 5);
        JScrollPane scrollPane = new JScrollPane(vendasList);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        vendasList.setBackground(new Color(240, 248, 255));
        vendasList.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(scrollPane, gbc);

        JButton btnRegistrar = new JButton("Registrar Venda");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnListar = new JButton("Listar Vendas");

        estilizarBotao(btnRegistrar);
        estilizarBotao(btnLimpar);
        estilizarBotao(btnListar);

        btnRegistrar.addActionListener(e -> registrarVenda());

        btnLimpar.addActionListener(e -> limparCampos());

        btnListar.addActionListener(e -> listarVendas());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnListar);
        buttonPanel.setBackground(Color.WHITE);
        panel.add(buttonPanel, gbc);

        add(panel);

        // Configure os campos txtQuantidade e txtPrecoUnitario para aceitar apenas números e um ponto decimal
        ((AbstractDocument) txtQuantidade.getDocument()).setDocumentFilter(new DecimalFilter());
        ((AbstractDocument) txtPrecoUnitario.getDocument()).setDocumentFilter(new DecimalFilter());

        // Formato padrão para a data
        txtDataVenda.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

    private void registrarVenda() {
        try {
            // Verificar se os campos estão preenchidos
            if (txtNomeCliente.getText().trim().isEmpty() || txtProdutoVendido.getText().trim().isEmpty() ||
                    txtQuantidade.getText().trim().isEmpty() || txtPrecoUnitario.getText().trim().isEmpty() ||
                    txtDataVenda.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nomeCliente = txtNomeCliente.getText();
            String produtoVendido = txtProdutoVendido.getText();
            int quantidade;
            BigDecimal precoUnitario;

            // Verificar a quantidade
            try {
                quantidade = Integer.parseInt(txtQuantidade.getText());
                if (quantidade <= 0) {
                    throw new NumberFormatException("A quantidade deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar o preço unitário
            try {
                precoUnitario = new BigDecimal(txtPrecoUnitario.getText());
                if (precoUnitario.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new NumberFormatException("O preço unitário deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Preço unitário inválido: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar a data de venda
            LocalDate dataVenda;
            try {
                dataVenda = LocalDate.parse(txtDataVenda.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Data de venda inválida: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcular o total da venda
            BigDecimal totalVenda = precoUnitario.multiply(new BigDecimal(quantidade));
            txtTotalVenda.setText(totalVenda.toString());

            Venda venda = new Venda();
            vendas.add(venda);

            JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");
            listarVendas(); // Atualiza a lista de vendas após registrar uma nova
            limparCampos(); // Limpa os campos após registrar
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
            String vendaInfo = String.format(
                    "Cliente: %s | Produto: %s | Quantidade: %d | Preço Unitário: R$ %.2f | Data: %s | Total: R$ %.2f");
            vendasListModel.addElement(vendaInfo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Define o Look and Feel padrão para o sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new RegistroDeVendasApp().setVisible(true);
        });
    }

    static class DecimalFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isValidDecimal(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isValidDecimal(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidDecimal(String text) {
            return text.matches("[0-9]*\\.?[0-9]*");
        }
    }
}

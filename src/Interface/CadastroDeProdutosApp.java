package Interface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import java.util.List;

public class CadastroDeProdutosApp extends JFrame {
    private ProdutoCRUDImpl produtoCRUD = new ProdutoCRUDImpl();
    private JTextField txtNomeProduto = new JTextField(20);
    private JTextField txtDescricaoProduto = new JTextField(20);
    private JTextField txtPreco = new JTextField(10);
    private JComboBox<CategoriaProduto> cmbCategoria = new JComboBox<>(CategoriaProduto.values());
    private DefaultListModel<String> produtosListModel = new DefaultListModel<>();
    private JList<String> produtosList = new JList<>(produtosListModel);

    public CadastroDeProdutosApp() {
        setTitle("Cadastro de Produtos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNomeProduto = new JLabel("Nome Produto:");
        JLabel lblDescricaoProduto = new JLabel("Descrição Produto:");
        JLabel lblPreco = new JLabel("Preço:");
        JLabel lblCategoria = new JLabel("Categoria:");

        estilizarLabel(lblNomeProduto);
        estilizarLabel(lblDescricaoProduto);
        estilizarLabel(lblPreco);
        estilizarLabel(lblCategoria);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNomeProduto, gbc);

        gbc.gridx = 1;
        panel.add(txtNomeProduto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblDescricaoProduto, gbc);

        gbc.gridx = 1;
        panel.add(txtDescricaoProduto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblPreco, gbc);

        gbc.gridx = 1;
        panel.add(txtPreco, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblCategoria, gbc);

        gbc.gridx = 1;
        panel.add(cmbCategoria, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.insets = new Insets(5, 20, 5, 5);
        JScrollPane scrollPane = new JScrollPane(produtosList);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        produtosList.setBackground(new Color(230, 240, 255));
        produtosList.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(scrollPane, gbc);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnAtualizar = new JButton("Atualizar");

        estilizarBotao(btnCadastrar);
        estilizarBotao(btnExcluir);
        estilizarBotao(btnAtualizar);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarProduto();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    excluirProduto();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarProduto();
            }
        });

        produtosList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    carregarProdutoSelecionado();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCadastrar);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);
        buttonPanel.setBackground(Color.WHITE);
        panel.add(buttonPanel, gbc);

        listarProdutos();

        add(panel);

        ((AbstractDocument) txtPreco.getDocument()).setDocumentFilter(new DecimalFilter());
    }

    private void estilizarLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(30, 144, 255));
    }

    private void estilizarBotao(JButton button) {
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void cadastrarProduto() {
        try {
            String nomeProduto = txtNomeProduto.getText();
            String descricaoProduto = txtDescricaoProduto.getText();
            String precoTexto = txtPreco.getText().replaceAll("[,.]", "");
            BigDecimal preco = new BigDecimal(precoTexto);
            CategoriaProduto categoria = (CategoriaProduto) cmbCategoria.getSelectedItem();
            Produto produto = new Produto(nomeProduto, descricaoProduto, categoria, preco);
            produtoCRUD.cadastrar(produto);
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            listarProdutos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirProduto() throws Exception {
        int selectedIndex = produtosList.getSelectedIndex();
        if (selectedIndex != -1) {
            String produtoInfo = produtosListModel.getElementAt(selectedIndex);
            int idProduto = Integer.parseInt(produtoInfo.split("\\|")[0].split(":")[1].trim());
            Produto produto = new Produto();
            produto.setId(idProduto);
            try {
                produtoCRUD.excluir(produto);
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                listarProdutos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
        }
    }

    private void atualizarProduto() {
        int selectedIndex = produtosList.getSelectedIndex();
        if (selectedIndex != -1) {
            String produtoInfo = produtosListModel.getElementAt(selectedIndex);
            int idProduto = Integer.parseInt(produtoInfo.split("\\|")[0].split(":")[1].trim());
            try {
                Produto produto = new Produto();
                produto.setId(idProduto);
                produto.setNome(txtNomeProduto.getText());
                produto.setDescricao(txtDescricaoProduto.getText());
                String precoTexto = txtPreco.getText().replaceAll("[,.]", "");
                produto.setPreco(new BigDecimal(precoTexto));
                produto.setCategoria((CategoriaProduto) cmbCategoria.getSelectedItem());
                produtoCRUD.atualizar(produto);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                listarProdutos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.");
        }
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

    private void carregarProdutoSelecionado() {
        int selectedIndex = produtosList.getSelectedIndex();
        if (selectedIndex != -1) {
            String produtoInfo = produtosListModel.getElementAt(selectedIndex);
            int idProduto = Integer.parseInt(produtoInfo.split("\\|")[0].split(":")[1].trim());
            Produto produto = produtoCRUD.consultar().stream().filter(p -> p.getId() == idProduto).findFirst()
                    .orElse(null);
            if (produto != null) {
                txtNomeProduto.setText(produto.getNome());
                txtDescricaoProduto.setText(produto.getDescricao());
                txtPreco.setText(produto.getPreco().toString());
                cmbCategoria.setSelectedItem(produto.getCategoria());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new CadastroDeProdutosApp().setVisible(true);
            }
        });
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

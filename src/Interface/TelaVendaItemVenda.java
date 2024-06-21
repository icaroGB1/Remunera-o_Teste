// Código principal, ajustado e revisado
package Interface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import Entidades.ItemVenda;
import Entidades.Produto;
import Entidades.Venda;
import crud.ProdutoCRUDImpl;
import crud.VendaCRUDimpl;

public class TelaVendaItemVenda extends JFrame {

	private List<Venda> vendas;
	private DefaultTableModel tabelaModelo;

	private JTextField campoIdFuncionario;
	private JTextField campoTotal;
	private JButton botaoAdicionarVenda;
	private JTable tabelaVendas;

	private JTextField campoIdProduto;
	private JTextField campoQuantidade;
	private JButton botaoAdicionarItem;
	private JTable tabelaItens;

	public TelaVendaItemVenda() {
		super("Cadastro de Vendas e Itens de Venda");

		vendas = new ArrayList<>();

		// Inicializa componentes da interface
		campoIdFuncionario = new JTextField(5);
		campoTotal = new JTextField(10);
		botaoAdicionarVenda = new JButton("Adicionar Venda");

		campoIdProduto = new JTextField(5);
		campoQuantidade = new JTextField(5);
		botaoAdicionarItem = new JButton("Adicionar Item");

		// Configuração da tabela de vendas
		tabelaModelo = new DefaultTableModel();
		tabelaModelo.addColumn("ID");
		tabelaModelo.addColumn("Data"); // Ainda presente na tabela de vendas, ajustar conforme necessidade
		tabelaModelo.addColumn("ID Funcionário");
		tabelaModelo.addColumn("Total");

		tabelaVendas = new JTable(tabelaModelo);
		JScrollPane scrollPaneVendas = new JScrollPane(tabelaVendas);

		// Configuração da tabela de itens de venda
		DefaultTableModel tabelaModeloItens = new DefaultTableModel();
		tabelaModeloItens.addColumn("ID Produto");
		tabelaModeloItens.addColumn("Quantidade");

		tabelaItens = new JTable(tabelaModeloItens);
		JScrollPane scrollPaneItens = new JScrollPane(tabelaItens);

		// Painel de vendas
		JPanel painelVendas = new JPanel();
		painelVendas.setBorder(BorderFactory.createTitledBorder("Venda"));
		painelVendas.setLayout(new BorderLayout());

		JPanel subPainelVenda = new JPanel(new FlowLayout());
		subPainelVenda.add(new JLabel("ID Funcionário:"));
		subPainelVenda.add(campoIdFuncionario);
		subPainelVenda.add(new JLabel("Total:"));
		subPainelVenda.add(campoTotal);
		subPainelVenda.add(botaoAdicionarVenda);

		JPanel subPainelListaVendas = new JPanel(new BorderLayout());
		subPainelListaVendas.add(new JLabel("Vendas Cadastradas:"), BorderLayout.NORTH);
		subPainelListaVendas.add(scrollPaneVendas, BorderLayout.CENTER);

		painelVendas.add(subPainelVenda, BorderLayout.NORTH);
		painelVendas.add(subPainelListaVendas, BorderLayout.CENTER);

		// Painel de itens de venda
		JPanel painelItens = new JPanel();
		painelItens.setBorder(BorderFactory.createTitledBorder("Item de Venda"));
		painelItens.setLayout(new BorderLayout());

		JPanel subPainelItem = new JPanel(new FlowLayout());
		subPainelItem.add(new JLabel("ID Produto:"));
		subPainelItem.add(campoIdProduto);
		subPainelItem.add(new JLabel("Quantidade:"));
		subPainelItem.add(campoQuantidade);
		subPainelItem.add(botaoAdicionarItem);

		JPanel subPainelListaItens = new JPanel(new BorderLayout());
		subPainelListaItens.add(new JLabel("Itens de Venda da Venda Selecionada:"), BorderLayout.NORTH);
		subPainelListaItens.add(scrollPaneItens, BorderLayout.CENTER);

		painelItens.add(subPainelItem, BorderLayout.NORTH);
		painelItens.add(subPainelListaItens, BorderLayout.CENTER);

		// Layout principal da janela
		setLayout(new GridLayout(1, 2));
		add(painelVendas);
		add(painelItens);

		// Configuração de eventos
		botaoAdicionarVenda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					adicionarVenda();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		botaoAdicionarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					adicionarItemVenda();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Configurações do frame
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void adicionarVenda() throws Exception {
		try {
			VendaCRUDimpl vendaCRUD = new VendaCRUDimpl();
			int idFuncionario = Integer.parseInt(campoIdFuncionario.getText().trim());
			BigDecimal total = new BigDecimal(campoTotal.getText().trim());

			Venda venda = new Venda(idFuncionario, total); // Data será definida automaticamente

			vendaCRUD.cadastrar(venda, null, new ArrayList<>());

			vendas.add(venda);
			atualizarTabelaVendas();
			limparCamposVenda();

			// Feedback visual
			campoIdFuncionario.setBackground(UIManager.getColor("TextField.background"));
			campoTotal.setBackground(UIManager.getColor("TextField.background"));

		} catch (NumberFormatException | NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Erro ao adicionar venda: " + e.getMessage(), "Erro de Cadastro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void adicionarItemVenda() throws Exception {
		int vendaSelecionada = tabelaVendas.getSelectedRow();
		if (vendaSelecionada == -1) {
			JOptionPane.showMessageDialog(this, "Selecione uma venda para adicionar itens.", "Seleção Necessária",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			int idProduto = Integer.parseInt(campoIdProduto.getText().trim());
			int quantidade = Integer.parseInt(campoQuantidade.getText().trim());

			ItemVenda itemVenda = new ItemVenda();
			ProdutoCRUDImpl produtoCRUD = new ProdutoCRUDImpl();
			Produto produto = produtoCRUD.consultarPorId(idProduto);

			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(quantidade);
			itemVenda.calcularSubtotal();

			Venda venda = vendas.get(vendaSelecionada);
			venda.adicionarItem(itemVenda);
			venda.calcularTotal();

			atualizarTabelaItens(vendaSelecionada);
			limparCamposItemVenda();

			// Feedback visual
			campoIdProduto.setBackground(UIManager.getColor("TextField.background"));
			campoQuantidade.setBackground(UIManager.getColor("TextField.background"));

		} catch (NumberFormatException | NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Erro ao adicionar item de venda: " + e.getMessage(),
					"Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void atualizarTabelaVendas() {
		tabelaModelo.setRowCount(0);
		for (Venda venda : vendas) {
			tabelaModelo.addRow(
					new Object[] { venda.getId(), venda.getData(), venda.getIdFuncionario(), venda.getTotal() });
		}
	}

	private void atualizarTabelaItens(int indiceVenda) {
		DefaultTableModel tabelaModeloItens = (DefaultTableModel) tabelaItens.getModel();
		tabelaModeloItens.setRowCount(0);

		for (ItemVenda itemVenda : vendas.get(indiceVenda).getItens()) {
			tabelaModeloItens.addRow(new Object[] { itemVenda.getIdProduto(), itemVenda.getQuantidade() });
		}
	}

	private void limparCamposVenda() {
		campoIdFuncionario.setText("");
		campoTotal.setText("");
	}

	private void limparCamposItemVenda() {
		campoIdProduto.setText("");
		campoQuantidade.setText("");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// Aplicando um look and feel mais moderno (opcional)
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				new TelaVendaItemVenda();
			}
		});
	}
}

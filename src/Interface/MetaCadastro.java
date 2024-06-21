package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Entidades.Meta;
import Entidades.Produto;
import crud.MetaCRUDimpl;

public class MetaCadastro extends javax.swing.JFrame {
	private javax.swing.JButton jButtonCadastrar;
	private javax.swing.JButton jButtonAtualizar;
	private javax.swing.JButton jButtonExcluir;
	private javax.swing.JButton jButtonConsultar;
	private javax.swing.JTextField jTextFieldDescricao;
	private javax.swing.JTextField jTextFieldValorMeta;
	private javax.swing.JTextField jTextFieldProdutoId;
	private javax.swing.JTextField jTextFieldFuncionarioId;
	private MetaCRUDimpl metaCRUDimpl;

	public MetaCadastro() {
		metaCRUDimpl = new MetaCRUDimpl();
		initComponents();
	}

	private void initComponents() {
		jButtonCadastrar = new javax.swing.JButton();
		jButtonAtualizar = new javax.swing.JButton();
		jButtonExcluir = new javax.swing.JButton();
		jButtonConsultar = new javax.swing.JButton();
		jTextFieldDescricao = new javax.swing.JTextField();
		jTextFieldValorMeta = new javax.swing.JTextField();
		jTextFieldProdutoId = new javax.swing.JTextField();
		jTextFieldFuncionarioId = new javax.swing.JTextField();
		JFrame frame = new JFrame("Minha Janela");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jButtonCadastrar.setText("Cadastrar");
		jButtonAtualizar.setText("Atualizar");
		jButtonExcluir.setText("Excluir");
		jButtonConsultar.setText("Consultar");

		jButtonCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cadastrarActionPerformed(evt);
			}
		});

		jButtonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				atualizarActionPerformed(evt);
			}
		});

		jButtonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				excluirActionPerformed(evt);
			}
		});

		jButtonConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				consultarActionPerformed(evt);
			}
		});

		JLabel jLabelDescricao = new JLabel("Descrição:");
		JLabel jLabelValorMeta = new JLabel("Valor Meta:");
		JLabel jLabelProdutoId = new JLabel("Produto ID:");
		JLabel jLabelFuncionarioId = new JLabel("Funcionário ID:");

		jTextFieldDescricao.setText("");
		jTextFieldValorMeta.setText("");
		jTextFieldProdutoId.setText("");
		jTextFieldFuncionarioId.setText("");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(30, 30,
						30)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jLabelDescricao)
						.addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabelValorMeta)
						.addComponent(jTextFieldValorMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabelProdutoId)
						.addComponent(jTextFieldProdutoId, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabelFuncionarioId)
						.addComponent(jTextFieldFuncionarioId, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jButtonCadastrar).addComponent(jButtonAtualizar).addComponent(jButtonExcluir)
						.addComponent(jButtonConsultar))
				.addContainerGap(25, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(30, 30, 30).addComponent(jLabelDescricao)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabelValorMeta)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jTextFieldValorMeta, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabelProdutoId)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jTextFieldProdutoId, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabelFuncionarioId)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jTextFieldFuncionarioId, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButtonCadastrar)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButtonAtualizar)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButtonExcluir)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButtonConsultar)
				.addContainerGap(30, Short.MAX_VALUE)));

		pack();
	}

	private void cadastrarActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			String descricao = jTextFieldDescricao.getText();
			BigDecimal valorMeta = new BigDecimal(jTextFieldValorMeta.getText());

			int produtoId;
			int funcionarioId;

			try {
				produtoId = Integer.parseInt(jTextFieldProdutoId.getText());
				funcionarioId = Integer.parseInt(jTextFieldFuncionarioId.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Erro: Produto ID e Funcionário ID devem ser números inteiros.");
				return; // Encerra o método sem prosseguir, pois não é possível continuar sem IDs
						// válidos
			}

			Meta meta = new Meta();
			meta.setDescricao(descricao);
			meta.setValorMeta(valorMeta);

			Produto produto = new Produto();
			produto.setId(produtoId);
			meta.setProduto(produto);

			meta.setFuncionarioId(funcionarioId);

			metaCRUDimpl.cadastrar(meta);

			JOptionPane.showMessageDialog(this, "Meta cadastrada com sucesso!");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Erro: Valor Meta deve ser um número válido.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao cadastrar meta: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void atualizarActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			int idMeta = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite o ID da meta a ser atualizada:"));
			String descricao = jTextFieldDescricao.getText();
			BigDecimal valorMeta = new BigDecimal(jTextFieldValorMeta.getText());
			BigDecimal valorAtingido = BigDecimal.ZERO; // ou obter de outro campo se necessário

			int produtoId;
			int funcionarioId;

			try {
				produtoId = Integer.parseInt(jTextFieldProdutoId.getText());
				funcionarioId = Integer.parseInt(jTextFieldFuncionarioId.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Erro: Produto ID e Funcionário ID devem ser números inteiros.");
				return;
			}

			Meta meta = new Meta();
			meta.setId(idMeta);
			meta.setDescricao(descricao);
			meta.setValorMeta(valorMeta);
			meta.setValorAtingido(valorAtingido);

			Produto produto = new Produto();
			produto.setId(produtoId);
			meta.setProduto(produto);

			meta.setFuncionarioId(funcionarioId);

			metaCRUDimpl.atualizar(meta);

			JOptionPane.showMessageDialog(this, "Meta atualizada com sucesso!");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Erro: Valor Meta, Produto ID e Funcionário ID devem ser números válidos.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao atualizar meta: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void excluirActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			int metaId = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite o ID da meta a ser excluída:"));
			metaCRUDimpl.excluir(metaId);
			JOptionPane.showMessageDialog(this, "Meta excluída com sucesso!");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Erro: ID da Meta deve ser um número válido.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao excluir meta: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void consultarActionPerformed(java.awt.event.ActionEvent evt) {
		JFrame consultaFrame = new JFrame("Consulta de Metas");
		consultaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		consultaFrame.setSize(400, 300);

		String[] options = { "Consultar por ID", "Consultar por Funcionário", "Consultar todas" };
		int choice = JOptionPane.showOptionDialog(consultaFrame, "Escolha uma opção de consulta", "Consultar Metas",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		switch (choice) {
		case 0:
			try {
				int metaId = Integer.parseInt(JOptionPane.showInputDialog(consultaFrame, "Digite o ID da meta:"));
				Meta meta = metaCRUDimpl.consultarPorId(metaId);
				if (meta != null) {
					JOptionPane.showMessageDialog(consultaFrame, "Meta encontrada:\n" + meta.toString());
				} else {
					JOptionPane.showMessageDialog(consultaFrame, "Meta não encontrada");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(consultaFrame, "Erro: ID da Meta deve ser um número válido.");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(consultaFrame, "Erro ao consultar meta: " + e.getMessage());
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				int funcionarioId = Integer
						.parseInt(JOptionPane.showInputDialog(consultaFrame, "Digite o ID do funcionário:"));
				List<Meta> metasFuncionario = metaCRUDimpl.consultarPorFuncionario(funcionarioId);
				if (!metasFuncionario.isEmpty()) {
					StringBuilder sb = new StringBuilder("Metas encontradas:\n");
					for (Meta m : metasFuncionario) {
						sb.append(m.toString()).append("\n");
					}
					JOptionPane.showMessageDialog(consultaFrame, sb.toString());
				} else {
					JOptionPane.showMessageDialog(consultaFrame, "Nenhuma meta encontrada para o funcionário");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(consultaFrame, "Erro: ID do Funcionário deve ser um número válido.");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(consultaFrame,
						"Erro ao consultar metas do funcionário: " + e.getMessage());
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				List<Meta> todasMetas = metaCRUDimpl.consultarTodas();
				if (!todasMetas.isEmpty()) {
					StringBuilder sb = new StringBuilder("Todas as metas:\n");
					for (Meta m : todasMetas) {
						sb.append(m.toString()).append("\n");
					}
					JOptionPane.showMessageDialog(consultaFrame, sb.toString());
				} else {
					JOptionPane.showMessageDialog(consultaFrame, "Nenhuma meta encontrada");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(consultaFrame, "Erro ao consultar todas as metas: " + e.getMessage());
				e.printStackTrace();
			}
			break;
		}

		consultaFrame.setVisible(true);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MetaCadastro().setVisible(true);
			}
		});
	}

}

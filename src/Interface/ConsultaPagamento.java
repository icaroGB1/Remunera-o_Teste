package Interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Entidades.Pagamento;
import crud.PagamentoCRUDimpl;

public class ConsultaPagamento extends JFrame {

	private JTextField campoFuncionario;
	private JTextArea areaResultado;
	private JButton botaoConsultar;
	private PagamentoCRUDimpl pagamentoCRUD;
	JFrame frame = new JFrame("Minha Janela");

	public ConsultaPagamento() {
		super("Consulta de Pagamentos por Funcionário");

		pagamentoCRUD = new PagamentoCRUDimpl();

		// Inicializa componentes
		campoFuncionario = new JTextField(10);
		areaResultado = new JTextArea(10, 30);
		botaoConsultar = new JButton("Consultar");

		// Define layout do frame
		setLayout(new BorderLayout());

		// Painel para o campo de entrada e botão
		JPanel painelEntrada = new JPanel();
		painelEntrada.add(new JLabel("ID do Funcionário: "));
		painelEntrada.add(campoFuncionario);
		painelEntrada.add(botaoConsultar);

		add(painelEntrada, BorderLayout.NORTH);
		add(new JScrollPane(areaResultado), BorderLayout.CENTER);

		botaoConsultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				areaResultado.setText("");

				String idFuncionarioStr = campoFuncionario.getText().trim();
				if (!idFuncionarioStr.isEmpty()) {
					try {
						int idFuncionario = Integer.parseInt(idFuncionarioStr);
						List<Pagamento> pagamentos = pagamentoCRUD.consultarPagamentosPorFuncionario(idFuncionario);

						if (pagamentos.isEmpty()) {
							areaResultado
									.append("Nenhum pagamento encontrado para o funcionário com ID " + idFuncionario);
						} else {
							for (Pagamento pagamento : pagamentos) {
								areaResultado.append(pagamento.toString() + "\n");
							}
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(ConsultaPagamento.this,
								"ID do funcionário deve ser um número inteiro.", "Erro de Entrada",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(ConsultaPagamento.this,
								"Erro ao consultar pagamentos: " + ex.getMessage(), "Erro de Consulta",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(ConsultaPagamento.this, "Por favor, insira o ID do funcionário.",
							"Erro de Entrada", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Configura propriedades do frame
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Centraliza a janela na tela
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ConsultaPagamento();
			}
		});
	}
}

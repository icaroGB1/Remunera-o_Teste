package Interface;

import javax.swing.JFrame;
import crud.ItemVendaCRUDImpl;

public class Main {
	  public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("CadastroDeProdutosPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new TelaVendaItemVenda());
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	  }

}

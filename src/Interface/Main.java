package Interface;

import javax.swing.JFrame;

public class Main {
	  public static void main(String[] args) {
		JFrame frame = new JFrame("CadastroDeProdutosPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new CadastroDeProdutosPanel());
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	  }

}

package heig.igl3.roc2.GUI;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ROC2StatusBar extends JPanel {
	
	private JLabel message;
	
	public ROC2StatusBar() {
		add(message = new JLabel());
		setLayout(new GridLayout(1, 1));
	}
	
	public void setMessage(String message) {
		this.message.setText(message);
	}

}

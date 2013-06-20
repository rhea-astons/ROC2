package heig.igl3.roc2.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe ROC2StatusBar Gestion de la barre de status
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
@SuppressWarnings("serial")
public class ROC2StatusBar extends JPanel {

	private JLabel message;

	/**
	 * Constructeur
	 */
	public ROC2StatusBar() {
		add(message = new JLabel());
		setBackground(Color.lightGray);
		setLayout(new GridLayout(1, 1));
	}

	/**
	 * Définition du message à afficher
	 * 
	 * @param message
	 *            le message à afficher
	 */
	public void setMessage(String message) {
		this.message.setText(message);
	}

}

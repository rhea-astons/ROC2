package heig.igl3.roc2.GUI;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Classe ROC2MenuBar Gestion de la barre de menu
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
@SuppressWarnings("serial")
public class ROC2MenuBar extends JMenuBar {

	// Menu Fichier
	JMenuItem menuItemQuitter;
	// JMenuItem menuItemGraphe;

	// Menu Edition
	JMenuItem menuiItemBudget;
	JMenuItem menuItemCategories;
	JMenuItem menuItemMouvements;

	// Menu Aide
	JMenuItem menuItemApropos;

	/**
	 * Constructeur
	 */
	public ROC2MenuBar() {
		// Fichier - Mnemo F
		JMenu menuFichier = new JMenu("Ficher");
		menuFichier.setMnemonic(KeyEvent.VK_F);
		add(menuFichier);

		// Fichier > Quitter -Mnemo Q
		menuItemQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
		// menuItemQuitter.addActionListener(menuListener);
		menuFichier.add(menuItemQuitter);

		// Fichier > Graphe -Mnemo G
		// menuItemGraphe = new JMenuItem("Graphe", KeyEvent.VK_G);

		// menuFichier.add(menuItemGraphe);
		// Edition - Mnemo E
		JMenu menuEdition = new JMenu("Edition");
		menuEdition.setMnemonic(KeyEvent.VK_E);
		add(menuEdition);

		// Edition > Budget - Mnemo B
		menuiItemBudget = new JMenuItem("Budget", KeyEvent.VK_B);

		menuEdition.add(menuiItemBudget);

		// Edition > Catégories - Mnemo C
		menuItemCategories = new JMenuItem("Catégories", KeyEvent.VK_C);
		// menuItemCategories.addActionListener(menuListener);
		menuEdition.add(menuItemCategories);

		// Edition > Mouvements - Mnemo M
		menuItemMouvements = new JMenuItem("Mouvements", KeyEvent.VK_M);
		// menuItemMouvements.addActionListener(menuListener);
		menuEdition.add(menuItemMouvements);

		// Aide - Menmo H
		JMenu menuAide = new JMenu("?");
		menuAide.setMnemonic(KeyEvent.VK_H);
		add(menuAide);

		// Aide > A propos - Mnemo A
		menuItemApropos = new JMenuItem("A propos", KeyEvent.VK_A);
		// menuItemApropos.addActionListener(menuListener);
		menuAide.add(menuItemApropos);
	}

}

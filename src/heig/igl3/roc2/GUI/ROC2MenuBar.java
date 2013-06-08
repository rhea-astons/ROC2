package heig.igl3.roc2.GUI;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ROC2MenuBar extends JMenuBar{
	
	//Menu Fichier
	JMenuItem menuItemQuitter;
	
	//Menu Edition
	JMenuItem menuItemCategories;
	JMenuItem menuItemMouvements;
	
	//Menu Aide
	JMenuItem menuItemApropos;
	
	
	
	
	public ROC2MenuBar() {
		// Fichier - Mnemo F
		JMenu menuFichier = new JMenu("Ficher");
		menuFichier.setMnemonic(KeyEvent.VK_F);
		add(menuFichier);
		
			// Fichier > Quitter -Mnemo Q
			menuItemQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
			//menuItemQuitter.addActionListener(menuListener);
			menuFichier.add(menuItemQuitter);
		
		// Edition - Mnemo E
		JMenu menuEdition = new JMenu("Edition");
		menuEdition.setMnemonic(KeyEvent.VK_E);
		add(menuEdition);
			
			// Edition > Catégories - Mnemo C
			menuItemCategories = new JMenuItem("Catégories", KeyEvent.VK_C);
			//menuItemCategories.addActionListener(menuListener);
			menuEdition.add(menuItemCategories);
			
			// Edition > Mouvements - Mnemo M
			menuItemMouvements = new JMenuItem("Mouvements", KeyEvent.VK_M);
			//menuItemMouvements.addActionListener(menuListener);
			menuEdition.add(menuItemMouvements);
			
		// Aide - Menmo H
		JMenu menuAide = new JMenu("?");
		menuAide.setMnemonic(KeyEvent.VK_H);
		add(menuAide);
		
			// Aide > A propos - Mnemo A
			menuItemApropos = new JMenuItem("A propos", KeyEvent.VK_A);
			//menuItemApropos.addActionListener(menuListener);
			menuAide.add(menuItemApropos);
	}

}

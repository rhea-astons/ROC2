package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Mouvement;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Classe BudgetView pour le GUI de visualisation des budgets
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
@SuppressWarnings("serial")
public class BudgetView extends JPanel {
	Budget budget;

	/**
	 * Constructeur
	 * 
	 * @param budget
	 *            Le budget à afficher
	 */
	public BudgetView(Budget budget) {
		this.budget = budget;
		ROC2PieChart chartPie = new ROC2PieChart(budget);
		ROC2LineChart chartLine = new ROC2LineChart(budget);
		this.setLayout(new BorderLayout(0, 0));

		JPanel splitPanel = new JPanel(new GridLayout(1, 2));
		JPanel budgPanel = new JPanel(new BorderLayout(0, 0));
		JPanel chartPanel = new JPanel(new GridLayout(2, 1));
		chartPanel.add(chartLine.createPanel());
		chartPanel.add(chartPie.createPanel());

		DefaultListModel<String> lm = new DefaultListModel<String>();
		JList<String> list = new JList<String>();
		for (Categorie cat : budget.categories) {
			float sum = (float) 0.0;
			for (Mouvement mouv : budget.mouvements) {
				if (mouv.idCategorie == cat.id) {
					sum += mouv.montant;
				}
			}
			lm.addElement(cat.libelle + ": " + sum);
		}
		list.setModel(lm);
		JScrollPane sp = new JScrollPane(list);

		budgPanel.add(sp);

		splitPanel.add(budgPanel);
		splitPanel.add(chartPanel, BorderLayout.SOUTH);

		add(splitPanel);

	}

}

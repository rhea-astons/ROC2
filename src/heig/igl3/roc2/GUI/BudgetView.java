package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Mouvement;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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

		DefaultTableModel tm = new DefaultTableModel(new String[] {"Catégorie", "Montant"}, 0);
		
		float total = (float) 0.0;
		for (Categorie cat : budget.categories) {
			float sum = (float) 0.0;
			for (Mouvement mouv : budget.mouvements) {
				if (mouv.idCategorie == cat.id) {
					if (mouv.ESType == 0)
						sum += mouv.montant;
					else
						sum -= mouv.montant;
				}
			}
			if (sum != 0.0) {
				tm.addRow(new String[] {cat.libelle, Float.toString(sum)});
			}
			total += sum;
		}
		tm.addRow(new String[] {"",""});
		tm.addRow(new String[] {"Total", Float.toString(total)});
		
		
		JTable budgetTable = new JTable(tm);
		JScrollPane scrollPane = new JScrollPane(budgetTable);
		budgetTable.setFillsViewportHeight(true);

		budgPanel.add(scrollPane);

		splitPanel.add(budgPanel);
		splitPanel.add(chartPanel, BorderLayout.SOUTH);

		add(splitPanel);

	}

}

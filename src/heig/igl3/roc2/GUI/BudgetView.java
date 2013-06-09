package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BudgetView extends JPanel {
	Budget budget;
	public BudgetView(Budget budget){
		this.budget = budget;
		ROC2PieChart chart = new ROC2PieChart(budget);
		this.setLayout(new BorderLayout(0,0));
		
		JPanel splitPanel = new JPanel(new GridLayout(1,2));
		JPanel budgPanel = new JPanel(new BorderLayout(0,0));
		
		splitPanel.add(budgPanel);
		splitPanel.add(chart.createPanel());
		
		add(splitPanel);
		
	}

}

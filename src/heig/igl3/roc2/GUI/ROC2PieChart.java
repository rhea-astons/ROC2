package heig.igl3.roc2.GUI;

import java.awt.Font;

import javax.swing.JPanel;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Mouvement;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Classe de création de pieChart
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
@SuppressWarnings("serial")
public class ROC2PieChart extends JPanel {

	private Budget budget;

	/**
	 * Constructeur
	 * 
	 * @param budget
	 */
	public ROC2PieChart(Budget budget) {
		this.budget = budget;

	}

	/**
	 * Création du dataset pour le pieChart
	 * 
	 * @return le dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();


		for (Categorie cat : budget.categories) {
			for (Mouvement mouv : budget.mouvements){
				if (mouv.idCategorie == cat.id && mouv.ESType == 1){
					dataset.setValue(cat.libelle, budget.sommeCategorieSorties(cat.id));
				}
			}
			
		}
		return dataset;
	}

	/**
	 * Création du graphique
	 * 
	 * @param dataset
	 *            le dataset
	 * @return un graphique JFreeChart
	 */
	private JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart("Budget", // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		return chart;

	}

	/**
	 * Création d'un panel contenant le graphique
	 * 
	 * @return le panel
	 */
	public JPanel createPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}

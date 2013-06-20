package heig.igl3.roc2.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Mouvement;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Classe ROC2LineChart Affichage du lineChart
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
@SuppressWarnings("serial")
public class ROC2LineChart extends JPanel {

	private Budget budget;

	/**
	 * Constructeur
	 * 
	 * @param budget
	 */
	public ROC2LineChart(Budget budget) {
		this.budget = budget;
	}

	/**
	 * Crée un dataset pour le graphique
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CategoryDataset createDataset() {

		GregorianCalendar toDay = new GregorianCalendar();

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		@SuppressWarnings("rawtypes")
		ArrayList<ArrayList> dataList = new ArrayList();

		for (Mouvement mouv : budget.mouvements) {

			if (toDay.compareTo(mouv.date) >= -6) {
				boolean found = false;
				boolean dateFound = false;
				for (@SuppressWarnings("rawtypes")
				ArrayList d : dataList) {
					GregorianCalendar dat = (GregorianCalendar) d.get(0);
					if (mouv.date.equals(dat)) {
						dateFound = true;
						if (mouv.ESType == (int) d.get(2)) {
							d.set(1, mouv.montant + (Float) d.get(1));
							found = true;
						}
					}

				}
				if (!found && !dateFound) {
					@SuppressWarnings("rawtypes")
					ArrayList data = new ArrayList();
					@SuppressWarnings("rawtypes")
					ArrayList dataDummy = new ArrayList();
					data.add(mouv.date);
					data.add(mouv.montant);
					data.add(mouv.ESType);
					dataList.add(data);
					dataDummy.add(mouv.date);
					dataDummy.add((float) 0.0);
					if (mouv.ESType == 0) {
						dataDummy.add(1);
					} else {
						dataDummy.add(0);
					}
					dataList.add(dataDummy);
				} else if (!found) {
					@SuppressWarnings("rawtypes")
					ArrayList data = new ArrayList();

					data.add(mouv.date);
					data.add(mouv.montant);
					data.add(mouv.ESType);
					dataList.add(data);

				}
			}

		}

		for (@SuppressWarnings("rawtypes")
		ArrayList d : dataList) {
			String serie;
			if ((int) d.get(2) == 0) {
				serie = "Entrees";
			} else {
				serie = "Sorties";
			}
			String dateConcat;
			dateConcat = ((GregorianCalendar) d.get(0))
					.get(Calendar.DAY_OF_MONTH)
					+ "."
					+ ((GregorianCalendar) d.get(0)).get(Calendar.MONTH);
			dataset.addValue((Number) d.get(1), serie, dateConcat);
		}

		return dataset;

	}

	/**
	 * Création du graphique
	 * 
	 * @param dataset
	 *            le dataset du graphique
	 * @return un objet JFreeChart contenant le graphique
	 */
	private JFreeChart createChart(CategoryDataset dataset) {

		// create the chart...
		JFreeChart chart = ChartFactory.createLineChart("Budget", // chart title
				"Mois", // domain axis label
				"Montant", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// legend.setShapeScaleX(1.5);
		// legend.setShapeScaleY(1.5);
		// legend.setDisplaySeriesLines(true);

		chart.setBackgroundPaint(Color.white);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);

		// customise the range axis...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);

		/*
		 * // customise the renderer... LineAndShapeRenderer renderer =
		 * (LineAndShapeRenderer) plot.getRenderer(); //
		 * renderer.setDrawShapes(true);
		 * 
		 * renderer.setSeriesStroke( 0, new BasicStroke( 2.0f,
		 * BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]
		 * {10.0f, 6.0f}, 0.0f ) ); renderer.setSeriesStroke( 1, new
		 * BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		 * 1.0f, new float[] {6.0f, 6.0f}, 0.0f ) ); renderer.setSeriesStroke(
		 * 2, new BasicStroke( 2.0f, BasicStroke.CAP_ROUND,
		 * BasicStroke.JOIN_ROUND, 1.0f, new float[] {2.0f, 6.0f}, 0.0f ) ); //
		 * OPTIONAL CUSTOMISATION COMPLETED.
		 */
		return chart;
	}

	/**
	 * Création d'un panel
	 * 
	 * @return le panel contenant le graphique
	 */
	public JPanel createPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}

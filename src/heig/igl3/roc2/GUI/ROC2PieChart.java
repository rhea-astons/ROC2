package heig.igl3.roc2.GUI;

import java.awt.Font;

import javax.swing.JPanel;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class ROC2PieChart {
	
	private Budget budget;
	
	public ROC2PieChart(Budget budget){
		this.budget = budget;
	}

	private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Categorie cat : budget.categories){
        	 dataset.setValue(cat.libelle, budget.sommeCategorie(cat.id));
        }
        return dataset;        
    }
	
	private JFreeChart createChart(PieDataset dataset) {
        
        JFreeChart chart = ChartFactory.createPieChart(
            "Budget",  // chart title
            dataset,             // data
            true,               // include legend
            true,
            false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;
        
    }
	
	public JPanel createPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
}

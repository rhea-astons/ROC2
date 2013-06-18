package heig.igl3.roc2.GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

@SuppressWarnings("serial")
public class ROC2PieChart extends JPanel{
	
	private Budget budget;
	private JPanel panel;
	
	
	public ROC2PieChart(Budget budget){
		this.budget = budget;
		
	}
	
	

	private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Categorie cat : budget.categories){
        	
        	 dataset.setValue(cat.libelle, budget.sommeCategorieSorties(cat.id));
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

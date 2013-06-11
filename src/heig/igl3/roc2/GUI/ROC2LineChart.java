package heig.igl3.roc2.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Vector;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Mouvement;
import heig.igl3.roc2.Business.Mouvement.TypeES;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

@SuppressWarnings("serial")
public class ROC2LineChart extends JPanel {
	
	private Budget budget;
	private JPanel panel;
	
	
	public ROC2LineChart(Budget budget){
		this.budget = budget;
	}

	@SuppressWarnings("unchecked")
	private CategoryDataset createDataset(){
		
		GregorianCalendar date;
		
		GregorianCalendar toDay = new GregorianCalendar();
		
		//Définition des séries
		String serie1 = "Entrees";
		String serie2 = "Sorties";
		String serie3 = "Balance";
		//Définition des valeurs
		double entree = 0.0;
		double sortie = 0.0;
		double balance = 0.0;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		@SuppressWarnings("rawtypes")
		ArrayList<ArrayList> dataList = new ArrayList();
        
		
		
		//Faire une liste de vecteur Date / Montant (somme du jour)
		for (Mouvement mouv : budget.mouvements){
        	date = mouv.date;
        	if(toDay.compareTo(mouv.date) >= -6 ){
        		boolean found = false;
        		for(@SuppressWarnings("rawtypes") ArrayList d : dataList){
        			if (mouv.date == d.get(0) && mouv.ESType == (int)d.get(2)){
        				d.set(1, mouv.montant + (Float)d.get(1));
        				found = true;
        			}
        			
        		}
        		if (!found){
        			@SuppressWarnings("rawtypes")
    				ArrayList data= new ArrayList();
    				data.add(mouv.date);
    				data.add(mouv.montant);
    				data.add(mouv.ESType);
    				dataList.add(data);
    			}
        	}

        }
		
		for (ArrayList d : dataList){
			String serie;
			if ((int)d.get(2) == 0){
				serie = "Entrées";
			}else{
				serie = "Sorties";
			}
			dataset.addValue((Number) d.get(1), serie, d.get(0).toString());
		}
        

        
        return dataset;   
        
        
    }
	  
	
	private JFreeChart createChart( CategoryDataset dataset) {
	        
	        // create the chart...
	         JFreeChart chart = ChartFactory.createLineChart(
	            "Budget",       // chart title
	            "Mois",                    // domain axis label
	            "Montant",                   // range axis label
	            dataset,                   // data
	            PlotOrientation.VERTICAL,  // orientation
	            true,                      // include legend
	            true,                      // tooltips
	            false                      // urls
	        );
	
	        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
	//        final StandardLegend legend = (StandardLegend) chart.getLegend();
	  //      legend.setDisplaySeriesShapes(true);
	    //    legend.setShapeScaleX(1.5);
	      //  legend.setShapeScaleY(1.5);
	        //legend.setDisplaySeriesLines(true);
	
	        chart.setBackgroundPaint(Color.white);
	
	        CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setRangeGridlinePaint(Color.white);
	
	        // customise the range axis...
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        rangeAxis.setAutoRangeIncludesZero(true);
	        
	        /*
	     // customise the renderer...
	        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//	        renderer.setDrawShapes(true);

	        renderer.setSeriesStroke(
	            0, new BasicStroke(
	                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
	                1.0f, new float[] {10.0f, 6.0f}, 0.0f
	            )
	        );
	        renderer.setSeriesStroke(
	            1, new BasicStroke(
	                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
	                1.0f, new float[] {6.0f, 6.0f}, 0.0f
	            )
	        );
	        renderer.setSeriesStroke(
	            2, new BasicStroke(
	                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
	                1.0f, new float[] {2.0f, 6.0f}, 0.0f
	            )
	        );
	        // OPTIONAL CUSTOMISATION COMPLETED.
	        */
	        return chart;
	}
	
	public JPanel createPanel(){
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}

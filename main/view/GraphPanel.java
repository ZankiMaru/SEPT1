package main.view;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import main.model.ForecastInterval;
import main.model.Station;

public class GraphPanel extends JPanel{
	
	Station stationData;
	
	public GraphPanel(Station stationData)
	{
		this.stationData = stationData;
		JFreeChart chart = createChart(createDataset());
		ChartPanel panel = new ChartPanel(chart);
		this.add(panel);
	}
	private CategoryDataset createDataset( ) 
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		for (ForecastInterval inter: stationData.list2)
		{
			dataset.setValue( inter.temp, "Temp", inter.dateTime);  
		}
		return dataset;         
	}
	 private JFreeChart createChart( CategoryDataset dataset )
	 {
		 
		 JFreeChart chart = ChartFactory.createLineChart(      
				 "Temperature", // chart title 
				 "Date",		// Bottom Axis label
				 "Temp",
				 dataset);

		 return chart;
	   }

}

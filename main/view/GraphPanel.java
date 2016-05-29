package main.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import main.model.ForecastInterval;
import main.model.Station;

public class GraphPanel extends JPanel{
	
	Station stationData;
	JButton rdbTemp, rdbWind, rdbRain;
	static JFreeChart chartActive;
   JFreeChart chartTemp;
   JFreeChart chartWind;
   JFreeChart chartRain;
	JPanel redrawPanel;
	ChartPanel panel;
	
	public GraphPanel(Station stationData)
	{

	   this.stationData = stationData;
		this.setLayout(new BorderLayout());

		//Chart
		chartTemp = createChart("Temperature", "Temp °C", createTempDataset());
		chartWind = createChart("Wind", "Km/h", createWindDataset());
		chartRain = createChart("Rain", "mm", createRainDataset());
		
		panel = new ChartPanel(chartTemp);
		redrawPanel = new JPanel(new BorderLayout());
		redrawPanel.add(panel, BorderLayout.CENTER);
		this.add(redrawPanel, BorderLayout.CENTER);
		
		// Buttons
		JPanel mainPanel = new JPanel(new FlowLayout());
		rdbTemp = new JButton("Temperature");
		rdbWind = new JButton("Wind");
		rdbRain = new JButton("Rain");
		rdbTemp.addActionListener(new GraphButtonListener(this));
		rdbWind.addActionListener(new GraphButtonListener(this));
		rdbRain.addActionListener(new GraphButtonListener(this));
		mainPanel.add(rdbTemp);
		mainPanel.add(rdbWind);
		mainPanel.add(rdbRain);

		this.add(mainPanel, BorderLayout.PAGE_END);
	}
	
	private CategoryDataset createTempDataset() 
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		for (ForecastInterval inter: stationData.list2){
	       if(inter.getAMPM() == 0){
	          dataset.setValue( inter.temp, "Temp °C", Integer.toString(inter.getDate()) + ":" + Integer.toString(inter.getHour()) + "AM");  
	       }
	       else
	          dataset.setValue( inter.temp, "Temp °C", Integer.toString(inter.getDate()) + ":" + Integer.toString(inter.getHour()) + "PM");  
		}
		return dataset;         
	}
	private CategoryDataset createWindDataset() 
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		for (ForecastInterval inter: stationData.list2){
			dataset.setValue( inter.wind, "Km/h", Integer.toString(inter.getDate()) + "-" + Integer.toString(inter.getHour())); 
		}
		return dataset;         
	}
	private CategoryDataset createRainDataset() 
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		for (ForecastInterval inter: stationData.list2)
			dataset.setValue( inter.rain, "mm", Integer.toString(inter.getDate()) + "/" + inter.getMonth() + ":" + Integer.toString(inter.getHour()));  
		return dataset;         
	}
	
	 private JFreeChart createChart(String title, String axis, CategoryDataset dataset )
	 {
		 
		 JFreeChart chart = ChartFactory.createLineChart(      
				 title, // chart title 
				 "Date",		// Bottom Axis label
				 axis,			// Right Axis label
				 dataset);
		 
		 CategoryPlot x = (CategoryPlot) chart.getPlot();
		 CategoryAxis domainAx = x.getDomainAxis();
		 domainAx.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		 
		 return chart;
	   }

}
class GraphButtonListener implements ActionListener {
	GraphPanel panel;
    public GraphButtonListener(GraphPanel panel) {
		this.panel = panel;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Temperature")) {
        	System.out.print("Temperature");
        	panel.redrawPanel.removeAll();
            panel.panel = new ChartPanel(panel.chartTemp);
            panel.redrawPanel.add(panel.panel, BorderLayout.CENTER);
            panel.redrawPanel.repaint();
            panel.redrawPanel.revalidate();
        }
        if (e.getActionCommand().equals("Wind")) {
        	System.out.print("Wind");
        	panel.redrawPanel.removeAll();
            panel.panel = new ChartPanel(panel.chartWind);
            panel.redrawPanel.add(panel.panel, BorderLayout.CENTER);
            panel.redrawPanel.repaint();
            panel.redrawPanel.revalidate();
        }
        if (e.getActionCommand().equals("Rain")) {
        	System.out.print("Rain");
        	panel.redrawPanel.removeAll();
            panel.panel = new ChartPanel(panel.chartRain);
            panel.redrawPanel.add(panel.panel, BorderLayout.CENTER);
            panel.redrawPanel.repaint();
            panel.redrawPanel.revalidate();
        }
    }
}
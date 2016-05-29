package main.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import main.model.ForecastInterval;
import main.model.Station;

public class GraphPanel extends JPanel{
	
	Station stationData;
	JButton rdbTemp, rdbWind, rdbRain;
	
	public GraphPanel(Station stationData)
	{
		this.stationData = stationData;
		this.setLayout(new BorderLayout());
		
		//Chart
		JFreeChart chart = createChart(createDataset());
		ChartPanel panel = new ChartPanel(chart);
		this.add(panel, BorderLayout.CENTER);
		
		//Radio buttons
		JPanel mainPanel = new JPanel(new FlowLayout());
		rdbTemp = new JButton("Temperature");
		rdbWind = new JButton("Wind");
		rdbRain = new JButton("Rain");
		rdbTemp.addActionListener(new GraphButtonListener(rdbTemp));
		rdbWind.addActionListener(new GraphButtonListener(rdbTemp));
		rdbRain.addActionListener(new GraphButtonListener(rdbTemp));
		mainPanel.add(rdbTemp);
		mainPanel.add(rdbWind);
		mainPanel.add(rdbRain);

		
		
		this.add(mainPanel, BorderLayout.PAGE_END);
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
class GraphButtonListener implements ActionListener {
    JButton button;
    
    public GraphButtonListener(JButton button) {
        this.button = button;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Temperature")) {
            
        }
        if (e.getActionCommand().equals("Wind")) {
           
        }
        if (e.getActionCommand().equals("Rain")) {
            
        }
    }
}
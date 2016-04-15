package main.view;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.model.Interval;


public class NowPanel extends JPanel
{
    
    Interval interval;
    JPanel mainPanel, statsPanel;
    JLabel lblDayLabel, lblMinMax, lblWind, lblRain;
    
    public NowPanel(Interval interval)
    {
        this.interval = interval;
        
        GridLayout mainLayout = new GridLayout(3, 1);
        GridLayout statsLayout = new GridLayout(2, 3, 25, 5);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        
        mainPanel = new JPanel(mainLayout);
        
        statsPanel = new JPanel();
        statsPanel.setLayout(statsLayout);
        
        lblDayLabel = new JLabel("Now");
        
        fillPanel();
        
        statsPanel.add(new JLabel("Min/Max °C:"));
        statsPanel.add(new JLabel("Wind:"));
        statsPanel.add(new JLabel("Rain:"));
        
        //
        statsPanel.add(lblMinMax);
        statsPanel.add(lblWind);
        statsPanel.add(lblRain);

        //
        mainPanel.add(lblDayLabel);
        mainPanel.add(statsPanel);
        
        this.add(mainPanel);
        
    }
    
    public void fillPanel()
    {
        lblMinMax = new JLabel(interval.getTemp());
        lblWind = new JLabel(interval.getWind());
        lblRain = new JLabel(interval.getRain());
    }
}

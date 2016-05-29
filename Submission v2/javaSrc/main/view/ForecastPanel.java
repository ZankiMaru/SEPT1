package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.model.ForecastDayData;

public class ForecastPanel extends JPanel
{
    ForecastDayData day;
    JPanel mainPanel, statsPanel;
    JLabel lblDayLabel, lblMinMax, lblWind, lblRain;
    
    public ForecastPanel(ForecastDayData day)
    {
        GridLayout mainLayout = new GridLayout(3, 1);
        GridLayout statsLayout = new GridLayout(2, 3, 25, 5);
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        mainPanel = new JPanel(mainLayout);
        mainPanel.setBorder(new EmptyBorder(5,35,5,35));
        
        statsPanel = new JPanel();
        statsPanel.setLayout(statsLayout);
        
        String dateString = String.valueOf(day.getDate());
        dateString += "/" + String.valueOf(day.getMonth());
        dateString += "/" + String.valueOf(day.getYear());
        lblDayLabel = new JLabel(dateString);
        
        this.day = day;
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
        
        this.add(mainPanel,BorderLayout.CENTER);

    }
    
    public void fillPanel()
    {
        lblMinMax = new JLabel(day.getMinMax());
        lblWind = new JLabel(day.getWind());
        lblRain = new JLabel(day.getRain());
    }
}

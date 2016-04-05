package view;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.DayData;


public class DayPanel extends JPanel
{
    DayData day;
    JPanel mainPanel, statsPanel;
    JLabel lblDayLabel, lblMinMax, lblWind, lblRain;
    
    public DayPanel(String label, DayData day)
    //public DayPanel(String label)
    {
        GridLayout mainLayout = new GridLayout(3, 1);
        GridLayout statsLayout = new GridLayout(2, 3, 25, 5);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        
        mainPanel = new JPanel(mainLayout);
        
        statsPanel = new JPanel();
        statsPanel.setLayout(statsLayout);
        
        lblDayLabel = new JLabel(label);
        
        this.day = day;
        fillPanel();
        //fillDumbyData();
        
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
        lblMinMax = new JLabel(day.getMinMax());
        lblWind = new JLabel(day.getWind());
        lblRain = new JLabel(day.getRain());
    }
    
    public void fillDumbyData()
    {
        lblMinMax = new JLabel("10/24 °C");
        lblWind = new JLabel("30kmph");
        lblRain = new JLabel("2mm");
    }
    
    
}

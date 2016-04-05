package view;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Station;


public class StationView extends JFrame
{
    Station stationData;
    JPanel mainPanel;
    JLabel lblStationName;
    NowPanel nowPanel;
    DayPanel dpToday, dpYesterday, dpDayBefore;
    
    
    public StationView(Station stationData)
    {
        mainPanel = new JPanel();
        lblStationName = new JLabel(stationData.name);
        nowPanel = new NowPanel(stationData.getNow());
        dpToday = new DayPanel("Today", stationData.getDay("today"));
        dpYesterday = new DayPanel("Yesterday", stationData.getDay("yesterday"));
        dpDayBefore = new DayPanel("Day Before", stationData.getDay("dayBefore"));
        
        //this.setBounds(100, 100, 1000, 1000);
        this.setBounds(100, 100, 350, 700);
        
        this.setResizable(false);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        GridLayout mainLayout = new GridLayout(5, 1, 10, 10);
        
        mainPanel.add(lblStationName);
        mainPanel.add(nowPanel);
        mainPanel.add(dpToday);
        mainPanel.add(dpYesterday);
        mainPanel.add(dpDayBefore);
        
        mainPanel.setLayout(mainLayout);
        this.setLayout(new GridLayout(1,1));
        
        this.add(mainPanel);
        
        this.setVisible(true);
    }
    
    public void fillData()
    {
        
    }
}

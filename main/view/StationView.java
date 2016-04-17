package main.view;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.model.Station;


public class StationView extends JFrame
{
    Station stationData;
    JPanel mainPanel;
    JLabel lblStationName;
    NowPanel nowPanel;
    DayPanel dpToday, dpYesterday, dpDayBefore;
    StationInfoPanel sipStation;
    MainMenu mainMenu;
    
    public StationView(Station stationData, MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
        this.stationData = stationData;
        mainPanel = new JPanel();
        
        fillData();
        
        this.setBounds(100, 100, 350, 700);
        
        this.setResizable(false);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        GridLayout mainLayout = new GridLayout(5, 1, 10, 10);

        sipStation = new StationInfoPanel(this);
        mainPanel.add(sipStation);
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
        nowPanel = new NowPanel(stationData.getNow());
        dpToday = new DayPanel("Today", stationData.getDay("today"));
        dpYesterday = new DayPanel("Yesterday", stationData.getDay("yesterday"));
        dpDayBefore = new DayPanel("Day Before", stationData.getDay("dayBefore"));
    }
    
    public void addRemoveFavourites(){
       System.out.println("station view " + stationData.getStation());
       mainMenu.addRemoveFavourites(stationData.getStation());
    }
}

class StationInfoPanel extends JPanel
{
    JPanel mainPanel, buttonsPanel;
    JLabel lblStationLabel;
    JButton btnRefresh, btnFavourite;
    
    public StationInfoPanel(StationView stationView)
    {
        GridLayout mainLayout = new GridLayout(2, 1, 0, 15);
        FlowLayout buttonsLayout = new FlowLayout(FlowLayout.CENTER, 50, 0);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        
        mainPanel = new JPanel(mainLayout);
        
        //Buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(buttonsLayout);
        
        btnFavourite = new JButton("Favourite");
        btnFavourite.addActionListener(new ButtonListener(stationView));
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ButtonListener(stationView));
        
        buttonsPanel.add(btnFavourite);
        buttonsPanel.add(btnRefresh);
        
        //Station Name
        lblStationLabel = new JLabel(stationView.stationData.name, SwingConstants.CENTER);
        lblStationLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        
        mainPanel.add(buttonsPanel);
        mainPanel.add(lblStationLabel);
        
        this.add(mainPanel);
    }
}

class ButtonListener implements ActionListener {
    StationView stationView;
    ButtonListener(StationView stationView) {
        this.stationView = stationView;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Refresh")) {
            //Refresh Data
            stationView.fillData();
        }
        if (e.getActionCommand().equals("Favourite")) {
           //Add or Remove Favourites
           stationView.addRemoveFavourites();
        }
    }
}

package main.view;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.model.ForecastDayData;
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
    JTabbedPane tabPanel;
    StationView stationView = this;
    GraphPanel graphPanel;
    
    JPanel mainForecastPanel;
    
    public StationView(Station stationData, MainMenu mainMenu)
    {
        this.mainMenu = mainMenu;
        this.stationData = stationData;
             
        
        tabPanel = new JTabbedPane();
        
        mainPanel = new JPanel();
        
        fillData();
        
        this.setBounds(100, 100, 350, 700);
        
        this.setResizable(false);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        GridLayout mainLayout = new GridLayout(5, 1, 10, 10);

        //Overview Panel
        sipStation = new StationInfoPanel(this, stationData.getFaved());
        mainPanel.add(sipStation);
        mainPanel.add(nowPanel);
        mainPanel.add(dpToday);
        mainPanel.add(dpYesterday);
        mainPanel.add(dpDayBefore);
        
        mainPanel.setLayout(mainLayout);
        this.setLayout(new GridLayout(1,1));
        

        JPanel mainPanel2 = new JPanel();
        
        fillData();
        
        this.setBounds(100, 100, 350, 700);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        

        sipStation = new StationInfoPanel(this, stationData.getFaved());
        mainPanel2.add(sipStation);
        mainPanel2.add(nowPanel);
        mainPanel2.add(dpToday);
        mainPanel2.add(dpYesterday);
        mainPanel2.add(dpDayBefore);
        
        mainPanel2.setLayout(mainLayout);

        //Forecast Data
        if (stationData.forecastDays.size() == 0) //If there's no data
        {
        	mainForecastPanel = new JPanel(new GridLayout(4, 1, 10, 10));
            mainForecastPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            mainForecastPanel.add(sipStation);
            JLabel nodata = new JLabel("No Data");
            nodata.setHorizontalAlignment(SwingConstants.CENTER); 
        	mainForecastPanel.add(nodata);
        }
        else
        {
        	mainForecastPanel = new JPanel(new GridLayout(stationData.forecastDays.size() + 1, 1, 10, 10));
            mainForecastPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            mainForecastPanel.add(sipStation);
	        for (ForecastDayData day : stationData.forecastDays)
	        {
	        	ForecastPanel panel = new ForecastPanel(day);
	        	mainForecastPanel.add(panel);
	        }
        }
        
        graphPanel = new GraphPanel(stationData);
        
        tabPanel.addTab("Overview", null, mainPanel, "Overview Tab");
        tabPanel.addTab("Forecast", null, mainForecastPanel, "Forecast Tab");
        tabPanel.addTab("Graph", null, graphPanel, "Graph Tab");

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
            	
            	System.out.println(tabPanel.getSelectedIndex());
            	if(tabPanel.getSelectedIndex() == 0 || tabPanel.getSelectedIndex() == 1)
            		stationView.setBounds(stationView.getX(), stationView.getY(), 350, 700);
            	else
            		stationView.setBounds(stationView.getX(), stationView.getY(), 900, 700);

            }
          };

          tabPanel.addChangeListener(changeListener);

        
        this.add(tabPanel);

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
       mainMenu.addRemoveFavourites(stationData.getStation());
    }
}

class StationInfoPanel extends JPanel
{
    JPanel mainPanel, buttonsPanel;
    JLabel lblStationLabel;
    JButton btnRefresh, btnFavourite;
    
    public StationInfoPanel(StationView stationView, boolean faved)
    {
        GridLayout mainLayout = new GridLayout(2, 1, 0, 15);
        FlowLayout buttonsLayout = new FlowLayout(FlowLayout.CENTER, 50, 0);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        
        mainPanel = new JPanel(mainLayout);
        
        //Buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(buttonsLayout);
        
        if(faved)
        	btnFavourite = new JButton("Unfavourite");
        else
        	btnFavourite = new JButton("Favourite");
        btnFavourite.addActionListener(new ButtonListener(stationView, btnFavourite));
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ButtonListener(stationView, btnRefresh));
        
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
    JButton button;
    ButtonListener(StationView stationView, JButton button) {
        this.stationView = stationView;
        this.button = button;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Refresh")) {
            //Refresh Data
            stationView.fillData();
        }
        if (e.getActionCommand().equals("Favourite")) {
           //Add or Remove Favourites
           stationView.addRemoveFavourites();
           button.setText("Unfavourite");
        }
        if (e.getActionCommand().equals("Unfavourite")) {
            //Add or Remove Favourites
            stationView.addRemoveFavourites();
            button.setText("Favourite");
        }
    }
}

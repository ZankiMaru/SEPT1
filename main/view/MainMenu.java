package main.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSplitPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import main.model.Extraction;
import main.model.Model;
import main.model.Station;

import java.awt.GridLayout;

import javax.swing.ScrollPaneConstants;

/* MainMenu Class is the Swing class for the main menu of the application.
 * MainMenu Class will be populated with data and informations through 
 * the Main Class. */
public class MainMenu extends JFrame {
	
	/* headerPanel is a JPanel for date and clock at the top.
	 * browsePanel is a JPanel for browse available states.
	 * favePanel is a JPanel for list of favorite stations. 
	 * favScrollPane is a JScrollPane for scrolling through favePanel.
	 * browseScrollPane is a JSCrollPane for scrolling through states. */
	JPanel headerPanel, browsePanel, favePanel;
	static JPanel citiesPanel, mainPanel;
	JScrollPane favScrollPane, browseScrollPane, cityScrollPane;
	JLabel dateLabel;
	static Model model;
	static MainMenu mainMenu;
	JSplitPane statefavPanel;
	static ArrayList<JButton> stationButtons = new ArrayList<JButton>();
	static ArrayList<JButton> stateButtons = new ArrayList<JButton>();
	
	public MainMenu(Model model) {
		/* Set up main menu data. */
		MainMenu.model = model;
		mainMenu = this;
		
		setTitle("Weather Obs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 900));
		this.setResizable(false);
		
		/* Set up main menu layout */
		GridBagLayout mainWindowGridBagLayout = new GridBagLayout();
		mainWindowGridBagLayout.columnWidths = new int[]{0, 0};
		mainWindowGridBagLayout.rowHeights = new int[] {50, 0};
		mainWindowGridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		mainWindowGridBagLayout.rowWeights = new double[]{0.0, 0.8};
		getContentPane().setLayout(mainWindowGridBagLayout);
		
		/* Set up favourite panel */
		favePanel = new JPanel();

		/* Populate favourite panel */
		populateFavePanel();
		
		/* Set up scrollpane for favourite panel */
		favScrollPane = new JScrollPane(favePanel);
		favScrollPane.setMinimumSize(new Dimension(0,0));
		GridBagConstraints gbc_favScrollPane = new GridBagConstraints();
		gbc_favScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_favScrollPane.fill = GridBagConstraints.BOTH;
		gbc_favScrollPane.gridx = 0;
		gbc_favScrollPane.gridy = 2;
		favScrollPane.getVerticalScrollBar().setUnitIncrement(12);

		/* Set up browsePanel */
		browsePanel = new JPanel();
		browsePanel.setBackground(Color.CYAN);
		
		/* Set up browse panel layout */
		GridBagLayout gbl_browsePanel = new GridBagLayout();
		gbl_browsePanel.columnWidths = new int[] {0};
		gbl_browsePanel.rowHeights = new int[]{55, 55, 55, 55, 55, 55, 55, 55, 55, 0};
		gbl_browsePanel.columnWeights = new double[]{1.0};
		gbl_browsePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		browsePanel.setLayout(gbl_browsePanel);
		
		/* Populate browse panel */
		populateBrowsePanel();
		
		/* Set up browse panel scroll bar */
		browseScrollPane = new JScrollPane(browsePanel);
		browseScrollPane.setMinimumSize(new Dimension(400,100));
		GridBagConstraints gbc_browseScrollPane = new GridBagConstraints();
		gbc_browseScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_browseScrollPane.fill = GridBagConstraints.BOTH;
		gbc_browseScrollPane.gridx = 0;
		gbc_browseScrollPane.gridy = 1;
		browseScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		/* Set up header Panel */
		headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.insets = new Insets(5, 5, 5, 4);
		gbc_headerPanel.fill = GridBagConstraints.BOTH;
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		getContentPane().add(headerPanel, gbc_headerPanel);
		headerPanel.setLayout(new BorderLayout(0, 0));
		headerPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		/* Set up date label */
		dateLabel = new JLabel("date and clock here");
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(dateLabel, BorderLayout.CENTER);
						
		/* Set up both state and favourite panel*/
		statefavPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,browseScrollPane,favScrollPane);
		statefavPanel.setDividerLocation(350);
		statefavPanel.setContinuousLayout(true);
		GridBagConstraints gbc_statefavPanel = new GridBagConstraints();
		gbc_statefavPanel.insets = new Insets(0, 5, 5, 5);
		gbc_statefavPanel.fill = GridBagConstraints.BOTH;
		gbc_statefavPanel.gridx = 0;
		gbc_statefavPanel.gridy = 1;
		
		/* Change the split panel divider location */
		statefavPanel.addPropertyChangeListener("dividerLocation", new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		        int location = ((Integer)e.getNewValue()).intValue();
		        if (location > 500){
		            JSplitPane splitPane = (JSplitPane)e.getSource();
		            splitPane.setDividerLocation( 500 );
		        }
		    }
		});
		
		/* Set up cities panel */
		citiesPanel = new JPanel();
		
		/* Set up cities scroll panel */
		cityScrollPane = new JScrollPane(citiesPanel);
		cityScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cityScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		cityScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		/* Set up main panel */
		mainPanel = new JPanel();
		mainPanel.setLayout(new CardLayout());
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		/* Add cities and state panel to card layout */
		mainPanel.add(statefavPanel, "state");
		mainPanel.add(cityScrollPane, "city");
		
		
		getContentPane().add(mainPanel, gbc_statefavPanel);
      
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Date curDate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("EEEE K:mm a");
				dateLabel.setText("<html><span style='font-size:12px'>" + formatter.format(curDate) + "</span></html>");
			}
		},0, 60000);
		
		addWindowListener(new exitAdapter());

	}

	
	/* populateBrowsePanel is a function used to populate browsePanel. */
	private void populateBrowsePanel() {
		JButton antarticaButton = new JButton("Antarctica");
		GridBagConstraints gbc_antarticaButton = new stateButtonGBC(0,0,browsePanel,antarticaButton);
		antarticaButton.addActionListener(new stateButtonListener());
		
		JButton canberraButton = new JButton("Canberra");
		GridBagConstraints gbc_canberraButton = new stateButtonGBC(0,1,browsePanel,canberraButton);
		canberraButton.addActionListener(new stateButtonListener());

		JButton newSouthWalesButton = new JButton("New South Wales");
		GridBagConstraints gbc_newSouthWalesButton = new stateButtonGBC(0,2,browsePanel,newSouthWalesButton);
		newSouthWalesButton.addActionListener(new stateButtonListener());

		JButton northernTerritoryButton = new JButton("Northern Territory");
		GridBagConstraints gbc_northernTerritoryButton = new stateButtonGBC(0,3,browsePanel,northernTerritoryButton);
		northernTerritoryButton.addActionListener(new stateButtonListener());

		JButton queenslandButton = new JButton("Queensland");
		GridBagConstraints gbc_queenslandButton = new stateButtonGBC(0,4,browsePanel,queenslandButton);
		queenslandButton.addActionListener(new stateButtonListener());

		JButton southAustraliaButton = new JButton("South Australia");
		GridBagConstraints gbc_southAustraliaButton = new stateButtonGBC(0,5,browsePanel,southAustraliaButton);
		southAustraliaButton.addActionListener(new stateButtonListener());

		JButton tasmaniaButton = new JButton("Tasmania");
		GridBagConstraints gbc_tasmaniaButton = new stateButtonGBC(0,6,browsePanel,tasmaniaButton);
		tasmaniaButton.addActionListener(new stateButtonListener());
	    
		JButton victoriaButton = new JButton("Victoria");
		GridBagConstraints gbc_victoriaButton = new stateButtonGBC(0,7,browsePanel,victoriaButton);
		victoriaButton.addActionListener(new stateButtonListener());

		JButton westernAustraliaButton = new JButton("Western Australia");
		GridBagConstraints gbc_westernAustraliaButton = new stateButtonGBC(0,8,browsePanel,westernAustraliaButton);
		westernAustraliaButton.addActionListener(new stateButtonListener());
		
		stateButtons.add(westernAustraliaButton);
		stateButtons.add(victoriaButton);
		stateButtons.add(tasmaniaButton);
		stateButtons.add(southAustraliaButton);
		stateButtons.add(queenslandButton);
		stateButtons.add(northernTerritoryButton);
		stateButtons.add(newSouthWalesButton);
		stateButtons.add(antarticaButton);
		stateButtons.add(canberraButton);
	}

	/* populateFavePanel is a function used to populate favePanel. It checks if
	 * user have a favourited stations. If yes, it will populate it. If not, it
	 * will print a label saying to add the user's favourite station. */
	private void populateFavePanel() {
	  
	   int faveNum = 0;
	   ArrayList<Station> faves = model.getAllFave();

	   if(faves.isEmpty()) {
	      JLabel emptyFavLabel = new JLabel("Please add your favourite stations");
	      emptyFavLabel.setHorizontalAlignment(SwingConstants.CENTER);
	      favePanel.setLayout(new BorderLayout());
	      favePanel.add(emptyFavLabel, BorderLayout.NORTH);
	   }	
	   else{
	      for(int i = 0; i<faves.size(); i++){
	         JButton cityButton = new JButton(faves.get(i).getStation().replaceAll("\\p{P}", ""));
	         cityButton.setPreferredSize(new Dimension(20,40));
	         cityButton.addActionListener(new cityButtonListener());
	         favePanel.add(cityButton);
	         faveNum++;
	      }
	      GridLayout gridLayout;
	      if(faveNum < 5)
	         gridLayout = new GridLayout(5,1);
	      else
	         gridLayout = new GridLayout(faveNum,1);

	      gridLayout.setVgap(2);
	      favePanel.setLayout(gridLayout);
	   }
	}
	
	/* populateCitiesPanel is a function used to populate citiesPanel with
	 * cityButton */
   private static void populateCitiesPanel(JSONArray cities)
   {
      citiesPanel.removeAll();
      citiesPanel.repaint();
      stationButtons.clear();
      JButton backButton = new JButton("<html><font size=6>Back</font></html>");
      backButton.setBorderPainted(false);
      backButton.setBackground(new Color(230,230,230));
      backButton.setOpaque(true);
      backButton.addActionListener(new backButtonListener());

      citiesPanel.add(backButton);
      int cityNum = 0;
      for(int j = 0; j<cities.size(); j++){
         JSONObject cities2 = (JSONObject) cities.get(j);
         JButton cityButton = new JButton((String) cities2.get("city"));
         cityButton.setPreferredSize(new Dimension(20,40));
         cityButton.addActionListener(new cityButtonListener());
         citiesPanel.add(cityButton);
         stationButtons.add(cityButton);
         cityNum++;
      }
      cityNum ++ ;
      GridLayout gridLayout = new GridLayout(cityNum,1);
      gridLayout.setVgap(2);
      citiesPanel.setLayout(gridLayout);
   }
   
   public void addRemoveFavourites(String stationName){
      System.out.println("window fave " + stationName);
      model.addRemoveFavourites(stationName);
	   favePanel.removeAll();
	   favePanel.repaint();
	   populateFavePanel();
   }
	
	/* stateButtonGBC is a inner private class used to make states button 
	 * easier to manage. */
	private class stateButtonGBC extends GridBagConstraints{
		private stateButtonGBC(int x, int y, JPanel panel, Component button){
			this.fill = GridBagConstraints.BOTH;
			this.gridx = x;
			this.gridy = y;
			panel.add(button, this);
		}
	}
	
	/* stateButtonListener is an ActionListener class for state button */
	private class stateButtonListener implements ActionListener{
      @Override
      public void actionPerformed(ActionEvent arg0) {
         JSONArray x = Extraction.getStateCities(arg0.getActionCommand());
         populateCitiesPanel(x);
         CardLayout cl = (CardLayout)(mainPanel.getLayout());
         cl.show(mainPanel, "city");
      }
	}
		
	/* backButtonListener is an ActionListener class for back button */
	private static class backButtonListener implements ActionListener{
	   @Override
      public void actionPerformed(ActionEvent arg0) {
	      CardLayout cl = (CardLayout)(mainPanel.getLayout());
         cl.show(mainPanel, "state");            
	   }
	}
   
	/* cityButtonListener is an ActionListener class for cities button */
	private static class cityButtonListener implements ActionListener{
	   @Override
	   public void actionPerformed(ActionEvent arg0) {
	      try { 
	         Station station = model.getStation(arg0.getActionCommand());
	         station.getData();
	         JFrame frame = new StationView(station, mainMenu);
	         frame.setLocationRelativeTo(mainMenu);
	         frame.setVisible(true);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	}

	public JButton getStateButton(String stateName){
		for(int i = 0; i<stateButtons.size(); i++){
			if(stateButtons.get(i).getText().equals(stateName))
				return stateButtons.get(i);
		}
		return null;
	}
	
	public JButton getStationButton(String stationName){
		for(int i = 0; i<stationButtons.size(); i++){
			if(stationButtons.get(i).getText().equals(stationName))
				return stationButtons.get(i);
		}
		return null;
	}

	private class exitAdapter extends WindowAdapter{
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         Point location = mainMenu.getLocationOnScreen();
         int x = (int) location.getX();
         int y = (int) location.getY();
         model.saveCoordinates(x,y);
         model.saveFaveList();
      }

	}
	
	
}
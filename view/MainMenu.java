package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import model.Extraction;

import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSplitPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.*;
import controller.Main;

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
	
	public MainMenu(Model model) {
		this.model = model;
		getContentPane().setBackground(Color.MAGENTA);
		setTitle("Weather Obs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 900);
		setMinimumSize(new Dimension(400, 600));
		this.setResizable(false);
        
		GridBagLayout mainWindowGridBagLayout = new GridBagLayout();
		mainWindowGridBagLayout.columnWidths = new int[]{0, 0};
		mainWindowGridBagLayout.rowHeights = new int[] {50, 0};
		mainWindowGridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		mainWindowGridBagLayout.rowWeights = new double[]{0.0, 0.8};
		getContentPane().setLayout(mainWindowGridBagLayout);
		
		favePanel = new JPanel();
		favePanel.setLayout(new BorderLayout(0, 0));

		populateFavePanel();
		
		favScrollPane = new JScrollPane(favePanel);
		favScrollPane.setMinimumSize(new Dimension(0,0));
		GridBagConstraints gbc_favScrollPane = new GridBagConstraints();
		gbc_favScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_favScrollPane.fill = GridBagConstraints.BOTH;
		gbc_favScrollPane.gridx = 0;
		gbc_favScrollPane.gridy = 2;
		favScrollPane.getVerticalScrollBar().setUnitIncrement(12);

		browsePanel = new JPanel();
		browsePanel.setBackground(Color.CYAN);
		
		GridBagLayout gbl_browsePanel = new GridBagLayout();
		gbl_browsePanel.columnWidths = new int[] {0};
		gbl_browsePanel.rowHeights = new int[]{55, 55, 55, 55, 55, 55, 55, 55, 55, 0};
		gbl_browsePanel.columnWeights = new double[]{1.0};
		gbl_browsePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		browsePanel.setLayout(gbl_browsePanel);
		
		populateBrowsePanel();
		
		browseScrollPane = new JScrollPane(browsePanel);
		browseScrollPane.setMinimumSize(new Dimension(400,100));
		GridBagConstraints gbc_browseScrollPane = new GridBagConstraints();
		gbc_browseScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_browseScrollPane.fill = GridBagConstraints.BOTH;
		gbc_browseScrollPane.gridx = 0;
		gbc_browseScrollPane.gridy = 1;
		browseScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel.fill = GridBagConstraints.BOTH;
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		getContentPane().add(headerPanel, gbc_headerPanel);
		headerPanel.setLayout(new BorderLayout(0, 0));
		
		dateLabel = new JLabel("date and clock here");
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(dateLabel, BorderLayout.CENTER);
						
		JSplitPane statefavPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,browseScrollPane,favScrollPane);
		statefavPanel.setDividerLocation(350);
		statefavPanel.setContinuousLayout(true);
		GridBagConstraints gbc_statefavPanel = new GridBagConstraints();
		gbc_statefavPanel.fill = GridBagConstraints.BOTH;
		gbc_statefavPanel.gridx = 0;
		gbc_statefavPanel.gridy = 1;
		
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
		
		citiesPanel = new JPanel();
		
		cityScrollPane = new JScrollPane(citiesPanel);
		cityScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cityScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		cityScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new CardLayout());
		mainPanel.add(statefavPanel, "state");
		mainPanel.add(cityScrollPane, "city");
		
		
      getContentPane().add(mainPanel, gbc_statefavPanel);
      
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          System.out.println("asd");
          Date curDate = new Date();
          SimpleDateFormat formatter = new SimpleDateFormat("EEEE K:mm a");
          dateLabel.setText("<html><span style='font-size:12px'>" + formatter.format(curDate) + "</span></html>");
        }
      },0, 60000);
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
	}

	/* populateFavePanel is a function used to populate favePanel. It checks if
	 * user have a favourited stations. If yes, it will populate it. If not, it
	 * will print a label saying to add the user's favourite station. */
	private void populateFavePanel() {
		JLabel emptyFavLabel = new JLabel("Please add your favourite stations");
		emptyFavLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favePanel.add(emptyFavLabel, BorderLayout.NORTH);
	}
	
	/* populateCitiesPanel is a function used to populate citiesPanel with
	 * cityButton */
   private static void populateCitiesPanel(JSONArray cities)
   {
      citiesPanel.removeAll();
      citiesPanel.repaint();
      JButton backButton = new JButton("Back");
      backButton.addActionListener(new backButtonListener());

      citiesPanel.add(backButton);
      int x = 0;
      for(int j = 0; j<cities.size(); j++){
         JSONObject cities2 = (JSONObject) cities.get(j);
         JButton cityButton = new JButton((String) cities2.get("city"));
         cityButton.setPreferredSize(new Dimension(20,40));
         cityButton.addActionListener(new cityButtonListener());
         citiesPanel.add(cityButton);
         x++;
      }
      x ++ ;
      citiesPanel.setLayout(new GridLayout(x,1));
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
         System.out.println(arg0.getActionCommand());
         JSONArray x = Extraction.getStateCities(arg0.getActionCommand());
         System.out.println(x);
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
	         JFrame frame = new StationView(station);
	         frame.setLocationRelativeTo(null);
	         frame.setVisible(true);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	}
}
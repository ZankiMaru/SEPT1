import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSplitPane;

/* MainMenu Class is the Swing class for the main menu of the application.
 * MainMenu Class will be populated with data and informations through 
 * the Main Class. */
public class MainMenu extends JFrame {
	
	/* headerPanel is a JPanel for date and clock at the top.
	 * browsePanel is a JPanel for browse available states.
	 * favePanel is a JPanel for list of favorite stations. 
	 * favScrollPane is a JScrollPane for scrolling through favePanel.
	 * browseScrollPane is a JSCrollPane for scrolling through states. */
	JPanel headerPanel;
	JPanel browsePanel;
	JPanel favePanel;
	JScrollPane favScrollPane;
	JScrollPane browseScrollPane;

	public MainMenu() {
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
		
		headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_headerPanel.fill = GridBagConstraints.BOTH;
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		getContentPane().add(headerPanel, gbc_headerPanel);
		headerPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel dateLabel = new JLabel("date and clock here");
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(dateLabel, BorderLayout.CENTER);
		
		JSplitPane mainPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,browseScrollPane,favScrollPane);
		mainPanel.setDividerLocation(350);
		mainPanel.setContinuousLayout(true);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		getContentPane().add(mainPanel, gbc_splitPane);
		
		mainPanel.addPropertyChangeListener("dividerLocation", new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		        int location = ((Integer)e.getNewValue()).intValue();
		        if (location > 500){
		            JSplitPane splitPane = (JSplitPane)e.getSource();
		            splitPane.setDividerLocation( 500 );
		        }
		    }
		});
	}

	
	/* populateBrowsePanel is a function used to populate browsePanel. */
	private void populateBrowsePanel() {
		JButton antarticaButton = new JButton("Antartica");
		GridBagConstraints gbc_antarticaButton = new stateButtonGBC(0,0,browsePanel,antarticaButton);
		
		JButton canberraButton = new JButton("Canberra");
		GridBagConstraints gbc_canberraButton = new stateButtonGBC(0,1,browsePanel,canberraButton);

		JButton newSouthWalesButton = new JButton("New South Wales");
		GridBagConstraints gbc_newSouthWalesButton = new stateButtonGBC(0,2,browsePanel,newSouthWalesButton);
		
		JButton northernTerritoryButton = new JButton("Northern Territory");
		GridBagConstraints gbc_northernTerritoryButton = new stateButtonGBC(0,3,browsePanel,northernTerritoryButton);
		
		JButton queenslandButton = new JButton("Queensland");
		GridBagConstraints gbc_queenslandButton = new stateButtonGBC(0,4,browsePanel,queenslandButton);
		
		JButton southAustraliaButton = new JButton("South Australia");
		GridBagConstraints gbc_southAustraliaButton = new stateButtonGBC(0,5,browsePanel,southAustraliaButton);
		
		JButton tasmaniaButton = new JButton("Tasmania");
		GridBagConstraints gbc_tasmaniaButton = new stateButtonGBC(0,6,browsePanel,tasmaniaButton);
		
		JButton victoriaButton = new JButton("Victoria");
		GridBagConstraints gbc_victoriaButton = new stateButtonGBC(0,7,browsePanel,victoriaButton);
	
		JButton westernAustraliaButton = new JButton("Western Australia");
		GridBagConstraints gbc_westernAustraliaButton = new stateButtonGBC(0,8,browsePanel,westernAustraliaButton);
	}

	/* populateFavePanel is a function used to populate favePanel. It checks if
	 * user have a favourited stations. If yes, it will populate it. If not, it
	 * will print a label saying to add the user's favourite station. */
	private void populateFavePanel() {
		JLabel emptyFavLabel = new JLabel("Please add your favourite stations");
		emptyFavLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favePanel.add(emptyFavLabel, BorderLayout.NORTH);
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
}
package test;

import static org.junit.Assert.*;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.model.DayData;
import main.model.Interval;
import main.model.Model;
import main.model.State;
import main.model.Station;
import main.view.MainMenu;
import main.view.NowPanel;

public class TestCase {
	private Model model;
	private MainMenu mainMenu;
	
	@Before
	public void test() {
		model = new Model();
		model.init_data();
		mainMenu = new MainMenu(model);
	}
	
	@Test
	public void testModelInitialisation(){
		int statesCount = model.countStates();
		Assert.assertEquals(9, statesCount);
	}
	
	@Test
	public void testStationList(){
		State state = model.getState("Canberra");
		int stationCount = state.countAllStation();
		Assert.assertEquals(16, stationCount);
	}
	
	@Test
	public void testGetStationData(){
		Station station = model.getStation("Batchelor");
		station.getData();
		Assert.assertTrue(station.checkData());
	}
	
	@Test
	public void testStationButton(){
		JButton stateButton = mainMenu.getStateButton("Canberra");
		stateButton.doClick();
		JButton stationButton = mainMenu.getStationButton("Braidwood");
		stationButton.doClick();
		Station station = model.getStation("Braidwood");
      Assert.assertEquals(true, station.checkData());
	}
	
	@Test
	public void testFavesIO(){
		model.addRemoveFavourites("Bunbury");
		model.saveFaveList();
		
		String line;
		BufferedReader br;
		
		boolean found = false;
      try
      {
         br = new BufferedReader(new FileReader("favourites.txt"));
         while((line = br.readLine()) != null){
            if(line.equalsIgnoreCase("Bunbury"))
               found = true;
         };
         Assert.assertEquals(true, found);
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
	}

	@Test
	public void testNowPanelFillsData()
	{
		Interval i = new Interval("20160416193000", 15.1, 11, "0.0");
		NowPanel np = new NowPanel(i);

		Assert.assertEquals(np.lblMinMax.getText(), "15.1 °C");
	}

	@Test
	public void testDayDataFormatting()
	{
		DayData d = new DayData();
		d.min = (double) 10;
		d.max = (double) 20;
		String str = d.getMinMax();
		Assert.assertEquals(str, "10.0/20.0 °C");
	}
}

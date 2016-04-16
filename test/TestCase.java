package test;

import static org.junit.Assert.*;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.model.Model;
import main.model.State;
import main.model.Station;
import main.view.MainMenu;

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
		Assert.assertTrue(station.checkData());
	}
	
	@Test
	public void testFavesIO(){
		Model model = new Model();
		model.addFave("Station x");
		model.saveFaveList("testFavourites.txt");
		
		String line;
		BufferedReader br = new BufferedReader(new FileReader("testFavourites.txt"))
		while((line = br.readLine()) != null){
			Assert.assertEquals("Station x", line);
		};
	}
}

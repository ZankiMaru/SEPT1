package main.app;

import java.awt.EventQueue;
import javax.swing.JFrame;

import main.model.Model;
import main.view.MainMenu;

public class Main {
	public static void main(String[] args){
		String favouritesFile = "favourites.txt";
		String coordinateFile = "coordinates.txt";
		final Model model = new Model();
		
		model.init_data();
		model.init_coordinates(coordinateFile);
		model.init_faveList(favouritesFile);
		
		/* Load main menu frame */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					JFrame frame = new MainMenu(model);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
		/*Save data*/
		model.saveFaveList(favouritesFile);
		model.saveCoordinates(coordinateFile);
	}
}

package main.app;

import java.awt.EventQueue;
import javax.swing.JFrame;

import main.model.Model;
import main.view.MainMenu;

public class Main {
	public static void main(String[] args){
		final Model model = new Model();
		model.init_data();
		/* Checks for favorite stations */
		
		
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
	}
}

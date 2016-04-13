package controller;

import view.*;
import model.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.*;

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
//	             frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	
	public static void getCities(String stateName) {
//	   model.getCities();
	}
	
}

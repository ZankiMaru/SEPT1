package controller;

import view.*;
import model.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.*;

public class Main {
   
	public static void main(String[] args){
		
		HashMap dataStructure = new HashMap();
		HashMap faveList = new HashMap();
		Model model = new Model();
		/* Checks for favorite stations */
		
		
		/* Load main menu frame */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					JFrame frame = new MainMenu();
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

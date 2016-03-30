import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		
		/* Checks for favorite stations */
		
		
		/* Load main menu frame */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					JFrame frame = new MainMenu();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

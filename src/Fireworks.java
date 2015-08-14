import java.awt.event.*;
import javax.swing.*;

/*
 * Currently Known Bugs
 * Background flashes white/transparent sometimes for no reason
 * Fireworks kinda lag in the middle for some reason
 * 
 * please fix!
 */

public class Fireworks extends JFrame implements Runnable{
	private static final long serialVersionUID = 4052899274202593763L;
	public FireworksPanel fireworksPanel;
	
	public static void main(String[] args){
		new Timer(33, new ActionListener() {
			Fireworks f = new Fireworks();
			
		    public void actionPerformed(ActionEvent e) {
		    	if (f.fireworksPanel.sparksLeft() > 0) {
		    		f.repaint();
		    	}
		    }
		}).start();
	}
	
	public void run(){
		this.repaint();
	}
	
	public Fireworks(){
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.fireworksPanel = new FireworksPanel();
		this.setContentPane(fireworksPanel);
		this.pack();
		this.validate();
		this.setVisible(true);
		this.setResizable(false);
	}
}
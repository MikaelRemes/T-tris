package mainpackage;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class UserInterface extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private TetrisPanel tetrispanel;
	//private JPanel statspanel;
	
	
	
	public UserInterface() {
		
		this.setTitle("Tötris");
		tetrispanel = new TetrisPanel();
		//statspanel = new JPanel();
		
		this.getContentPane().setLayout(new BorderLayout());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setResizable(false);
		
		this.add((JPanel) tetrispanel);
		
		this.setSize(400, 800);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
		
	}
	
	public void run(){
		boolean running=true;
		while(running){
			
			tetrispanel.repaint();
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				running=false;
				e.printStackTrace();
			}
		}
	}

}

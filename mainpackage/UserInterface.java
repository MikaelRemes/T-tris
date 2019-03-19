package mainpackage;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class UserInterface extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private TetrisPanel tetrispanel;
		
	public UserInterface() {
		

		this.setTitle("Tötris");
		tetrispanel = new TetrisPanel();
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//stops user from resizing screen
		this.setResizable(false);
		
		
		
		//adds tetris gamepanel to JFrame
		this.add((JPanel) tetrispanel);
		
		//sets frame size to 400 by 800
		this.setSize(400, 800);
		
		//pack helps to center game on screen when jframe is set visible
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
			
		tetrispanel.run();
	}

}

package mainpackage;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainMenu extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	JButton startGameButton = new JButton("START GAME");
	JButton testRunButton = new JButton("TEST RUN");
	JButton resetButton = new JButton("RESET FILES");
	JButton quitButton = new JButton("QUIT");
	ButtonListener buttonListener = new ButtonListener(this);

	public MainMenu() {
		
		this.setResizable(false);
		
		this.getContentPane().setLayout(new GridLayout(4,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setPreferredSize(new Dimension(500,500));
		
		startGameButton.addActionListener(buttonListener);
		this.add(startGameButton);
		testRunButton.addActionListener(buttonListener);
		this.add(testRunButton);
		resetButton.addActionListener(buttonListener);
		this.add(resetButton);
		quitButton.addActionListener(buttonListener);
		this.add(quitButton);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener{
		private MainMenu menu;
		
		private ButtonListener(MainMenu menu) {
			this.menu=menu;
		}
		
  	    public void actionPerformed(ActionEvent e) {
  	    	if(e.getSource() == startGameButton) {
  	    		menu.setVisible(false);
  	    		menu.dispose();
  	    		new UserInterface();
  	    	}
  	    }
	}
	

}

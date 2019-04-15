package mainpackage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


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
		
		makeButton(startGameButton);
		makeButton(testRunButton);
		makeButton(resetButton);
		makeButton(quitButton);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void makeButton(JButton button){
		button.addActionListener(buttonListener);
		button.setBackground(new Color(0, 46, 255));
		if(button == quitButton)button.setBackground(new Color(255,60,90));
		button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.add(button);
	}
	
	private class ButtonListener implements ActionListener{
		private MainMenu menu;
		
		private ButtonListener(MainMenu menu) {
			this.menu=menu;
		}
		
		//TODO: make quitbutton (see userinterface gameover) and resetbutton work (save a highscore of 0 or something, see userinterface gameover)
  	    public void actionPerformed(ActionEvent e) {
  	    	if(e.getSource() == startGameButton) {
  	    		menu.setVisible(false);
  	    		menu.dispose();
  	    		new UserInterface();
  	    	}
  	    	if(e.getSource() == testRunButton) {
  	    		try {
  	    		System.out.println("RUNNING TESTS....");
  	    		
  	    		long highScore = doHighScoreFilesTest();
  	    		if(highScore!=-1) {
  	    			System.out.print("Found highscore, current highscore: ");
  	    			System.out.println("" + highScore);
  	    		}else {
  	    			System.out.println("Could not find highscore");
  	    		}
  	    		
  	    		JOptionPane.showMessageDialog(menu, "Testing done \nAll necessary files found \nHighscore: " + highScore);
  	    		System.out.println("TESTING DONE");
  	    		}catch(Exception error) {
  	    			System.out.println("ERROR:");
  	    			error.printStackTrace();
  	    			JOptionPane.showMessageDialog(menu, "ERROR IN TESTING \n SEE DEVELOPER CONSOLE");
  	    		}
  	    		
  	    	}
  	    }
	}
	
	public long doHighScoreFilesTest() {
		try {
			FileInputStream fis = new FileInputStream(new File("./Highscore.txt"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			long highScore = (long) ois.readObject();
			ois.close();
			fis.close();
			return highScore;
		}catch(Exception e) {
			return -1;
		}
	}

	

}

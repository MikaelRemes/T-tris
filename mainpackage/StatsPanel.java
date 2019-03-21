package mainpackage;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsPanel extends JPanel{
	private static final long serialVersionUID = 3L;
	private GameState game;
	
	private JLabel pointsLabel = new JLabel();
	private JLabel levelLabel = new JLabel();

	public StatsPanel(GameState game, int width, int height) {
		this.game=game;
		this.setSize(width,height);
		this.setLayout(new GridLayout(2,1));
		
		pointsLabel.setText("Points: " + game.points);
		this.add(pointsLabel);
		levelLabel.setText("Level: " + game.level);
		this.add(levelLabel);
		
	}
	
	public void update() {
		pointsLabel.setText("Points: " + game.points);
		levelLabel.setText("Level: " + game.level);
	}

}

package mainpackage;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsPanel extends JPanel{
	private static final long serialVersionUID = 3L;
	private GameState game;
	
	private JLabel pointsLabel = new JLabel();
	private JLabel levelLabel = new JLabel();
	private JLabel highScoreLabel = new JLabel();

	public StatsPanel(GameState game, int width, int height) {
		this.game=game;
		this.setSize(width,height);
		this.setLayout(new GridLayout(3,1));
		
		pointsLabel.setText("Points: " + game.points);
		this.add(pointsLabel);
		levelLabel.setText("Level: " + game.level);
		this.add(levelLabel);
		highScoreLabel.setText("Highscore: " + game.highScore);
		this.add(highScoreLabel);
		
	}
	
	public void update() {
		pointsLabel.setText("Points: " + game.points);
		levelLabel.setText("Level: " + game.level);
		highScoreLabel.setText("Highscore: " + game.highScore);
	}

}

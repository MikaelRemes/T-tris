package mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * Displays the game.
 */
public class TetrisPanel extends JPanel{
	
	private static final long serialVersionUID = 2L;
	private final int extraHeightForPiece=4;
	
	/** Object that holds the state of the game logic */
	private GameState game;
	/** Background color */
	private Color backgroundColor = Color.BLACK;
	
	/**
	 * Constructor
	 * Listens to the key presses of the player
	 * @param game, the game logic object.
	 * @param width, width of the panel
	 * @param height, height of the panel
	 * 
	 */
	public TetrisPanel(GameState game, int width, int height) {
		this.game=game;
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				game.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				game.keyPressed(e);
			}
		});
		this.setPreferredSize(new Dimension(width,height));
		setFocusable(true);
	}
	
	/**
	 * Draws the screen graphics depending on the state of the game.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//background color
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i=0;i<game.boxes.length;i++) {
			for(int j=extraHeightForPiece;j<game.boxes[i].length;j++) {
				//color of frozen boxes
				if(game.boxes[i][j]==1) {
					g.setColor(Color.BLUE);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, (j-extraHeightForPiece)*(getHeight()/(game.boxes[0].length-extraHeightForPiece))+2,
							(getWidth()/game.boxes.length)-4, (getHeight()/(game.boxes[0].length-extraHeightForPiece))-4);
				}
				//color of player controlled boxes
				if(game.boxes[i][j]==2) {
					g.setColor(Color.RED);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, (j-extraHeightForPiece)*(getHeight()/(game.boxes[0].length-extraHeightForPiece))+2,
							(getWidth()/game.boxes.length)-4, (getHeight()/(game.boxes[0].length-extraHeightForPiece))-4);
				}
			}
		}

	}
}

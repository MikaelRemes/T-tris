package mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;


public class TetrisPanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 2L;
	private final int width = 400;
	private final int height = 800;
	private final int boxesWidth = 10;
	private final int boxesHeight = 20;
	

	
	private GameState game;
	

	public TetrisPanel() {
		
		game = new GameState(boxesWidth, boxesHeight);
		
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

			}
		});
		
		for(int[] xAxisBoxes : game.boxes) {
			for(@SuppressWarnings("unused") int yAxisBox : xAxisBoxes) {
				yAxisBox=0;
			}
		}
		
		game.boxes[4][0]=2;
		game.boxes[4][1]=2;
		game.boxes[4][2]=2;
		game.boxes[4][3]=2;
		
		
		this.setPreferredSize(new Dimension(width,height));
		setFocusable(true);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i=0;i<game.boxes.length;i++) {
			for(int j=0;j<game.boxes[i].length;j++) {
				if(game.boxes[i][j]==0) {
					g.setColor(Color.GREEN);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, j*(getHeight()/game.boxes[0].length)+2, (getWidth()/game.boxes.length)-4, (getHeight()/game.boxes[0].length)-4);
				}
				if(game.boxes[i][j]==1) {
					g.setColor(Color.BLUE);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, j*(getHeight()/game.boxes[0].length)+2, (getWidth()/game.boxes.length)-4, (getHeight()/game.boxes[0].length)-4);
				}
				if(game.boxes[i][j]==2) {
					g.setColor(Color.RED);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, j*(getHeight()/game.boxes[0].length)+2, (getWidth()/game.boxes.length)-4, (getHeight()/game.boxes[0].length)-4);
				}
			}
		}

	}
	
	public void run(){										//starts frame drawing loop and starts the game loop
		
		boolean running=true;
		while(running){
			
			this.repaint();									//repaints the screen every 15 milliseconds (~60fps)
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				running=false;
				e.printStackTrace();
			}
		}
	}
	

}

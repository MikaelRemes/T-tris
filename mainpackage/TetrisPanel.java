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
	private final int extraHeightForPiece=4;

	
	private GameState game;
	

	public TetrisPanel() {
		
		game = new GameState(boxesWidth, boxesHeight+extraHeightForPiece);
		
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
		
		game.generatePiece();
		
		this.setPreferredSize(new Dimension(width,height));
		setFocusable(true);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i=0;i<game.boxes.length;i++) {
			for(int j=extraHeightForPiece;j<game.boxes[i].length;j++) {
				if(game.boxes[i][j]==0) {
					g.setColor(Color.GREEN);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, (j-extraHeightForPiece)*(getHeight()/(game.boxes[0].length-extraHeightForPiece))+2,
							(getWidth()/game.boxes.length)-4, (getHeight()/(game.boxes[0].length-extraHeightForPiece))-4);
				}
				if(game.boxes[i][j]==1) {
					g.setColor(Color.BLUE);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, (j-extraHeightForPiece)*(getHeight()/(game.boxes[0].length-extraHeightForPiece))+2,
							(getWidth()/game.boxes.length)-4, (getHeight()/(game.boxes[0].length-extraHeightForPiece))-4);
				}
				if(game.boxes[i][j]==2) {
					g.setColor(Color.RED);
					g.fillRect(i*(getWidth()/game.boxes.length)+2, (j-extraHeightForPiece)*(getHeight()/(game.boxes[0].length-extraHeightForPiece))+2,
							(getWidth()/game.boxes.length)-4, (getHeight()/(game.boxes[0].length-extraHeightForPiece))-4);
				}
			}
		}

	}
	
	//starts frame drawing loop
	//repaints the screen every 15 milliseconds (~60fps)
	public void run(){
		boolean running=true;
		while(running){
			
			this.repaint();
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				running=false;
				e.printStackTrace();
			}
		}
	}
	

}

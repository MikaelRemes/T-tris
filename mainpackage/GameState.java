package mainpackage;

import java.awt.event.KeyEvent;
import java.util.Random;

public class GameState implements Runnable{
	
	//represent the coordinates of tetris map
	//value of 2 in box means that space is occupied by player piece
	//value of 1 in box means that space is occupied
	//value of 0 in box means that space is not occupied
	public int[][] boxes;
	
	//represent the coordinates of tetris map of the next frame
	//value of 2 in box means that space is occupied by player piece
	//value of 1 in box means that space is occupied
	//value of 0 in box means that space is not occupied
	public int[][] nextBoxes;
	
	Thread gamerunner;
	
	public GameState(int width, int height) {
		boxes = new int[width][height];
		nextBoxes = new int[width][height];
		
		gamerunner=new Thread(this);
		gamerunner.start();
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			System.out.println("Up pressed");
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			System.out.println("Left pressed");
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			System.out.println("Right pressed");
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			System.out.println("Down pressed");
		}
	}
	
	public void run() {
		boolean running=true;
		while(running){
			
			doGravity();
			System.out.println("one tick");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				running=false;
				e.printStackTrace();
			}
		}
	}
	
	
	public void doGravity() {
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {
				if(boxes[i][j] == 0 && j>=1) {
					if(j>=1 && nextBoxes[i][j-1] !=2)nextBoxes[i][j] = 0;
				}
				if(boxes[i][j] == 1) {
					nextBoxes[i][j] = 1;
				}
				if(boxes[i][j] == 2) {
					if(j<boxes[i].length-1) {
						nextBoxes[i][j+1] = 2;
					}
					if(j>=1 && boxes[i][j-1] == 0) {
						nextBoxes[i][j] = 0;
					}
				}
			}
		}
		
		for(int k=0;k<boxes.length;k++) {
			System.arraycopy(nextBoxes[k], 0, boxes[k], 0, nextBoxes[k].length);
		}
	}
	
	public void generatePiece() {
		Random rng = new Random();
		int nextpiece=rng.nextInt(7);
		
		
		//I-piece
		if(nextpiece==0) {
			
		}
		
		//J-piece
		if(nextpiece==1) {
					
		}
		
		//L-piece
		if(nextpiece==2) {
							
		}				
		
		//O-piece
		if(nextpiece==3) {
									
		}
		
		//S-piece
		if(nextpiece==4) {
									
		}
		
		//T-piece
		if(nextpiece==5) {
			
		}
		
		//Z-piece
		if(nextpiece==6) {
			
		}
		
	}
	

}

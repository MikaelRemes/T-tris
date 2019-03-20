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
	private int[][] nextBoxesGravity;	
	private int[][] nextBoxesMovement;
	
	private boolean collision = false;
	
	private Thread gamerunner;
	
	public GameState(int width, int height) {
		boxes = new int[width][height];
		nextBoxesGravity = new int[width][height];
		nextBoxesMovement = new int[width][height];
		
		gamerunner=new Thread(this);
		gamerunner.start();
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			System.out.println("Up pressed");
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			System.out.println("Left pressed");
			moveLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			System.out.println("Right pressed");
			moveRight();
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
		
		//check collisions
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {		
				if(boxes[i][j] == 2) {
					if(j==boxes[i].length-1) {
						collision=true;
					}
					else {
						if(boxes[i][j+1] == 1) {
							collision=true;
						}
					}
				}
			}
		}
		
		//if collision turn all player controlled boxes into frozen boxes
		if(collision) {
			for(int i=0;i<boxes.length;i++) {
				for(int j=0;j<boxes[i].length;j++) {
					if(boxes[i][j] == 2) {
						boxes[i][j]=1;
					}
				}
			}
		}
		
		
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {
				
				//if empty box
				if(boxes[i][j] == 0) {
					//if not at the top of the screen
					if(j>=1) {
						//if the box above isn't player controlled
						if(nextBoxesGravity[i][j-1] !=2)nextBoxesGravity[i][j] = 0;
					}else nextBoxesGravity[i][j] = 0;
				}
				
				//if frozen box
				if(boxes[i][j] == 1) {
					nextBoxesGravity[i][j] = 1;
				}
				
				//if player controlled box
				if(boxes[i][j] == 2) {
					//the box below will be set to 2 and the box above to 0 (if not at the top of screen)
					nextBoxesGravity[i][j+1] = 2;
					if(j!=0) {
						if(boxes[i][j-1] == 0)nextBoxesGravity[i][j] = 0;
					}
				}			
			}
		}
		
		collision=false;
		
		//copy next boxes state into boxes
		for(int k=0;k<boxes.length;k++) {
			System.arraycopy(nextBoxesGravity[k], 0, boxes[k], 0, nextBoxesGravity[k].length);
		}
	}
	
	
	//return true if no collision
	public boolean moveLeft() {
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {
				//if empty box
				if(boxes[i][j] == 0) {
					nextBoxesMovement[i][j] = 0;
				}
				//if frozen box
				if(boxes[i][j] == 1) {
					nextBoxesMovement[i][j] = 1;
				}
				//if player controlled box
				if(boxes[i][j] == 2) {
					if(i==0)return false;
					if(boxes[i-1][j] == 1)return false;
					
					
					nextBoxesMovement[i-1][j] = 2;
					nextBoxesMovement[i][j]=0;
				}
			}
		}
		
		//copy next boxes state into boxes
		for(int k=0;k<boxes.length;k++) {
			System.arraycopy(nextBoxesMovement[k], 0, boxes[k], 0, nextBoxesMovement[k].length);
		}
		return true;
	}
	
	//return true if no collision
	public boolean moveRight() {
		for(int i=boxes.length-1;i>=0;i--) {
			for(int j=0;j<boxes[i].length;j++) {
				//if empty box
				if(boxes[i][j] == 0) {
					nextBoxesMovement[i][j] = 0;
				}
				//if frozen box
				if(boxes[i][j] == 1) {
					nextBoxesMovement[i][j] = 1;
				}
				//if player controlled box
				if(boxes[i][j] == 2) {
					if(i==boxes.length-1)return false;
					if(boxes[i+1][j] == 1)return false;
					
					
					nextBoxesMovement[i+1][j] = 2;
					nextBoxesMovement[i][j]=0;
				}
			}
		}
		
		//copy next boxes state into boxes
		for(int k=0;k<boxes.length;k++) {
			System.arraycopy(nextBoxesMovement[k], 0, boxes[k], 0, nextBoxesMovement[k].length);
		}
		return true;
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

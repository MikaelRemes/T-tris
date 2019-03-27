package mainpackage;

import java.awt.Point;
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
	private int[][] nextBoxesLineClear;
	private int[][] nextBoxesMovement;
	private int[][] nextBoxesRotationMovement;
	
	public boolean gameOver=false;
	
	private Point pivot = new Point(0,0);
	
	private Thread gamerunner;
	public int level=1;
	public long points=0;
	
	public GameState(int width, int height) {
		boxes = new int[width][height];
		nextBoxesGravity = new int[width][height];
		nextBoxesLineClear = new int[width][height];
		nextBoxesMovement = new int[width][height];
		nextBoxesRotationMovement = new int[width][height];
		
		gamerunner=new Thread(this);
		gamerunner.start();
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			System.out.println("Down pressed");
			doGravity();
		}
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			System.out.println("Up pressed");
			rotatePieceCCW();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			System.out.println("Left pressed");
			moveLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			System.out.println("Right pressed");
			moveRight();
		}
	}
	
	public void run() {
		boolean running=true;
		generatePiece();
		while(running){
			
			doGravity();
			System.out.println("one tick");
			
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				running=false;
				e.printStackTrace();
			}
		}
	}
	
	
	public void doGravity() {
		boolean collision=false;
		
		//check collisions and game overs
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {		
				if(boxes[i][j] == 2) {
					if(j==boxes[i].length-1) {
						collision=true;
					}
					else {
						if(boxes[i][j+1] == 1) {
							collision=true;
							if(j<3) {
								gameOver=true;
							}
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
		
		//drop all player controlled boxes by 1 and replace with air if box above isn't player controlled
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {	
				//if empty box
				if(boxes[i][j] == 0) {
					//if not at the top of the screen
					if(j>=1) {
						//if the box above isn't player controlled
						if(boxes[i][j-1] !=2)nextBoxesGravity[i][j] = 0;
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
		
		//copy next boxes state into boxes
		for(int k=0;k<boxes.length;k++) {
			System.arraycopy(nextBoxesGravity[k], 0, boxes[k], 0, nextBoxesGravity[k].length);
		}
		
		pivot.y++;
		
		if(collision) {
			points += clearLines();
			generatePiece();
			collision=false;
		}
		
	}
	
	//clear all lines
	//return value for how many lines cleared
	public int clearLines() {
		int lines=0;
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {
				//if empty box
				if(boxes[i][j] == 0)nextBoxesLineClear[i][j] = 0;
				//if frozen box
				if(boxes[i][j] == 1)nextBoxesLineClear[i][j] = 1;				
				//if player controlled box
				if(boxes[i][j] == 2)nextBoxesLineClear[i][j] = 2;
			}
		}
		
		
		boolean line=false;
		for(int j=0;j<boxes[0].length;j++) {
			line=true;
			for(int i=0;i<boxes.length;i++) {
				if(boxes[i][j] == 0) {
					line=false;
				}
			}
			if(line) {
				lines++;
				for(int i=0;i<boxes.length;i++) {
					nextBoxesLineClear[i][j] = 0;
					for(int k=j;k>=3;k--) {
						if(nextBoxesLineClear[i][k-1] == 1) {
							nextBoxesLineClear[i][k] = 1;
							nextBoxesLineClear[i][k-1] = 0;
						}
					}
				}
			}
		}
		//copy next boxes state into boxes
		for(int k=0;k<boxes.length;k++) {
			System.arraycopy(nextBoxesLineClear[k], 0, boxes[k], 0, nextBoxesLineClear[k].length);
		}
		return lines;
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
		pivot.x--;
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
		pivot.x++;
		return true;
	}
	
	//return true if no collision
	public boolean rotatePieceCCW() {
		int newTempX=0;
		int newTempY=0;
		int newX=0;
		int newY=0;
		
		for(int i=boxes.length-1;i>=0;i--) {
			for(int j=0;j<boxes[i].length;j++) {
				//if empty box
				if(boxes[i][j] == 0) {
					nextBoxesRotationMovement[i][j] = 0;
				}
				//if frozen box
				if(boxes[i][j] == 1) {
					nextBoxesRotationMovement[i][j] = 1;
				}
				if(boxes[i][j] == 2)nextBoxesRotationMovement[i][j]=0;
			}
		}
		
		for(int i=boxes.length-1;i>=0;i--) {
			for(int j=0;j<boxes[i].length;j++) {
				if(boxes[i][j] == 2) {
					newTempX=i-pivot.x;
					newTempY=j-pivot.y;
					
					newX = newTempY*(-1);
					newY = newTempX;
					
					newX += pivot.x;
					newY += pivot.y;
					
					System.out.println("Pivot X:" + pivot.x + " Pivot Y:" + pivot.y);
					System.out.println(newX);
					System.out.println(newY);
					
					if(newX < 0 || newY < 0 || newX >= boxes.length || newY >= boxes[i].length)return false;
					
					if(boxes[newX][newY] == 1) {
						return false;
					}else {
						if(nextBoxesRotationMovement[i][j] != 2) nextBoxesRotationMovement[i][j]=0;
						nextBoxesRotationMovement[newX][newY] = 2;
					}
					
				}
			}
		}
		
		//copy next boxes state into boxes
		for(int k=0;k<boxes.length;k++) {
			System.arraycopy(nextBoxesRotationMovement[k], 0, boxes[k], 0, nextBoxesRotationMovement[k].length);
		}
		
		return true;
	}
	
	public void generatePiece() {
		Random rng = new Random();
		int nextpiece=rng.nextInt(7);
		
		//I-piece
		if(nextpiece==0) {
			boxes[boxes.length/2-1][0]=2;
			boxes[boxes.length/2-1][1]=2;
			boxes[boxes.length/2-1][2]=2;
			boxes[boxes.length/2-1][3]=2;
			pivot=new Point(boxes.length/2-1,1);
		}
		
		//J-piece
		if(nextpiece==1) {
			boxes[boxes.length/2-1][1]=2;
			boxes[boxes.length/2-1][2]=2;
			boxes[boxes.length/2-1][3]=2;
			boxes[boxes.length/2-2][3]=2;
			pivot=new Point(boxes.length/2-1,2);
		}
		
		//L-piece
		if(nextpiece==2) {
			boxes[boxes.length/2-1][1]=2;
			boxes[boxes.length/2-1][2]=2;
			boxes[boxes.length/2-1][3]=2;
			boxes[boxes.length/2][3]=2;
			pivot=new Point(boxes.length/2-1,2);
		}				
		
		//O-piece
		if(nextpiece==3) {
			boxes[boxes.length/2-1][2]=2;
			boxes[boxes.length/2][2]=2;
			boxes[boxes.length/2-1][3]=2;
			boxes[boxes.length/2][3]=2;
			pivot=new Point(boxes.length/2-1,2);
		}
		
		//S-piece
		if(nextpiece==4) {
			boxes[boxes.length/2][1]=2;
			boxes[boxes.length/2][2]=2;
			boxes[boxes.length/2-1][2]=2;
			boxes[boxes.length/2-1][3]=2;
			pivot=new Point(boxes.length/2-1,2);
		}
		
		//T-piece
		if(nextpiece==5) {
			boxes[boxes.length/2-1][2]=2;
			boxes[boxes.length/2-2][3]=2;
			boxes[boxes.length/2-1][3]=2;
			boxes[boxes.length/2][3]=2;
			pivot=new Point(boxes.length/2-1,3);
		}
		
		//Z-piece
		if(nextpiece==6) {
			boxes[boxes.length/2-1][1]=2;
			boxes[boxes.length/2-1][2]=2;
			boxes[boxes.length/2][2]=2;
			boxes[boxes.length/2][3]=2;
			pivot=new Point(boxes.length/2-1,2);
		}
		
	}
	

}

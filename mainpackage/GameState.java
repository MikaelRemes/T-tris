package mainpackage;

import java.awt.event.KeyEvent;

public class GameState implements Runnable{
	
	public int[][] boxes;
	
	public int[][] userBoxes;

	public GameState(int width, int height) {
		boxes = new int[width][height];
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
			
			
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				running=false;
				e.printStackTrace();
			}
		}
	}
	

}

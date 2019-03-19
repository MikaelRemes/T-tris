package mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;


public class TetrisPanel extends JPanel{
	
	private static final long serialVersionUID = 2L;
	private final int height = 800;
	private final int width = 400;
	
	private int[][] boxes;
	

	public TetrisPanel() {
		boxes = new int[10][20];
		for(int[] xAxisBoxes : boxes) {
			for(int yAxisBox : xAxisBoxes) {
				yAxisBox=0;
			}
		}
		boxes[0][2]=1;
		boxes[0][3]=1;
		boxes[0][4]=1;
		boxes[1][2]=1;
		this.setPreferredSize(new Dimension(width,height));
		setFocusable(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i=0;i<boxes.length;i++) {
			for(int j=0;j<boxes[i].length;j++) {
				if(boxes[i][j]==0) {
					
				}
				if(boxes[i][j]==1) {
					g.setColor(Color.RED);
					g.fillRect(i*(getWidth()/boxes.length)+2, j*(getHeight()/boxes[0].length)+2, (getWidth()/boxes.length)-4, (getHeight()/boxes[0].length)-4);
				}
			}
		}

	}

}

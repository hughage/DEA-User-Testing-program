import com.leapmotion.leap.Vector;

import processing.core.PApplet;

public class Cursors {
	
	PApplet p;
	Vector position;
	
	boolean distanceLine = true;
	boolean mag3D = true ;
	
	int xSize = 10; //size of cursor
	int ySize = 10;
	int r,g,b;		//color of cursor
	int xPos, yPos; //position of cursor
	
	
	int a = 20; //missing cursor arrow size
	int left[][];
	int up[][];
	int right[][];
	int down[][];
	
	Cursors(int red, int green, int blue, PApplet parent){
		this.r = red;
		this.g = green;
		this.b = blue; 
		this.p= parent;
		position = new Vector();
		left = new int[][] {{0,0}, {a,a}, {a,-a}};
		
		down = new int[][] {{0,0}, {a,-a}, {-a,-a}};
		
		right = new int[][] {{0,0}, {-a,-a}, {-a,a}};
		
		up = new int[][] {{0,0}, {-a,a}, {a,a}};
	}
	
	public void update(Vector a){
		position = a;
		xPos = (int) position.getX();
		yPos = (int) position.getY();
		p.fill(r,g,b);
		p.ellipse(xPos,yPos,xSize,ySize);
		p.noFill();
		
		if (xPos<0){
			p.pushMatrix();
			p.translate(0, yPos);
			p.fill(r,g,b);
			p.noStroke();
			p.beginShape();
			p.vertex(left[0][0], left[0][1]);
			p.vertex(left[1][0], left[1][1]);
			p.vertex(left[2][0], left[2][1]);
			p.vertex(left[0][0], left[0][1]);
			p.endShape();
			p.popMatrix();
			p.noFill();
		}
		
		if (xPos>p.width){
			p.pushMatrix();
			p.translate(p.width, yPos);
			p.fill(r,g,b);
			p.noStroke();
			p.beginShape();
			p.vertex(right[0][0], right[0][1]);
			p.vertex(right[1][0], right[1][1]);
			p.vertex(right[2][0], right[2][1]);
			p.vertex(right[0][0], right[0][1]);
			p.endShape();
			p.popMatrix();
			p.noFill();
		}
		
		if (yPos>p.height){
			p.pushMatrix();
			p.translate(xPos, p.height);
			p.fill(r,g,b);
			p.noStroke();
			p.beginShape();
			p.vertex(down[0][0], down[0][1]);
			p.vertex(down[1][0], down[1][1]);
			p.vertex(down[2][0], down[2][1]);
			p.vertex(down[0][0], down[0][1]);
			p.endShape();
			p.popMatrix();
			p.noFill();
		}
		
		if (yPos<0){
			p.pushMatrix();
			p.translate(xPos, 0);
			p.fill(r,g,b);
			p.noStroke();
			p.beginShape();
			p.vertex(up[0][0], up[0][1]);
			p.vertex(up[1][0], up[1][1]);
			p.vertex(up[2][0], up[2][1]);
			p.vertex(up[0][0], up[0][1]);
			p.endShape();
			p.popMatrix();
			p.noFill();
		}
		
		
	}
	
	public void drawMag3D (boolean b){
		mag3D = b;
	}
	
	
	public void drawDistanceLine(Vector t, float m){
			p.strokeWeight(1);
			p.stroke(100);
			p.line(xPos, yPos, t.getX(), t.getY());
			if (mag3D){
			p.textSize(30);
			p.fill(0,150);
			//p.text(m, (Math.abs(xPos+t.getX()))/2, (Math.abs(yPos+t.getY()))/2);
			}
			p.noStroke();
			p.noFill();
	}
	
	

}

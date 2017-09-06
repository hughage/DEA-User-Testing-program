import processing.core.PApplet;

public class Button {
	
	PApplet p;
	String text;
	int index;
	int x=0,y;
	int xsize, ysize;
	int r = 207,g =207,b =196;
	boolean isSelected = false, isInitiated = false;
	
	Button(PApplet parent, int n, int total, String name){
		this.text = name;
		this.index = n;
		this.p = parent;
		y = n*p.height/total;
		ysize = p.height/total;
		xsize = p.width;
	}
	
	Button(PApplet parent, int n, int total, String name, int pos){
		this.text = name;
		this.index = n;
		this.p = parent;
		y = pos;
		ysize = p.height/total;
		xsize = p.width;
	}
	
	@SuppressWarnings("static-access")
	public void drawButton(){
		
		//p.background(255);
		
		if(p.mouseX>x && p.mouseX<x+xsize
				&& p.mouseY>y && p.mouseY<y+ysize){
			p.noStroke();
			p.fill(r,g,b);
		} else {
			p.strokeWeight(2);
			p.stroke(r,g,b);
			p.fill(255);
		}
		
		p.rect(x,y,xsize,ysize,20);
		p.textAlign(p.CENTER,p.CENTER);
		p.fill(0);
		p.text(text,p.width/2,y+(ysize/2));

		
		if(isInitiated){
			p.fill(119,190,119);
			p.noStroke();
		} else {
			p.fill(r,g,b);
			p.stroke(119,190,119);
		}
		p.ellipse(20,y+(ysize/2),30,30);
		
		p.noFill();
		p.noStroke();
		
	}
	
	public void click(){
		if(p.mouseX>x && p.mouseX<x+xsize
				&& p.mouseY>y && p.mouseY<y+ysize){
			isSelected = !isSelected;
			isInitiated = true;
		} else {
			//isSelected = false;
		}
	}
	
	

}

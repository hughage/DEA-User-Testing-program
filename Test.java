import processing.core.*;

public class Test extends PApplet { 
	
	String cock = "Cock";
	
	
	Test(){
		String[] a = {""};
		PApplet.runSketch(a, this);
	}
	
	
	  public void settings() {
			smooth(8);
			size(100,100);
			//fullScreen();
		  }

	  public void draw() {
		    background(255);
		    fill(0);
		    ellipse(100, 50, 10, 10);
		    textAlign(CENTER,CENTER);
		    text(cock,width/2,height/2);
		  }
	  
	  public void setText (String t){
		  
		  this.cock = t; 
		  
	  }
	  
}


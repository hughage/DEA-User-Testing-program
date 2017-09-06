import com.leapmotion.leap.Vector;
import processing.core.PApplet;
import processing.core.PImage;

public class GraspSizeTest extends PApplet { 
	
	Leap myLeap;
	Vector index, thumb;
	Cursors iCursor, tCursor;	

	String instructions = "Make the gesture in picture please";
	
	PImage img;
	
	boolean visible = true; 
	float currentGraspDistance = 0.0f;
	
	
	GraspSizeTest (){
		String[] a = {""};
		PApplet.runSketch(a, this);
	}
	
	
	  public void settings() {
			smooth(8);
			//size(500,500);
			fullScreen(P3D,1);
		  }
	  
	  
	  public void setup(){
		  
		  myLeap = new Leap(width,height,100);
		  Vector[] temp = myLeap.getIndexThumbPos();
		  index = temp[0];
		  thumb = temp[1];		  
		  iCursor = new  Cursors (232,123,234,this);
		  tCursor = new  Cursors (123,123,234,this);
		  
		  img = loadImage("Assets/GraspImage.jpg");

	  
	  }

	  
	  public void draw() {
		  
		  if (myLeap.leap.isConnected()){
		    myLeap.update();
		    index = myLeap.indexCorrected;
		    thumb = myLeap.thumbCorrected;
		  }
		  
		  if (myLeap.inIdealVolume()){
			  background(255);
			  instructions = "Make the gesture in picture please";
		  } else {
			 background(200); 
			 instructions = "Please make sure your hand is directly above the LEAP motion";
		  }
		  
		  imageMode(CENTER);
		  image(img, width/2, height/2, width/3, height/2);
		  
		  textAlign(CENTER,CENTER);
		  textSize(40);
		  fill(0);
		  text(instructions, width/2, height-height/6);
		  iCursor.update(index);	  
		  tCursor.update(thumb);
		  currentGraspDistance =  myLeap.index.distanceTo(myLeap.thumb);
		  iCursor.drawDistanceLine(thumb, currentGraspDistance);	  
		  }
	  
	  public void setText (String t){
		  
		  this.instructions = t; 
		  
	  }
	  
	  public float getCurrentGraspDistance(){
		  return currentGraspDistance;
	  }
	  
	  public void running(boolean g){
		  if(!g){
		  this.noLoop();
		  surface.setVisible(false);
		  } else {
			  this.loop();
			  surface.setVisible(true);
		  }
	  }
	  
	  
}


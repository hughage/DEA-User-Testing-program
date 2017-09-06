import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class SingleFingerJNDUserScreen extends PApplet {

	Leap myLeap;
	Arduinos arduino;
	Vector index;
	Cursors iCursor, tCursor;	
	TouchObject left, right; 
	TouchObject[] objects;
	//Haptic scene; 
	HapticSingleFinger scene;
	
	String leftText = "";
	String rightText = "";
	int textHeight = 100;
	int textsize = 60;
	int []textColour = {0,0,0};
	
	
	
	SingleFingerJNDUserScreen (Arduinos ard){
		this.arduino = ard;
		String[] a = {""};
		PApplet.runSketch(a, this);
	}
	
	
	  public void settings() {			
			fullScreen(P3D,1);
			smooth(8);		  
		  }
	  
	  
	  public void setup(){		  		  
		  myLeap = new Leap(width,height,100);
		 // Vector[] temp = myLeap.getIndexThumbPos();
		  index = myLeap.getPalmPos();  
		  iCursor = new  Cursors (232,123,234,this);
		  left = new TouchObject(width/4,height/2, 119, 190, 119, this);
		  right = new TouchObject(3*(width/4),height/2, 119, 190, 119, this);
		  objects = new TouchObject[2];
		  objects[0] = left;
		  objects[1] = right;		  
		  scene = new HapticSingleFinger(objects, arduino);	
		  textAlign(CENTER,CENTER);
		  
	  }

	  
	  public void draw() {
		  
		  if (myLeap.leap.isConnected()){
		    myLeap.update();
		    index = myLeap.getPalmPos();
		  }	
		  
		  if (myLeap.inIdealVolume()){
			  background(255);	   
		  } else {
				// background(150); 
				 background(255);
		  }
		  
		  fill(textColour[0],textColour[1],textColour[2]);
		  textSize(textsize);
		  text(leftText,width/4,height-textHeight);
		  text(rightText,3*(width/4),height-textHeight);
		  noFill();
		    
		  left.update();
		  right.update();		  
		  Vector[] temp = {index};
		  scene.collsion(temp);				  
		  iCursor.update(index);	  

		  }
	  
	  public void setText (String t){
	  }
	  
	  public void setHapticResponce(int l, int r){
		  left.setHapticResponce(l);
		  right.setHapticResponce(r);
	  }
	  
	  public void running(boolean g){
		  if(!g){
		  this.noLoop();
		  surface.setVisible(false);
		  arduino.off();
		  } else {
			  this.loop();
			  surface.setVisible(true);
		  }
	  }
	  
	  
	  
}



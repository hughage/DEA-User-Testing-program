import com.leapmotion.leap.Vector;

import processing.core.PApplet;

public class SingleFingerJNDTesterScreen extends PApplet  {

	Leap myLeap;
	Arduinos arduino;
	Vector index;
	Cursors iCursor;	
	TouchObject left, right; 
	TouchObject[] objects;
	Haptic scene; 
	
	int[][] ABTouchCount;
	
	int curentTest = 0; 
	

	
	SingleFingerJNDTesterScreen( Arduinos ard){
			
		String[] a = {""};
		PApplet.runSketch(a, this);
		this.arduino = ard;
		
	}
	
	public void setABTouchCount(int numberOfTests){
		ABTouchCount = new int[numberOfTests][2];
	}
	

	
		  public void settings() {
				smooth(8);
				fullScreen(P3D,1);
			  }
		  
		  
		  public void setup(){
			  
			  myLeap = new Leap(width,height,100);
			  index = myLeap.getPalmPos();   
			  iCursor = new  Cursors (232,123,234,this);

			  left = new TouchObject(width/4,height/2, 119, 190, 119, this, "A");
			  right = new TouchObject(3*(width/4),height/2, 119, 190, 119, this, "B");
			  objects = new TouchObject[2];
			  objects[0] = left;
			  objects[1] = right;

			  
			  scene = new Haptic(objects, arduino);
			  
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
			    
			  left.update();
			  right.update();
			  
			  Vector[] temp = {index};
			  scene.collsion(temp);		
			  
			  iCursor.update(index);	 
			
			  
			  }
		  
		  public void setText (String t){ 
		  }
		  
		  public void setHapticResponce(int a, int b, int test){
			  left.setHapticResponce(a);
			  right.setHapticResponce(b);		  
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



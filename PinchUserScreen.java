import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class PinchUserScreen extends PApplet{
	
	Leap myLeap;
	Arduinos arduino;
	Vector index, thumb;
	ThreeDCursor iCursor, tCursor;
	PinchObject pinchObjectR;
	PinchObject pinchObjectL;
	
	String leftText = "";
	String rightText = "";
	int textHeight = 100;
	int textsize = 60;
	int []textColour = {0,0,0};
	float realDistance; //real distance in mm between thumb and index, used for haptic response not visuals
	
	float pMax; //value of the maximum hand pinch size from the grasp size class
	boolean drawInfo = false;

	PinchUserScreen(Arduinos ard, float pinchMaxValue) {
		this.arduino = ard;
		String[] a = {""};
		PApplet.runSketch(a, this);
		this.pMax = pinchMaxValue;
	}
	
	public void settings() {
		smooth(8);
		fullScreen(P3D,1);
	}
	
	public void setup(){
		  myLeap = new Leap(width,height,width);
		  Vector[] temp = myLeap.getIndexThumbPos();
		  index = temp[0];
		  thumb = temp[1];
		  iCursor = new  ThreeDCursor (232,123,234,this);
		  tCursor = new  ThreeDCursor (123,123,234,this);

		  pinchObjectL = new PinchObject (0,255,100,0,width/2,100,0, 0.75f,0.25f, pMax,myLeap.getpMaxScaler(),this);
		  pinchObjectR = new PinchObject (23,100,255,width/2,width,100,0,0.75f,0.25f,pMax,myLeap.getpMaxScaler(), this);	  
	}
	
	public void draw() {
		
		lights();
		
		  if (myLeap.leap.isConnected()){
		    myLeap.update();
		    realDistance= myLeap.index.distanceTo(myLeap.thumb);
		    index = myLeap.indexCorrected;
		    thumb = myLeap.thumbCorrected;
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
		  
		  if(drawInfo){
			  displayInfo();
		  }
		  

		  noFill();
		  
		  iCursor.update(index);	  
		  tCursor.update(thumb);
		  
		  pinchObjectL.update(iCursor, tCursor, realDistance);
		  pinchObjectR.update(iCursor, tCursor, realDistance);
		  haptics();
		    
		  drawSeperatorDots();
		
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
	
	public void haptics(){
		if (pinchObjectL.onSide){	
			arduino.setAB(pinchObjectL.getHapticResult()); 
		} 
		else if (pinchObjectR.onSide){	
			arduino.setAB(pinchObjectR.getHapticResult()); 
		} else {
			arduino.off();
		}	
	}
	
	private void displayInfo(){
		  textSize(10);
		  text("RealDistance: "+realDistance,40, 60);
		  text("Start pinch at: "+pinchObjectL.pinchUpperThreshold,40, 80);
		  text("Stop pinch at: "+pinchObjectL.pinchLowerThreshold,40, 100);
		  text("AveragePinchVal: "+pMax,40, 120);
		  
		  text("Corrected Distance: "+index.distanceTo(thumb),40, 140);
		  text("Screen correction scaler "+myLeap.getpMaxScaler(),40, 160);
		  text("PinchBall max size: "+ myLeap.getpMaxScaler()* pinchObjectL.pinchUpperThreshold,40, 180);
		  text("PinchBall min size: "+ myLeap.getpMaxScaler()* pinchObjectL.pinchLowerThreshold,40, 200);
		  text("PinchBall current scale factor: "+ pinchObjectL.scaleFactor,40, 220);
		  text("PinchBall current size: "+ pinchObjectL.pinchUpperThreshold*pinchObjectL.screenCorrectionScaller*pinchObjectL.scaleFactor,40, 240);
		  
	}
	
	public void setHapticResponce(int changeTop, int changeBottom, int reffTop, int reffBottom){
		pinchObjectL.setHapticResultBounds(changeTop, changeBottom);
		pinchObjectR.setHapticResultBounds(reffTop,reffBottom);
	  }
	
	public void drawSeperatorDots(){
		int seperatorDots = 30; 
		  for (int i=0; i<seperatorDots+1; i++){
			  noStroke();
			  fill(24,230,178);
			  ellipse(width/2,i*(height/seperatorDots),20,20);
			  noFill();
		  }
	}
}

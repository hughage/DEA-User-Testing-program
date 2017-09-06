import processing.core.PApplet;


public class PinchController2  extends PApplet{
	
	PinchUserScreen2 pinchUserScreen2;
	Button quit;
	
	
	int spaceing = 0;
	int spaces = 8;
	float pMax; //value of the maximum hand pinch size from the grasp size class

	float[][] changeValues; //a 3 by 5 array to store my test values
	int jndTests = 3; //number of reference tests 255-0, 255-50, 255-100, 255-150, 255-200
	int jndSubTests = 3; // number of tests in each reference test
	int currentTest = 0; // which reference test 255-0, 255-50, 255-100, 255-150, 255-200
	int currentSubTest = 0; //which repeat for test 1,2,3,4,5
	float pwmMaxValue = 0.5f; //maximum output (255 = 5V)
	int refferenceValues[] = {100,66,33}; // change to 0,50,100 for DEA version
	float average[] = new float[3]; //store my average pinch jnd values
	float variance[] = new float[3];
	int[][] averagesForJNDTest = new int[3][2];
	
	
	
	PinchController2(float average, Arduinos ard) {
		String[] a = {""};
		PApplet.runSketch(a, this);
		pinchUserScreen2 = new PinchUserScreen2(ard, average);
		this.pMax = average;
	}
	
	public void settings() {
		size(500,500);
	}
	
	public void setup(){
		changeValues = new float[jndTests][jndSubTests];
		for (int i=0; i< changeValues.length; i++){
			 for (int j = 0; j<changeValues[i].length; j++){
				 changeValues[i][j]=0.5f;
			 }
		 }
		 
		background(255);
		spaceing = height/(changeValues.length+spaces);
		quit = new Button(this,1,8,"Quit",height-(int)((float)spaceing*1.5f));
		delay(3000);
		
		//setHapticResponce();	
		recalculate();	
	}
	
	public void draw() {
		background(255);	  	  
		fill(0);
		textSize(20);
		textAlign(CENTER, CENTER);
		
		text("Current Test Number: "+(currentTest+1) +"\nRefference Value: "+refferenceValues[currentTest], width/2, spaceing);
		
		for (int i=0; i<jndSubTests; i++){
			  if(i==currentSubTest){
				  fill(230,130,20);
			  } 
			  text(i+1, width/3, (i+3)*spaceing);
			  text(changeValues[currentTest][i], 2*(width/3), (i+3)*spaceing); 
			  fill(0);
		  }
		
		  text("Average:", width/3, (changeValues.length+5)*spaceing);
		  text(average[currentTest], 2*(width/3), (changeValues.length+5)*spaceing);
		  
		  text("Variance:", width/3, (changeValues.length+6)*spaceing);
		  text(variance[currentTest], 2*(width/3), (changeValues.length+6)*spaceing);
		  
		
		quit.drawButton();
	}
	
	  public void keyReleased(){
		  if (key == '1') {
			  currentTest = 0;	      
		    }
		  if (key == '2') {
			  currentTest = 1;
		    }
		  if (key == '3') {
			  currentTest = 2;		      
		    }

		  
		  if (key == CODED) {
			    if (keyCode == DOWN) {
			    	if(currentSubTest<jndSubTests-1){
			    	currentSubTest = currentSubTest +1;
			    	}else {
			    		currentSubTest = 0;
			    	}
			    }
			    if (keyCode == UP) {
			    	if(currentSubTest>0){
			    	currentSubTest = currentSubTest -1;
			    	} else {
			    		currentSubTest = jndSubTests-1;
			    	}
			    }	   	    
		  }
		  recalculate();
		  setHapticResponce();
	  }
	  
	  public void keyPressed(){
		  if (key == CODED) {
		    if (keyCode == RIGHT) {
		    	float v = changeValues[currentTest][currentSubTest];
		    	if(v<pwmMaxValue){
		    		changeValues[currentTest][currentSubTest] = v + 0.01f;
		    		setHapticResponce();
		    	}
		    	recalculate();
		    }
		    if (keyCode == LEFT) {
		     	float v = changeValues[currentTest][currentSubTest];
		    	if(v> 0.01){
			    	changeValues[currentTest][currentSubTest] = v - 0.01f;
			    	setHapticResponce();
		    	}
		    	recalculate();
		    }
		  }
		  }
		  
	  	  
	  private void setHapticResponce(){
		  pinchUserScreen2.setHapticResponce(changeValues[currentTest][currentSubTest],0.5f,refferenceValues[currentTest]); //setValue range
	  }
	  
	  private void recalculate(){
		  getAverage();
		  getVariance();
		  for (int i = 0; i< averagesForJNDTest.length; i++){
				  averagesForJNDTest[i][0] = refferenceValues[i];
				  averagesForJNDTest[i][1] = (int) average[i];
		  }
	  }
	  
	  public void getAverage(){
		  
		  for(int j = 0; j<changeValues.length; j++){
			  float sum = 0.0f;
		  for (int i=0; i<changeValues[j].length; i++){
			  sum = sum + changeValues[j][i];
		  }
		  average[j] = sum/changeValues[j].length;
		  }//return average[currentTest];
	  }
	  
	  public void getVariance(){
		  
		  for(int j = 0; j<changeValues.length; j++){
		  float[] meanDifSquared = new float[changeValues[j].length];
		  float meanDifSquaredSum = 0.0f;
		  for (int i=0; i<changeValues[j].length; i++){
			  meanDifSquared[i]= (float) Math.pow((average[j]-changeValues[j][i]), 2);
			  meanDifSquaredSum = meanDifSquaredSum + meanDifSquared[i];
		  }
		  variance[j] = meanDifSquaredSum/(changeValues[j].length-1);
		  }//return variance[currentTest];
	  }
	  
	  public int[][] getAveragesForJNDTest(){
		  return averagesForJNDTest;
	  }
	  
	  public void mouseReleased(){

		  quit.click();	  
		  if (quit.isSelected){		
			  quit.isSelected = false;
			  this.running(false);
			 
		  } else {
			  this.running(true);
			  //quit.isSelected = true;
		  }
	  }
	
	public void running(boolean g){
		if(!g){
			this.noLoop();
			surface.setVisible(false);
			pinchUserScreen2.running(false);
		} else {
			this.loop();
			surface.setVisible(true);
			pinchUserScreen2.running(true);
		  }
	  }

}

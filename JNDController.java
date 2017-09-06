import processing.core.PApplet;


public class JNDController extends PApplet { 
	
	JNDUserTestScreen userTestScreen;
	Button quit;
	
	int[][] changeValues;
	int jndTests = 5; //number of reference tests 255-0, 255-50, 255-100, 255-150, 255-200
	int jndSubTests = 3; // number of tests in each reference test
	int currentTest = 0; // which reference test 255-0, 255-50, 255-100, 255-150, 255-200
	int currentSubTest = 0; //which repeat for test 1,2,3,4,5;
	int testValue = 0; // value for increase of base PWM value;
	int maxValue =100;
	int refferenceValues[] = {100,80,60,40,20};
	
	int spaceing = 0;
	int spaces = 8;
	float currentGraspDistance = 0.0f;
	float average[] = new float[5];
	float variance[] = new float[5];
	int[][] averagesForJNDTest = new int[5][2];
	
	//boolean delay = true;
	
	JNDController (Arduinos ard){
		String[] a = {""};
		PApplet.runSketch(a, this);
		userTestScreen = new JNDUserTestScreen(ard);
	}
	
	
	  public void settings() {

			size(500,500);
		  }
	  
	  
	  public void setup(){
		  changeValues = new int[jndTests][jndSubTests];
		  spaceing = height/(changeValues.length+spaces);
		  quit = new Button(this,1,8,"Quit",height-(int)((float)spaceing*2));
		  
		 for (int i=0; i< changeValues.length; i++){
			 for (int j = 0; j<changeValues[i].length; j++){
				 changeValues[i][j]=refferenceValues[i];
			 }
		 }
		 
		 delay(3000);
		 setHapticResponce();	
		 recalculate();
	  }

	  
	  public void draw() {
		  background(255);	  
		  
		  fill(0);
		  textSize(20);
		  textAlign(CENTER, CENTER);
		  
//		  if(delay){
//			  delay(3000);
//			  delay = false;	  
//		  } 
		  

		  text("Current Test Number: "+(currentTest+1) +"\nRefference Value: "+refferenceValues[currentTest], width/2, spaceing);
		  
		  
		  text("Test Number: ", width/3, 3*spaceing);
		  text("Results", 2*(width/3), 3*spaceing);
		  
		  for (int i=0; i<changeValues[i].length; i++){
			  if(i==currentSubTest){
				  fill(230,130,20);
			  } 
			  text(i+1, width/3, (i+4)*spaceing);
			  text(changeValues[currentTest][i], 2*(width/3), (i+4)*spaceing); 
			  fill(0);
		  }
		  
		  text("Average:", width/3, (changeValues.length+4)*spaceing);
		  text(average[currentTest], 2*(width/3), (changeValues.length+4)*spaceing);
		  
		  text("Variance:", width/3, (changeValues.length+5)*spaceing);
		  text(variance[currentTest], 2*(width/3), (changeValues.length+5)*spaceing);
		  
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
		  if (key == '4') {
			  currentTest = 3;
		    }
		  if (key == '5') {
			  currentTest = 4;
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
			    	int v = changeValues[currentTest][currentSubTest];
			    	if(v<refferenceValues[currentTest]){
			    		changeValues[currentTest][currentSubTest] = v +1;
			    		setHapticResponce();
			    	}
			    	recalculate();
			    }
			    if (keyCode == LEFT) {
			     	int v = changeValues[currentTest][currentSubTest];
			    	if(v> (0)){
				    	changeValues[currentTest][currentSubTest] = v -1;
				    	setHapticResponce();
			    	}
			    	recalculate();
			    }
			  }
		  }
		  
	  
	  
	  private void setHapticResponce(){
		  userTestScreen.setHapticResponce(changeValues[currentTest][currentSubTest],refferenceValues[currentTest]);
	  }
	  
	  private void recalculate(){
		  getAverage();
		  getVariance();
		  for (int i = 0; i< averagesForJNDTest.length; i++){
			  averagesForJNDTest[i][0] = refferenceValues[i];
			  averagesForJNDTest[i][1] = refferenceValues[i] - (int) average[i];
	  }
	  }
	  
	  public void getAverage(){
		  
		  for(int j = 0; j<changeValues.length; j++){
			  float sum = 0.0f;
		  for (int i=0; i<changeValues[j].length; i++){
			  sum = sum + (refferenceValues[j]-changeValues[j][i]);
		  }
		  average[j] = sum/changeValues[j].length;
		  }//return average[currentTest];
	  }
	  
	  public void getVariance(){
		  
		  for(int j = 0; j<changeValues.length; j++){
		  float[] meanDifSquared = new float[changeValues[j].length];
		  float meanDifSquaredSum = 0.0f;
		  for (int i=0; i<changeValues[j].length; i++){
			  meanDifSquared[i]= (float) Math.pow((average[j]-(refferenceValues[j]-changeValues[j][i])), 2);
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
		  userTestScreen.running(false);
		  } else {
			  this.loop();
			  surface.setVisible(true);
			  userTestScreen.running(true);
		  }
	  }
	  
	  
	  
}


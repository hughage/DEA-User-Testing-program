import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import processing.core.PApplet;


public class PinchController  extends PApplet{
	
	PinchUserScreen pinchUserScreen;
	Button quit;
	
	Recorder rec;
	int sampleCount=0;
	int numberOfSamples = 1;
	int[] CurrentTestCount; // for saving the data, for each time a test is run, the number associated to that test increases
	
	int spaceing = 0;
	int spaces = 8;
	float pMax; //value of the maximum hand pinch size from the grasp size class

	int[][] changeValues; //a 3 by 3 array to store my test values
	int jndTests = 3; //number of reference tests
	int jndSubTests = 3; // number of tests in each reference test
	int currentTest = 0; // which reference test
	int currentSubTest = 0; //which repeat for test 1,2,3,4,5
	int maxValue =100; //maximum output (0-100)
	int refferenceValues[] = {100,66,33}; // change to 0,50,100 for DEA version
	float average[] = new float[3]; //store my average pinch jnd values
	float variance[] = new float[3];
	int[][] averagesForJNDTest = new int[3][2];
	
	int[][] testsValues;
	
	//final
	int[] aValues = {30,60,50,65,10,40,35,40,100,80,100,100,80,50,80,70,80,60};
	
	int[] bValues = {10,40,60,60,40,20,40,40,70,100,90,100,40,80,60,80,80,20};
	
	
	PinchController(float average, Arduinos ard) {
		String[] a = {""};
		PApplet.runSketch(a, this);
		pinchUserScreen = new PinchUserScreen(ard, average);
		this.pMax = average;
	}
	
	public void settings() {
		size(500,1000);
	}
	
	public void setup(){
		 spaces = aValues.length +5;
		 spaceing = height/(spaces);
		 quit = new Button(this,1,spaces,"Quit",height-(int)(spaceing*2));
	 
		 if(aValues.length == bValues.length){
			 
			 testsValues = new int[aValues.length][4];  
			 
			 for(int i=0; i<testsValues.length;i++){
				 testsValues[i][0]=aValues[i];
				 testsValues[i][1]=bValues[i];
				 if(aValues[i]<bValues[i]){
					 testsValues[i][2]=bValues[i];
				 } else if(aValues[i]>bValues[i]){
					 testsValues[i][2]=aValues[i];
				 } else {
					 testsValues[i][2]=666;
				 }
			 }
			 
		 } else {
			 print("\naValues: ");
			 for(int t: aValues){
				 print(t+"; ");
			 }
			 print("\nbValues: ");
			 for(int t: bValues){
				 print(t+"; ");
			 }
			 println("a and b values not the same length, change in JND test controller class");
			 
			 exit();
		 }
		 
		 testsValues= shuffleArray(testsValues);	 
		 delay(3000);
		 
		 CurrentTestCount = new int[testsValues.length];
		 newPosOutput();
		 
		 setHapticResponce();
		
		
	}
	
	public void draw() {
		background(255);
		
		recSample();
		  
		  fill(0);
		  textSize(20);
		  textAlign(CENTER, CENTER);
		  

		  text("Current Test Number: "+(currentTest+1), width/2, spaceing);
		  	  

		  text("Test:", width/6,(2*spaceing));
		  text("Green", 2*(width/6),(2*spaceing));
		  text("Blue", 3*(width/6),(2*spaceing));
		  text("M", 4*(width/6),(2*spaceing));
		  text("-", 5*(width/6),(2*spaceing));
		  
		  for (int i=0; i<testsValues.length; i++){
			  if(i==currentTest){
				  fill(230,130,20);
			  } else{
				  fill(0);
			  }
			  text((i+1), width/6,(3*spaceing)+(i*spaceing));
			  text(testsValues[i][0], 2*(width/6),(3*spaceing)+(i*spaceing));
			  text(testsValues[i][1], 3*(width/6),(3*spaceing)+(i*spaceing));
			  text(testsValues[i][2], 4*(width/6),(3*spaceing)+(i*spaceing));
			  if(testsValues[i][3]==666){
				  text("Same", 5*(width/6),(3*spaceing)+(i*spaceing));
			  } else {
				  text(testsValues[i][3], 5*(width/6),(3*spaceing)+(i*spaceing));
			  } 
			  }	  
		  
		  quit.drawButton();	  
	}
	
	private void newPosOutput(){
		 CurrentTestCount[currentTest] = CurrentTestCount[currentTest]+1;
		 String temp = "TestNum"+currentTest+"Repeated"+CurrentTestCount[currentTest]+"_";
		 rec = new Recorder(this,temp,"PinchTest");
		 rec.newTest(testsValues[currentTest][0],testsValues[currentTest][1],testsValues[currentTest][2]);
 }

	public void recSample(){
	  sampleCount++;
	  if(sampleCount==numberOfSamples){
	  rec.update(pinchUserScreen.index,pinchUserScreen.thumb,millis(),pinchUserScreen.arduino.previousA);
	  sampleCount=0;
	  }
	}
	
	  public void keyReleased(){

			  
			  if (key == CODED) {
				    if (keyCode == DOWN) {
				    	if (currentTest<testsValues.length-1){
				    	currentTest++;
				    	} else {
				    		currentTest =0;
				    	} 
				    	rec.close();
				    	newPosOutput();
				    }
				    if (keyCode == UP) {
				    	if (currentTest>0){
				    	currentTest--;
				    	} else {
				    		currentTest = testsValues.length-1;
				    	}
				    	rec.close();
				    	newPosOutput();
				    }	   	    
			  }
			  recalculate();
			  setHapticResponce();	  
		  }
	  
	  
	  public void keyPressed(){
		  
		  if (key == 'a'|| key == 'A'){
			  testsValues[currentTest][3] = testsValues[currentTest][0];
		  }
		  
		  if (key == 'b'|| key == 'B'){
			  testsValues[currentTest][3] = testsValues[currentTest][1];
		  }
		  if (key == 's'|| key == 'S'){
			  testsValues[currentTest][3] = 666;
		  }
		  
		  if (key == CODED) {
		    if (keyCode == RIGHT) {

		    	}
		    }
		    if (keyCode == LEFT) {

		    }
		  }
		  
	  
	  
	  private void setHapticResponce(){
		  pinchUserScreen.setHapticResponce(testsValues[currentTest][0],0,testsValues[currentTest][1],0); //setValue range
	  }
	  
	  private void recalculate(){
//		  getAverage();
//		  getVariance();
//		  for (int i = 0; i< averagesForJNDTest.length; i++){
//				  averagesForJNDTest[i][0] = refferenceValues[i];
//				  averagesForJNDTest[i][1] = (int) average[i];
//		  }
	  }
	  
	  public void getAverage(){
		  
//		  for(int j = 0; j<changeValues.length; j++){
//			  float sum = 0.0f;
//		  for (int i=0; i<changeValues[j].length; i++){
//			  sum = sum + changeValues[j][i];
//		  }
//		  average[j] = sum/changeValues[j].length;
//		  }//return average[currentTest];
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
		  }
	  }
	  
	  public int[][] getAveragesForJNDTest(){
		  return averagesForJNDTest;
	  }
	  
	  public void mouseReleased(){

		  quit.click();	  
		  if (quit.isSelected){		
			  quit.isSelected = false;
			  rec.close();
			  this.running(false);
			 
		  } else {
			  this.running(true);
			  //quit.isSelected = true;
		  }
	  }
	  
	  static int[][] shuffleArray(int[][] ar){
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--){
	    	
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a[] = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    return ar;
	  }
	  

	
	public void running(boolean g){
		if(!g){
			this.noLoop();
			surface.setVisible(false);
			pinchUserScreen.running(false);
		} else {
			this.loop();
			surface.setVisible(true);
			pinchUserScreen.running(true);
		  }
	  }

}

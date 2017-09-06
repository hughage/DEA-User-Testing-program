import processing.core.PApplet;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class JNDTestController extends PApplet { 
	
	JNDTesterScreen jndTesterScreen;
	Button quit;
	
	int spaceing = 0;
	int spaces = 10;
	
	int currentTest = 0;
	
	Recorder rec;
	int sampleCount=0;
	int numberOfSamples = 1;
	int[] CurrentTestCount; // for saving the data, for each time a test is run, the number associated to that test increases
	
	
	int[][] averagesForJNDTest;
	int[][] testsValues;//for each average, test if people can identify the threshold value and the reff value [test number][reff,jnd,tester,result(reff or jnd)]
	
	
	//final
	int[] aValues = {100,80,100,100,80,50,80,70,80,60,30,60,50,65,10,40,35,40};
	int[] bValues = {70,100,90,100,40,80,60,80,80,20,10,40,60,60,40,20,40,40};
	
	int[]left;
	int[] right;
	
	int[] randomSelection;
	
	JNDTestController ( Arduinos ard){
		String[] a = {""};
		PApplet.runSketch(a, this);
		//this.averagesForJNDTest = b;		
		System.out.println("dog");
		jndTesterScreen = new JNDTesterScreen( ard);		
	}
	
	
	  public void settings() {
			size(500,1000);
		  }
	  
	  
	  public void setup(){	 
		
		 spaces = aValues.length +5;
		 spaceing = height/(spaces);
		 quit = new Button(this,1,spaces,"Quit",height-(int)(spaceing*2));
	 
		 if(aValues.length == bValues.length){
			 
			 testsValues = new int[aValues.length][6];  
			 
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
		  text("A", 2*(width/6),(2*spaceing));
		  text("B", 3*(width/6),(2*spaceing));
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
			 rec = new Recorder(this,temp,"MultiFingerTest");
			 rec.newTest(testsValues[currentTest][0],testsValues[currentTest][1],testsValues[currentTest][2]);
	  }
	 
	 public void recSample(){
		  sampleCount++;
		  if(sampleCount==numberOfSamples){
		  rec.update(jndTesterScreen.index,millis(),jndTesterScreen.arduino.previousA);
		  sampleCount=0;
		  }
	 }

	  
	  public void keyReleased(){

		  
		  if (key == CODED) {
			    if (keyCode == DOWN) {
			    	getTouchData();
			    	if (currentTest<testsValues.length-1){
			    	currentTest++;
			    	} else {
			    		currentTest =0;
			    	}  	
			    	
			    	rec.close();
			    	newPosOutput();
			    	resetTouchData();
			    }
			    if (keyCode == UP) {
			    	getTouchData();
			    	if (currentTest>0){
			    	currentTest--;
			    	} else {
			    		currentTest = testsValues.length-1;
			    	}
			    	rec.close();
			    	newPosOutput();
			    	resetTouchData();
			    }	   	    
		  }
		 
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
		  
		  
	  static int[][] shuffleArray(int[][] ar)
	  {
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
	  
	  private void setHapticResponce(){
		  jndTesterScreen.setHapticResponce(testsValues[currentTest][0],testsValues[currentTest][1],testsValues[currentTest][2]);
	  }
	  
	  private void getTouchData(){
		  testsValues[currentTest][4] = testsValues[currentTest][4]+jndTesterScreen.left.getTouchCount();
		  testsValues[currentTest][5] = testsValues[currentTest][5]+jndTesterScreen.right.getTouchCount();
	  }
	  
	  private void resetTouchData(){
		  jndTesterScreen.left.resetTouchCount();
		  jndTesterScreen.right.resetTouchCount();
	  }
	
	  
	  public int[][] getAveragesForJNDTest(){
		  return averagesForJNDTest;
	  }
	  
	  public void mouseReleased(){

		  quit.click();	  
		  if (quit.isSelected){		
			  quit.isSelected = false;
			  resetTouchData();
			  rec.close();
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
		  jndTesterScreen.running(false);
		  } else {
			  this.loop();
			  surface.setVisible(true);
			  jndTesterScreen.running(true);
		  }
	  }
	  
	  
	  
}




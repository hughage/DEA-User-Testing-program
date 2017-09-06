
import processing.core.PApplet;


public class GraspTestController extends PApplet { 
	
	GraspSizeTest graspSizeTest;
	
	Button finish;
	float[] results;
	int spaceing = 0;
	float currentGraspDistance = 0.0f;
	float average = 0.0f;
	float variance = 0.0f;
	
	boolean delay = true;
	
	GraspTestController (){
		String[] a = {""};
		PApplet.runSketch(a, this);
		graspSizeTest = new GraspSizeTest();
	}
	
	
	  public void settings() {
			smooth(8);
			size(500,500);
			
		  }
	  
	  
	  public void setup(){
		    results = new float[5];
		    spaceing = height/(results.length+6);
		    finish = new Button(this,1,8,"Finshed",(int)(((float)results.length+4.5f)*(float)spaceing));
	  }

	  
	  public void draw() {
		  background(255);	  
		  
		  fill(0);
		  textSize(20);
		  textAlign(CENTER, CENTER);
		  
		  if(delay){
			  delay(3000);
			  delay = false;	  
		  } 
		  
		  currentGraspDistance = graspSizeTest.getCurrentGraspDistance();
		  text(currentGraspDistance, width/2, spaceing);
		  
		  
		  text("Test Number", width/3, 2*spaceing);
		  text("Results", 2*(width/3), 2*spaceing);
		  
		  for (int i=0; i<results.length; i++){
			  text(i+1, width/3, (i+3)*spaceing);
			  text(results[i], 2*(width/3), (i+3)*spaceing); 
		  }
		  
		  text("Average:", width/3, (results.length+3)*spaceing);
		  text(average, 2*(width/3), (results.length+3)*spaceing);
		  
		  text("Variance:", width/3, (results.length+4)*spaceing);
		  text(variance, 2*(width/3), (results.length+4)*spaceing);
		  
		  finish.drawButton();
		  
	  }

	  
	  public void keyReleased(){
		  if (key == '1') {
		      results[0]=currentGraspDistance;
		      recalculate();
		    }
		  if (key == '2') {
		      results[1]=currentGraspDistance;
		      recalculate();
		    }
		  if (key == '3') {
		      results[2]=currentGraspDistance;
		      recalculate();
		    }
		  if (key == '4') {
		      results[3]=currentGraspDistance;
		      recalculate();
		    }
		  if (key == '5') {
		      results[4]=currentGraspDistance;
		      recalculate();
		    }
	  }
	  
	  private void recalculate(){
		  getAverage();
		  getVariance();
	  }
	  
	  public float getAverage(){
		  float sum = 0.0f;
		  for (int i=0; i<results.length; i++){
			  sum = sum + results[i];
		  }
		  average = sum/results.length;
		  return average;
	  }
	  
	  public float getVariance(){
		  float[] meanDifSquared = new float[results.length];
		  float meanDifSquaredSum = 0.0f;
		  for (int i=0; i<results.length; i++){
			  meanDifSquared[i]= (float) Math.pow((average-results[i]), 2);
			  meanDifSquaredSum = meanDifSquaredSum + meanDifSquared[i];
		  }
		  variance = meanDifSquaredSum/(results.length-1);
		  return variance;
	  }
	  
	  public void mouseReleased(){
		  
		  finish.click();	  
		  if (finish.isSelected){		  
			  this.running(false);
			  finish.isSelected = false;
		  } else {
			  this.running(true);
			 // finish.isSelected = true;
		  }
	  }
	  
	  public void running(boolean g){
		  if(!g){
		  this.noLoop();
		  surface.setVisible(false);
		  graspSizeTest.running(false);
		  } else {
			  this.loop();
			  surface.setVisible(true);
			  graspSizeTest.running(true);
		  }
	  }
	  
	  
	  
}


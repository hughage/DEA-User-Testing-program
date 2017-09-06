
import processing.core.PApplet;
import processing.serial.Serial;
import cc.arduino.*;

public class Arduinos {

	Arduino arduino;
	PApplet p;	
	int baudRate = 57600;
	String ard = "/dev/tty.usbmodemFD131";	
	String[] stringStore = {"blah","blah","blah","blah","blah","blah","blah","blah","blah"};
	int stringCount =0;	
	int maxArduinoValue = 35, minArduinoValue = 160;
	float onThreshold = 0.00f; // smallest value (0-1) that has to be exceeded before applying the minimum start pwm value minArduinoValue	
	float multiplyValue;	
	int previousA, previousB; 
	
	boolean vibeMode = false;
	int vibeMaxArduinoValue = 255, vibeMinArduinoValue = 35;
	float vibeOnThreshold = 0.01f; 
	
	//(3,5,6,9) (1->4)
	int PWMPin1 = 6; //emco1
	int PWMPin2 = 9; //emco2

	Arduinos(PApplet parent){
		this.p= parent;
	    if (Serial.list().length>=1){
	    	boolean hasArd = false;
	    	for(int i=0; i<Serial.list().length; i++){
	    		System.out.println(Serial.list()[i]);
	    		if(Serial.list()[i].contains(ard)){
	    			hasArd = true;	
	    		};
	    	}
	    	
	    	if(hasArd){
	    	arduino = new Arduino(p, ard, baudRate);
	    	System.out.println("Arduino on: " + ard);
	    	} else {
	    		System.out.println(ard + " is not pressent, please connect.");
	    		p.exit();
	    	}
	        }
	    
	    if(vibeMode){
	    	maxArduinoValue = vibeMaxArduinoValue;
	    	minArduinoValue = vibeMinArduinoValue;
	    	onThreshold = vibeOnThreshold;
	    }
	    
	    multiplyValue = (float)maxArduinoValue;
	    System.out.println(multiplyValue);
	}
	
	public void setA(float a){
		float tempF = (float)a*multiplyValue;
		int tempI = (int) tempF;	
		if (previousA != tempI ){		
			arduino.analogWrite(PWMPin1, tempI);
			previousA = tempI;
		}		
	}
	
	public void setB(float b){
		float tempF = (float)b*multiplyValue;
		int tempI = (int) tempF;		
		if (previousB != b){
			arduino.analogWrite(PWMPin2, tempI);
			previousB = tempI;
		}	
	}
		
	public void setAB(float a, float b){		
		float tempF = (float)a*multiplyValue;
		int tempI = (int) tempF;
		float tempF2 = (float)b*multiplyValue;
		int tempI2 = (int) tempF2;
		if (previousA != tempI || previousB != tempI2 ){
			arduino.analogWrite(PWMPin1, tempI);
			arduino.analogWrite(PWMPin2, tempI2);
			previousA = tempI;
			previousB = tempI2;
		}	
	}	
	
	public void setAB(float c[]){	
		
		float a = c[0];
		float b = c[1];
		
		int tempI =0;
		int tempI2 =0;
		
		if(a<onThreshold){ //threshold test to make sure 0 is sent instead of a minimum value
			tempI = 0;
		} else {	
        float tempF = PApplet.map(a,0,1,minArduinoValue,maxArduinoValue);//(float)a*multiplyValue;
		tempI = (int) tempF;
		}
		
		if(b<onThreshold){
			tempI2 = 0;
		} else {	
		float tempF2 = PApplet.map(b,0,1,minArduinoValue,maxArduinoValue); //(float)b*multiplyValue;
		tempI2 = (int) tempF2;
		}
		
		if (previousA != tempI || previousB != tempI2 ){
			String send = "setAB(float[] c): "+PWMPin1+": "+tempI+" ; "+ PWMPin2+": "+tempI2;
		
			arduino.analogWrite(PWMPin1, tempI);
			arduino.analogWrite(PWMPin2, tempI2);
	
			previousA = tempI;
			previousB = tempI2;
		}	
	}	
	
	public void setABSingle(float c[]){	
		
		float a = c[0];
		float b = c[1];
		
		int tempI =0;
		int tempI2 =0;
		
		if(a<onThreshold){ //threshold test to make sure 0 is sent instead of a minimum value
			tempI = 0;
		} else {	
        float tempF = PApplet.map(a,0,1,minArduinoValue,maxArduinoValue);//(float)a*multiplyValue;
		tempI = (int) tempF;
		}
		
		if(b<onThreshold){
			tempI2 = 0;
		} else {	
		float tempF2 = PApplet.map(b,0,1,minArduinoValue,maxArduinoValue); //(float)b*multiplyValue;
		tempI2 = (int) tempF2;
		}
		
		if (previousA != tempI || previousB != tempI2 ){
			String send = "setAB(float[] c): "+PWMPin1+": "+tempI+" ; "+ PWMPin2+": "+tempI2;
		
			arduino.analogWrite(PWMPin1, tempI);
			arduino.analogWrite(PWMPin2, 0);

			previousA = tempI;
			previousB = tempI2;
		}	
	}	
	
	public void setAB(int c[]){	

		float a = (float)c[0]/100.0f;
		float b = (float)c[1]/100.0f;
		
		int tempI =0;
		int tempI2 =0;
		
		if(a<onThreshold){ //threshold test to make sure 0 is sent instead of a minimum value
			tempI = 0;
		} else {	
        float tempF = PApplet.map(a,0,1,minArduinoValue,maxArduinoValue);//(float)a*multiplyValue;
		tempI = (int) tempF;
		}
		
		if(b<onThreshold){
			tempI2 = 0;
		} else {	
		float tempF2 = PApplet.map(b,0,1,minArduinoValue,maxArduinoValue); //(float)b*multiplyValue;
		tempI2 = (int) tempF2;
		}
		
		
		if (previousA != tempI || previousB != tempI2 ){
			String send = "setAB(Int[]c): "+PWMPin1+" willy: "+tempI+" ; "+ PWMPin2+": "+tempI2;

			arduino.analogWrite(PWMPin1, tempI);
			arduino.analogWrite(PWMPin2, tempI2);
			//printToScreen(send);
			previousA = tempI;
			previousB = tempI2;
		}	
	}	
	
	public void off(){
		arduino.analogWrite(PWMPin1, 0);
		arduino.analogWrite(PWMPin2, 0);
	}
	
	public void printToScreen(String t){
		if(stringCount< stringStore.length-1){
			stringCount++;
		} else {
			stringCount = 0;
		}
		stringStore[stringCount] = t;
	}
	

	public void displayPrint(){
		for(int i =0; i<stringStore.length; i++){
			    p.text(stringStore[i],p.width/2,(p.height/2)+(i*20));
		}
	}
		

}

import java.io.PrintWriter;
//import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class NewOutput {
	
	PrintWriter writer;

	Table table;
	
	PApplet p;
	
	int d = 0;
	
	boolean isReady = false;
	
	String fileName ="";
	
	NewOutput(PApplet p, User user){
		try{

		    table = new Table();
		   
		    TableRow newRow = table.addRow();
		    
		    newRow.setString(1,"Date:"); 
		    newRow.setString(2,user.theDate); 
		    
		    newRow = table.addRow();  
		    newRow.setString(1,"Time:"); 
		    newRow.setString(2,user.time); 
		    
		    newRow = table.addRow();  
		    newRow.setString(1,"Age:"); 
		    newRow.setString(2,user.age); 
		    
		    newRow = table.addRow();  
		    newRow.setString(1,"Sex:"); 
		    newRow.setString(2,user.sex); 
		    
		    newRow = table.addRow();  
		    newRow.setString(1,"Handed:"); 
		    newRow.setString(2,user.hand); 
		    
		    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
		    Date date = new Date();
		    fileName= dateFormat.format(date);
		        
		    this.p = p;

		} catch (Exception e) {
		   System.out.println("problem with file out in newOutput class");
		}
	}

	public void setSingleFingerJNDController(SingleFingerJNDController s){		
		table.addRow();
		TableRow newRow = table.addRow();
	    newRow.setString(1,"Single Finger JND Test Results");
	    table.addRow();
	    newRow = table.addRow();
	    newRow.setString(1,"Refference value:"); 
	    newRow.setString(2,"Test 1"); 
	    newRow.setString(3,"Test 2"); 
	    newRow.setString(4,"Test 3"); 
	    newRow.setString(5,"Average Delta"); 
	    newRow.setString(6,"Delta Varience "); 
	    
	    for(int i =0; i< s.refferenceValues.length; i++){
    	newRow = table.addRow();
    	newRow.setInt(1,  s.refferenceValues[i]);
	    for(int j =0; j< s.changeValues[i].length; j++){
			newRow.setInt(j+2,s.changeValues[i][j]);			
		}
	    newRow.setFloat(5,s.average[i]); 
	    newRow.setFloat(6,s.variance[i]);     
	    }	    
	}
	
	public void setSingleFingerJNDTesterResults (SingleFingerJNDTesterController s){
		table.addRow();
		TableRow newRow = table.addRow();
	    newRow.setString(1,"Single Finger JND Tester Results");
	    table.addRow();
	    newRow = table.addRow();
	    newRow.setString(1,"A"); 
	    newRow.setString(2,"B"); 
	    newRow.setString(3,"?"); 
	    newRow.setString(4,"Answer"); 
	    newRow.setString(5,"Score"); 
	    newRow.setString(6,"Times A touched"); 
	    newRow.setString(7,"Times B touched"); 
	    int temp = 0;
	    for (int i=0; i<s.testsValues.length; i++){		
			newRow = table.addRow();
			newRow.setInt(1,s.testsValues[i][0]);
			newRow.setInt(2,s.testsValues[i][1]);
			newRow.setInt(3,s.testsValues[i][2]);
			newRow.setInt(4,s.testsValues[i][3]);
			if(s.testsValues[i][2]==s.testsValues[i][3]){
				temp++;
			}
			newRow.setInt(6,s.testsValues[i][4]);
			newRow.setInt(7,s.testsValues[i][5]);
			
	    }
		newRow = table.addRow();
		newRow.setString(1,"Result:");
		newRow.setInt(2,temp);
		newRow = table.addRow();
		newRow.setString(1,"Out of "+s.testsValues.length);
	}
	
	public void setGraspSizeResults(GraspTestController gt){
		table.addRow();
		TableRow newRow = table.addRow();
	    newRow.setString(1,"Grasp distance results");
	    table.addRow();
	    newRow = table.addRow();
	    newRow.setString(1,"Test 1"); 
	    newRow.setString(2,"Test 2"); 
	    newRow.setString(3,"Test 3"); 
	    newRow.setString(4,"Test 4"); 
	    newRow.setString(5,"Test 5"); 
	    newRow = table.addRow();
		for(int i =0; i<gt.results.length; i++){
			newRow.setFloat(i+1,gt.results[i]);
		}
		table.addRow();
		newRow = table.addRow();
		newRow.setString(1,"Average:");
		newRow.setFloat(2,gt.average);
		newRow = table.addRow();
		newRow.setString(1,"Varience:");
		newRow.setFloat(2,gt.variance);
		    
	}
	
	public void setJNDControllerResults (JNDController s){
		table.addRow();
		TableRow newRow = table.addRow();
	    newRow.setString(1,"Multi-Finger JND Test Results");
	    table.addRow();
	    newRow = table.addRow();
	    newRow.setString(1,"Refference value:"); 
	    newRow.setString(2,"Test 1"); 
	    newRow.setString(3,"Test 2"); 
	    newRow.setString(4,"Test 3"); 
	    newRow.setString(5,"Average Delta"); 
	    newRow.setString(6,"Delta Varience "); 
	    
	    for(int i =0; i< s.refferenceValues.length; i++){
    	newRow = table.addRow();
    	newRow.setInt(1,  s.refferenceValues[i]);
	    for(int j =0; j< s.changeValues[i].length; j++){
			newRow.setInt(j+2,s.changeValues[i][j]);			
		}
	    newRow.setFloat(5,s.average[i]); 
	    newRow.setFloat(6,s.variance[i]);     
	    }	  
	}
	
	public void setMultiFingerJNDTesterResults (JNDTestController s){
		table.addRow();
		TableRow newRow = table.addRow();
	    newRow.setString(1,"Multi Finger JND Tester Results");
	    table.addRow();
	    newRow = table.addRow();
	    newRow.setString(1,"A"); 
	    newRow.setString(2,"B"); 
	    newRow.setString(3,"?"); 
	    newRow.setString(4,"Answer"); 
	    newRow.setString(5,"Score"); 
	    newRow.setString(6,"Times A touched"); 
	    newRow.setString(7,"Times B touched"); 
	    int temp = 0;
	    for (int i=0; i<s.testsValues.length; i++){		
			newRow = table.addRow();
			newRow.setInt(1,s.testsValues[i][0]);
			newRow.setInt(2,s.testsValues[i][1]);
			newRow.setInt(3,s.testsValues[i][2]);
			newRow.setInt(4,s.testsValues[i][3]);
			if(s.testsValues[i][2]==s.testsValues[i][3]){
				temp++;
			}
			newRow.setInt(6,s.testsValues[i][4]);
			newRow.setInt(7,s.testsValues[i][5]);
	    }
		newRow = table.addRow();
		newRow.setString(1,"Result:");
		newRow.setInt(2,temp);
		newRow = table.addRow();
		newRow.setString(1,"Out of "+s.testsValues.length);
	}
	
	public void setPinchTestResults (PinchController s){
		PApplet.println("Pinch results printing");
		table.addRow();
		TableRow newRow = table.addRow();
	    newRow.setString(1,"Pinch Test Results");
	    table.addRow();
	    newRow = table.addRow();
	    newRow.setString(1,"A"); 
	    newRow.setString(2,"B"); 
	    newRow.setString(3,"?"); 
	    newRow.setString(4,"Answer"); 
	    newRow.setString(5,"Score"); 
	    int temp = 0;
	    for (int i=0; i<s.testsValues.length; i++){		
			newRow = table.addRow();
			newRow.setInt(1,s.testsValues[i][0]);
			newRow.setInt(2,s.testsValues[i][1]);
			newRow.setInt(3,s.testsValues[i][2]);
			newRow.setInt(4,s.testsValues[i][3]);
			if(s.testsValues[i][2]==s.testsValues[i][3]){
				temp++;
			}
	    }
		newRow = table.addRow();
		newRow.setString(1,"Result:");
		newRow.setInt(2,temp);
		newRow = table.addRow();
		newRow.setString(1,"Out of "+s.testsValues.length);	
	}
	
	public void setPinchTest2Results (PinchController2 s){
		table.addRow();
		TableRow newRow = table.addRow();
	    newRow.setString(1,"Pinch Test 1 Results");
	    table.addRow();
	    newRow = table.addRow();
	    newRow.setString(1,"Refference value:"); 
	    newRow.setString(2,"Test 1"); 
	    newRow.setString(3,"Test 2"); 
	    newRow.setString(4,"Test 3"); 
	    newRow.setString(5,"Average Delta"); 
	    newRow.setString(6,"Delta Varience "); 
	    
	    for(int i =0; i< s.refferenceValues.length; i++){
    	newRow = table.addRow();
    	newRow.setInt(1,  s.refferenceValues[i]);
	    for(int j =0; j< s.changeValues[i].length; j++){
			newRow.setFloat(j+2,s.changeValues[i][j]);			
		}
	    newRow.setFloat(5,s.average[i]); 
	    newRow.setFloat(6,s.variance[i]);     
	    }	
	}
	
	public void close(){
		System.out.println("Test File name: " + fileName);
		p.saveTable(table, "data/"+fileName+".csv");
	}

}

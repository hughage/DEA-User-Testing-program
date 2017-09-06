import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.leapmotion.leap.Vector;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Recorder {
	
	PrintWriter writer;

	Table table;
	
	PApplet p;
	
	int d = 0;
	
	boolean isReady = false;
	
	boolean once = true;
	
	String fileName ="";
	String folderName = "";
	
	Recorder(PApplet p, String name, String foldName){
		try{
			
		    table = new Table();
		    
		    TableRow newRow = table.addRow();
		    
		    newRow.setString(0,"Current Test"); 
		    newRow.setString(1,"Time"); 
		    newRow.setString(2,"X"); 
		    newRow.setString(3,"Y"); 
		    newRow.setString(4,"Z"); 
		    newRow.setString(5,"X2"); 
		    newRow.setString(6,"Y2"); 
		    newRow.setString(7,"Z2"); 
		    newRow.setString(8,"PWMValue"); 
		    

	
		    
		    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
		    Date date = new Date();
		    fileName= dateFormat.format(date);
		    fileName = name+fileName;
		    folderName = foldName;
		        
		    this.p = p;

		} catch (Exception e) {
		   System.out.println("problem with file out in newOutput class");
		}
	}
	
	public void update(Vector temp, int time, int pwm){
		
		if (temp.getX() != 0f){	
			TableRow newRow = table.addRow();
			newRow.setInt(1,time);
			newRow.setFloat(2,temp.getX()); 
			newRow.setFloat(3,temp.getY()); 
			newRow.setFloat(4,temp.getZ()); 
			newRow.setInt(8,pwm); 
			
			once=true;
		} else {
			if(once){
				TableRow newRow = table.addRow();
				newRow.setInt(1,time);
				newRow.setFloat(2,temp.getX()); 
				newRow.setFloat(3,temp.getY()); 
				newRow.setFloat(4,temp.getZ()); 
				newRow.setInt(8,pwm); 
			    once = false;
			}
		}
	}
	
	public void update(Vector temp, Vector temp2, int time,int pwm){
		
		if (temp.getX() != 0f){	
			TableRow newRow = table.addRow();
			newRow.setInt(1,time);
			newRow.setFloat(2,temp.getX()); 
			newRow.setFloat(3,temp.getY()); 
			newRow.setFloat(4,temp.getZ()); 
			newRow.setFloat(5,temp2.getX()); 
			newRow.setFloat(6,temp2.getY()); 
			newRow.setFloat(7,temp2.getZ());
			newRow.setInt(8,pwm); 
			once=true;
		} else {
			if(once){
				TableRow newRow = table.addRow();
				newRow.setInt(1,time);
				newRow.setFloat(2,temp.getX()); 
				newRow.setFloat(3,temp.getY()); 
				newRow.setFloat(4,temp.getZ()); 
				newRow.setFloat(5,temp2.getX()); 
				newRow.setFloat(6,temp2.getY()); 
				newRow.setFloat(7,temp2.getZ()); 
				newRow.setInt(8,pwm); 
			    once = false;
			}
		}
	}
	
	public void newTest(int a,int b, int correct){
		TableRow newRow = table.addRow();	
		newRow.setString(0,"Test perams:");
		newRow.setString(1,"A, B, C, Ans"); //
		newRow.setInt(2,a); //
		newRow.setInt(3,b); //
		newRow.setInt(4,correct); //
		 //
	}


	
	public void close(){
		System.out.println("Test File name: " + fileName);
		p.saveTable(table, "Position Data/"+folderName+"/"+fileName+".csv");
	}

}
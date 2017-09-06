import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class User {
	
	Scanner user_input = new Scanner( System.in );
	
	String theDate;
	String time;
	String fileName;
	String age;
	String sex;
	int[] pMax = new int[4];
	int avPMax; 
	String hand;
	
	boolean userDone = false;

	
	
	User(){
		getUserInfo();
	}
	
	private void getUserInfo(){
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat2.format(date));
		System.out.println(dateFormat.format(date));
		   
		theDate = dateFormat.format(date);
		time = dateFormat2.format(date);
		fileName = (date.getTime()+".txt");
		System.out.println(fileName);
		System.out.println("Age:");
		this.age = user_input.next( );
		System.out.println("m or f:");
		this.sex= user_input.next( );
		System.out.println("l or r:");
		this.hand = user_input.next( );
		userDone = true; 
		
	}
	
	public String getName(){
		if (userDone){
			return theDate; 
		} else {
			String t = "User Input Not finished";
			return t;
		}
	}
	
	public String getAge(){
		if (userDone){
			return age; 
		} else {
			return "no Age";
		}
	}
	
	public String getSex(){
		if (userDone){
			return sex; 
		} else {
			String t = "User Input Not finished";
			return t;
		}
	}

}

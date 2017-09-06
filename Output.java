
import java.io.PrintWriter;

public class Output {
	
	PrintWriter writer;
	
	Output(User user){
		try{
		    writer = new PrintWriter("Results/"+user.fileName, "UTF-8");
		    writer.println("*****************************************************");
		    writer.println("");
		    writer.println("<User Info>");
		    writer.println(user.theDate);
		    writer.println(user.age);
		    writer.println(user.sex);
		    writer.println(user.hand);
		    writer.println("</user Info>");
		    writer.println("");
		    System.out.println("Doc made");
		} catch (Exception e) {
			System.out.println("Nope");
		}
	}
	
	Output(){
		try{
		    writer = new PrintWriter("TestMe2.txt", "UTF-8");
		    writer.println("<user Info>");
		    writer.println("</user Info>");
		    writer.println("");

		} catch (Exception e) {
		   // do something
		}
	}
	
	public void setGraspSizeResults(GraspTestController gt){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("<Grasp Size Test>");
		writer.println("");
			writer.println("<Results>");
			writer.println("");
				for(int i =0; i<gt.results.length; i++){
					writer.println(gt.results[i]);
				}
				writer.println("");
			writer.println("</Results>");
			writer.println("");
			writer.println("<Pinch Size Test Average>");
				writer.println(gt.getAverage());
			writer.println("</Pinch Size Test Average>");
			writer.println("");
			writer.println("<Pinch Size Test Variance>");
				writer.println(gt.getVariance());
			writer.println("</Pinch Size Test Variance>");
			writer.println("");
		writer.println("</Grasp Size Test>");
		
		writer.println("");		
	}
	
	public void setSingleFingerJNDController(SingleFingerJNDController s){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("<Single Finger JND>");
		writer.println("");	
			for(int i =0; i< s.refferenceValues.length; i++){
				writer.println("<255-"+s.refferenceValues[i]+">");
					for(int j =0; j< s.changeValues[i].length; j++){
						writer.println(s.changeValues[i][j]);			
					}
				writer.println("</255-"+s.refferenceValues[i]+">");
				writer.println("");	
			}
		writer.println("<Averages>");
			for(int i =0; i< s.average.length; i++){
				writer.println(s.average[i]);
			}
		writer.println("</Averages>");
		writer.println("");	
		writer.println("<Variance>");
		for(int i =0; i< s.variance.length; i++){
			writer.println(s.variance[i]);
		}
		writer.println("</Varience>");
		writer.println("");	
		writer.println("</Single Finger JND>");
		writer.println("");
	}
	
	
	public void setSingleFingerJNDTesterResults (SingleFingerJNDTesterController s){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("<Single Finger JNDTester Results>");
		writer.println("");	
		writer.println("<Results>");	
		int temp = 0;
		for (int i=0; i<s.testsValues.length; i++){		
				writer.println(s.testsValues[i][0]+", "+s.testsValues[i][1]+", "+s.testsValues[i][2]+", "+s.testsValues[i][3]);	
				if(s.testsValues[i][2]==s.testsValues[i][3]){
					temp++;
				}
		}
		writer.println("</Results>");	
		writer.println("");
		writer.println(temp+"/"+s.testsValues.length+" correct");
		writer.println("");
		
		writer.println("<Single Finger JNDTester Results>");
		writer.println("");
	}
	
	
	public void setJNDControllerResults (JNDController s){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("<multi Finger JND>");
		writer.println("");	
			for(int i =0; i< s.refferenceValues.length; i++){
				writer.println("<255-"+s.refferenceValues[i]+">");
					for(int j =0; j< s.changeValues[i].length; j++){
						writer.println(s.changeValues[i][j]);			
					}
				writer.println("</255-"+s.refferenceValues[i]+">");
				writer.println("");	
			}
		writer.println("<Averages>");
			for(int i =0; i< s.average.length; i++){
				writer.println(s.average[i]);
			}
		writer.println("</Averages>");
		writer.println("");	
		writer.println("<Variance>");
		for(int i =0; i< s.variance.length; i++){
			writer.println(s.variance[i]);
		}
		writer.println("</Varience>");
		writer.println("");		
		writer.println("</multi Finger JND>");
		writer.println("");		
	}
	
	public void setMultiFingerJNDTesterResults (JNDTestController s){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("<Multi Finger JNDTester Results>");
		writer.println("");	
		writer.println("<Results>");	
		int temp = 0;
		for (int i=0; i<s.testsValues.length; i++){		
				writer.println(s.testsValues[i][0]+", "+s.testsValues[i][1]+", "+s.testsValues[i][2]+", "+s.testsValues[i][3]);	
				if(s.testsValues[i][2]==s.testsValues[i][3]){
					temp++;
				}
		}
		writer.println("</Results>");	
		writer.println("");
		writer.println(temp+"/"+s.testsValues.length+" correct");
		writer.println("");
		
		writer.println("</Multi Finger JNDTester Results>");
		writer.println("");
	}
	
	public void setPinchTestResults (PinchController s){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("<Pinch Test 1 JND>");
		writer.println("");	
			for(int i =0; i< s.refferenceValues.length; i++){
				writer.println("<255-"+s.refferenceValues[i]+">");
					for(int j =0; j< s.changeValues[i].length; j++){
						writer.println(s.changeValues[i][j]);			
					}
				writer.println("</255-"+s.refferenceValues[i]+">");
				writer.println("");	
			}
			
		writer.println("<Averages>");
			for(int i =0; i< s.average.length; i++){
				writer.println(s.average[i]);
			}
		writer.println("</Averages>");
		writer.println("");	
		writer.println("<Variance>");
		for(int i =0; i< s.variance.length; i++){
			writer.println(s.variance[i]);
		}
		writer.println("</Varience>");
		writer.println("");		
		writer.println("</Pinch Test 1 JND>");
		writer.println("");		
	}
	
	public void setPinchTest2Results (PinchController2 s){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("<Pinch Test 2 JND>");
		writer.println("");	
			for(int i =0; i< s.refferenceValues.length; i++){
				writer.println("<0-"+s.refferenceValues[i]+">");
					for(int j =0; j< s.changeValues[i].length; j++){
						writer.println(s.changeValues[i][j]);			
					}
				writer.println("</0-"+s.refferenceValues[i]+">");
				writer.println("");	
			}

		writer.println("<Averages>");
			for(int i =0; i< s.average.length; i++){
				writer.println(s.average[i]);
			}
		writer.println("</Averages>");
		writer.println("");	
		writer.println("<Variance>");
		for(int i =0; i< s.variance.length; i++){
			writer.println(s.variance[i]);
		}
		writer.println("</Varience>");
		writer.println("");		
		writer.println("</Pinch Test 2 JND>");
		writer.println("");		
	}
	
	public void noResult(String p){
		writer.println("*****************************************************");
	    writer.println("");
		writer.println("NO RESULT FOR: "+p);
		writer.println("");
	}
	
	
	public void close(){
		writer.close();
	}
	
	

}

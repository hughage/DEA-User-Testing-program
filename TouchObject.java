import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class TouchObject {
	
	PApplet p;
	int posX, posY, size, r, g, b;
	boolean contact = false;
	int hapticResponce = 0;
	String label = "";
	
	int touchCount =0;
	
	boolean startContact = false;
	
	
	TouchObject(int x, int y, int r, int g, int b, PApplet parent){
		this.posX=x;
		this.posY=y;
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;
		this.size = p.height/2;
	}
	
	TouchObject(int x, int y, int r, int g, int b, PApplet parent, String l){
		this.posX=x;
		this.posY=y;
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;
		this.size = 2*p.height/3;
		this.label= l;
	}
	
	
	@SuppressWarnings("static-access")
	public void update(){
		if(contact){
			p.noFill();
			p.stroke(r,g,b);
		} else {
			p.fill(r,g,b);
			p.noStroke();
		}
		p.ellipse(posX,posY,size,size);
		p.textAlign(p.CENTER, p.CENTER);
		p.textSize(120);
		p.fill(0,150);
		p.text(label, posX, posY);
		
		if (contact== false){
			startContact=false;
		}
		
		if(contact== true && startContact==false){
			touchCount++;
			startContact = true;
		}
		
		p.noFill();
	}
	

	public float[] isTouched(Vector i, Vector t){
		contact = false;
		float temp[] = {0,0};
		if (getDistance(i)<(size/2)){
			contact = true;
			temp[0] = 1.0f;
		} else {
			temp [0] = 0.0f;
		}
		
		if (getDistance(t)<(size/2)){
			contact = true;
			temp[1] = 1.0f;
		} else {
			temp [1] = 0.0f;
		}
		

		return temp;
	}
	
	@SuppressWarnings("static-access")
	private float getDistance(Vector v){
		float a = p.abs(v.getX()-posX);
		a = a*a;
		float b = p.abs(v.getY()-posY);
		b = b*b;
		float c = p.sqrt(a+b);
		return c;
	}
	
	public int getTouchCount(){
		return touchCount;		
	}
	
	public void resetTouchCount(){
		touchCount=0; 
	}
	
	public void setHapticResponce(int p){
		this.hapticResponce = p;
	}
	
	public int getHapticResponce(){
		return hapticResponce;
	}


}

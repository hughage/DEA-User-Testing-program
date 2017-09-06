import com.leapmotion.leap.Vector;
import processing.core.PApplet;

public class PinchObject {

	PApplet p;
	int posX, posY, r, g, b;
	Vector index, thumb;
	float pinchDistance;
	int xMag, yMag;
	int xSize, ySize;
	float rotation;
	float pMax; //value of the maximum hand pinch size from the grasp size class
	String label = "";
	int screenLimitL, screenLimitR; //values to draw pinch object if cursors are in the correct part of the screen
	float pinchUpperThreshold, pinchLowerThreshold; //the threshold to start squeezing the pinch object
	float pinchThresholdScaler = 0f; //starts pinching object at 0.75 of grasp max
	int maximumHapticResult;
	int minimumHapticResult;
	boolean onSide;
	float rd; //real distance in mm between thumb and index, used for haptic response not visuals
	float screenCorrectionScaller;
	
	float scaleFactor = 1.0f;
	
	PinchObject(int r, int g, int b, PApplet parent){
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;		
	}
	
	PinchObject(int r, int g, int b, int p1, int p2,int maxHR, int minHR,float startPinch, float endPinch, float pinchMaxAverage, float pMScaller, PApplet parent){
		this.r=r;
		this.g=g;
		this.b=b;
		this.screenLimitL = p1;
		this.screenLimitR = p2;
		this.maximumHapticResult = maxHR;
		this.minimumHapticResult = minHR;
		this.pMax = pinchMaxAverage;
		this.p = parent;			
		this.pinchLowerThreshold = pMax*endPinch;
		this.pinchUpperThreshold = pMax*startPinch; //start pinching object at 3/4 pinch max value
		xMag= (int) pinchUpperThreshold;	
		screenCorrectionScaller =  pMScaller;
	}
	
	PinchObject(int r, int g, int b, PApplet parent, String l){
		this.r=r;
		this.g=g;
		this.b=b;
		this.p = parent;
		this.label= l;
	}
	

	public void update(ThreeDCursor ic, ThreeDCursor tc, float realDistance){
		
		Vector i = new Vector();
		Vector t = new Vector();
		
		this.rd = realDistance;
		
		if (ic.position.getX()< tc.position.getX()){ // make sure thumb and index get treated the same way
		i = new Vector (ic.position);
		t = new Vector (tc.position);
		} else {
			t = new Vector (ic.position);
			i = new Vector (tc.position);
		}
		
//		p.stroke(255,134,30);
//		p.line(i.getX(),i.getY(), i.getZ(),t.getX(),t.getY(), t.getZ());
		
		float[] temp= returnMidPoint(i,t);//ball.getPos();
		Vector tempV = new Vector (temp[0],temp[1],temp[2]);
			
		if (onSide(tempV)){

		p.pushMatrix();

		p.fill(r,g,b,200);
		p.translate(temp[0], temp [1], temp[2]); 

		Vector test = new Vector((i.getX()-t.getX()),(i.getY()-t.getY()),(i.getZ()-t.getZ()));
		test = test.normalized();
		
 		float tempRotZ = PApplet.atan2(test.getY(),test.getX());
		float tempRotY = PApplet.atan2(test.getZ(), test.getX());
		
		p.rotateZ(tempRotZ);
		p.rotateY(tempRotY);
		
		if(realDistance<pinchUpperThreshold && realDistance>pinchLowerThreshold){
			float distance= (t.distanceTo(i)-ic.xSize*2);
			scaleFactor = (distance)/(pinchUpperThreshold*screenCorrectionScaller);
		} else if(realDistance<(pinchLowerThreshold)){	
			//scaleFactor = pinchLowerThreshold/(pinchUpperThreshold*screenCorrectionScaller);
		}

		//debugging axis
		//drawDebugAxis();

		p.scale(scaleFactor,1,1);	
		p.sphere((pinchUpperThreshold*screenCorrectionScaller)/2);
		p.popMatrix();	
		}
	}
	
	public float[] returnMidPoint(float[]l, float[]r){
		
		float x1 = l[0];
		float  y1 = l[1];
		float  z1 = l[2];
		
		float  x2 = r[0];
		float  y2 = r[1];
		float  z2 = r[2];
		
		float x=(x1+x2)/2f;
		float y=(y1+y2)/2f;
		float z=(z1+z2)/2f;
		
		float[] temp = {x,y,z};
		
		return temp;
	}
	
	public float[] returnMidPoint(Vector l, Vector r){
		
		float x1 = l.getX();
		float  y1 = l.getY();
		float  z1 = l.getZ();
		
		float  x2 = r.getX();
		float  y2 = r.getY();
		float  z2 = r.getZ();
		
		float x=(x1+x2)/2f;
		float y=(y1+y2)/2f;
		float z=(z1+z2)/2f;
		
		float[] temp = {x,y,z};
		
		return temp;
	}
	
	public void drawDebugAxis(){
		p.stroke(255,0,0);
		p.line(0, 0, 0, 1000,0,0);
		p.stroke(0,255,0);
		p.line(0, 0, 0, 0,1000,0);
		p.stroke(0,0,255);
		p.line(0, 0, 0,0 ,0,1000);
		p.stroke(0);
	}
	
	private boolean onSide(Vector h){
		 onSide = false;
		if(h.getX()>screenLimitL && h.getX()<screenLimitR && h.getY()>0 && h.getY()<p.height){
			onSide = true;
		}
		return onSide;
	}
	
	public void setHapticResultBounds(int maxHR, int minHR){
		this.maximumHapticResult = maxHR;
		this.minimumHapticResult = minHR;
	}
	
	
	public void setRampThreshold(float[] r){ // [0] start force of r[0]*averageGraspSize, end at r[1]
		pinchLowerThreshold = r[0]*pMax;
		pinchLowerThreshold = r[1]*pMax;
	}
	
	
	public float[] getHapticResult(){
		float[] temp = {0f,0f};
		if(rd<pinchUpperThreshold && rd>pinchLowerThreshold){			
			float v = PApplet.map(rd,pinchUpperThreshold,pinchLowerThreshold,minimumHapticResult,maximumHapticResult);
			temp[0] =v/100f;
			temp[1] =v/100f;
		} else if(rd<pinchLowerThreshold){
			float v = maximumHapticResult;
			temp[0] =v/100f;
			temp[1] =v/100f;
		}
		return temp;
	}
	
}

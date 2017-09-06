import com.leapmotion.leap.*;

public class Leap {
	
	Controller leap;	
	Vector index, thumb, palm; //actuall millimeter values
	Vector indexCorrected, thumbCorrected; //to scale to screen values xp+(xm*index.x)
	float pMaxScaler;
	
	
	int width, height, depth;
	
	//ideal operating volume for leap 
	float x = 235;
	float y = 235;
	float z = 147;
	
	//xm*(index.x + xp)	
	float xm; //(width/x) in the formula ((width/x)*(index.x + (x/2))
	float ym;
	float zm;
	
	float xp;//(x/2) in the formula ((width/x)*(index.x + (x/2))
	float yp;
	float zp;
	
	
	Leap(int w, int h, int d){ //pass in width, height and depth of screen
		
		leap = new Controller(); 
		index = new Vector();
		thumb = new Vector();
		indexCorrected = new Vector();
		thumbCorrected = new Vector();
		
		this.width = w;
		this.height = h;
		this.depth  = d;
		
		xm = ((float)w)/x;
		ym = ((float)h)/y;
		zm = ((float)d)/z; 
		
		xp = (x/2);
		yp = (y/2);
		zp = (z/2);

	}
	
	
	public Vector[] getIndexThumbPos(){
		Frame currentFrame = leap.frame();
		this.index = currentFrame.hands().rightmost().fingers().fingerType(Finger.Type.TYPE_INDEX).get(0).tipPosition();
		this.thumb = currentFrame.hands().rightmost().fingers().fingerType(Finger.Type.TYPE_THUMB).get(0).tipPosition();
		Vector[] temp = {index,thumb};
		return temp;
	}
	
	public Vector getPalmPos(){
		Frame currentFrame = leap.frame();
		this.palm = currentFrame.hands().rightmost().palmPosition();
		palm = correctToScreen(palm);
		return palm;
		
	}

	
	public void update(){
		Frame currentFrame = leap.frame();
		this.index = currentFrame.hands().rightmost().fingers().fingerType(Finger.Type.TYPE_INDEX).get(0).tipPosition();
		this.thumb = currentFrame.hands().rightmost().fingers().fingerType(Finger.Type.TYPE_THUMB).get(0).tipPosition();
		screenCorrected();		
	}
	
	private void screenCorrected(){
		indexCorrected.setX(xm*(index.getX()+xp));
		indexCorrected.setY(height-(ym*((index.getY()-200)+yp)));
		indexCorrected.setZ(zp+(zm*index.getZ()));		
		
		
		thumbCorrected.setX(xm*(thumb.getX()+xp));
		thumbCorrected.setY(height-(ym*((thumb.getY()-200)+yp)));
		thumbCorrected.setZ(zp+(zm*thumb.getZ()));
	}
	
	private Vector correctToScreen (Vector v){
		Vector temp = v;
		temp.setX(xm*(v.getX()+xp));
		temp.setY(height-(ym*((v.getY()-200)+yp)));
		temp.setZ(zp+(zm*v.getZ()));	
		return temp;
	}
	
	public float pMaxscreenCorrected(float p){
		return xm*p;
	}
	
	public float getpMaxScaler(){
		return xm;
	}
	
	public boolean inIdealVolume(){
		boolean inIdeal = false;
		if (inVol(thumb) && inVol(index)){
			inIdeal = true;
		}
		return inIdeal;
	}
	
	private boolean inVol(Vector t){
		boolean inIdeal = false;
		if(t.getX()<xp && t.getX()>-xp &&
		   t.getY()< 200+yp && t.getY()>200-yp && //200 is idea y height cog
		   t.getZ()<zp && t.getZ()>-zp){
			inIdeal = true; 
		}
		return inIdeal;
	}
	

	
	
	
}

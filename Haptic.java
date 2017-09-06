import com.leapmotion.leap.Vector;

public class Haptic {
	
	TouchObject[] objects;
	PinchObject pinchObject;
	Arduinos ard;
	
	Haptic(TouchObject[] o, Arduinos a){
		this.objects = o;
		ard = a; 
	}
	
	Haptic(PinchObject p, Arduinos a){
		this.pinchObject = p;
		ard = a; 
	}
	
	public void collsion(Vector[] v){
		
		float[] forces = {0.0f,0.0f};
		
		for (int j =0; j< objects.length; j++){
			objects[j].contact = false;
		}
	
		for (int i=0; i<v.length; i++){
			Vector temp = v[i];
			for (int j =0; j< objects.length; j++){
				if(getDistance(temp,objects[j])< (objects[j].size)/2){
					float force = (float)objects[j].getHapticResponce()/100.0f;
					forces[i] = force;
					objects[j].contact = true;
					}
				}
		} 
		ard.setAB(forces);
	}
	
	private float getDistance(Vector v, TouchObject o){
		float a = Math.abs(v.getX()-o.posX);
		a = a*a;
		float b = Math.abs(v.getY()-o.posY);
		b = b*b;
		float c = (float) Math.sqrt(a+b);
		return c;
	}

}

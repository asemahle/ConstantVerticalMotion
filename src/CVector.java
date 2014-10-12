
public class CVector {
	double x, y;
	final static double PI = Math.PI;
	
	public CVector(double _x, double _y){
		x = _x;
		y = _y;
	}
	
	public double getAngle(){
		double smallAngle = Math.atan2(Math.abs(y), Math.abs(x));
		
//		System.out.println("Small Angle " + smallAngle);
		
		smallAngle = smallAngle/Math.PI * 180; 
		double angle = Float.NaN;
		if (y >= 0 && x >= 0){
			angle = smallAngle;
		}else if (y >= 0 && x < 0){
			angle = 180 - smallAngle;
		}else if (y < 0 && x < 0){
			angle = 180 + smallAngle;
		}else if (y < 0 && x >= 0){
			angle = 360 - smallAngle;
		}
		
		return angle;
	}
	
	public double getMagnitude(){
		return Math.sqrt(x*x + y*y);
	}
	
	public double[] getComponents(){
		double[] components = {x, y};
		return components;
	}
	
	public static CVector add(CVector _v1, CVector _v2){
		CVector v1 = _v1;
		CVector v2 = _v2;
		
		CVector res = new CVector(v1.x + v2.x, v1.y + v2.y);
		return res;
	}
	
	public static CVector subtract(CVector _v1, CVector _v2){
		CVector v1 = _v1;
		CVector v2 = _v2;
		
		CVector res = new CVector(v1.x - v2.x, v1.y - v2.y);
		return res;
	}
	
	public static CVector dirVector(double _degAngle, double _magnitude){
		double angle = _degAngle * PI / 180;
		double magnitude = _magnitude;
		
		double smallAngle = angle % (PI / 2);
		double absX = magnitude * Math.cos(smallAngle);
		double absY = magnitude * Math.sin(smallAngle);
		
		
		double x = Float.NaN;
		double y = Float.NaN;
		int quadrant = ( (int) Math.floor(angle / (PI/2) ) ) % 4 + 1;
//		System.out.println(quadrant + " " + absX + " " + absY);
		switch (quadrant) {
		
			case 1:
				x = absX; 
				y = absY;
				break;
			case 2:
				x = -absY;
				y = absX;
				break;
			case 3:
				x = -absX;
				y = -absY;
				break;
			case 4:
				x = absY;
				y = -absX;
				break;
			default:break;
		}
		
		CVector res = new CVector(x,y);
		return res;
	}

}

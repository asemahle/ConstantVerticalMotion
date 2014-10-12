import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class constantVerticalMotion {
	
	private double g;
	private double inc;
	private double r, l, m, v;
	private double[][] handPoints;
	private double[][] ballPoints;
	
	private int animationCounter;
	private int[][] scaledHandPoints;
	private int[][] scaledBallPoints;
	
	public constantVerticalMotion(double _g, double _r, double _l, double _m, double _v, double _inc) {
		g =_g;

		r = _r; //circle radius
		l = _l; //chain length
		m = _m; //mass
		v = _v; //speed
		
		inc = _inc;
		
		handPoints = new double[(int) (360 / inc)][2];
		ballPoints = new double[(int) (360 / inc)][2];
		
		scaledHandPoints = new int[(int) (360 / inc)][2];
		scaledBallPoints = new int[(int) (360 / inc)][2];
		
		animationCounter = 0;
	}
	
	public void setAnimationCounter(int i){
		animationCounter = i;
	}
	
	public int getAnimationCounter(){
		return animationCounter;
	}
	
	public void simulate(){
		cVector tension;
		cVector fnet;
		cVector ballPos;
		cVector handPos;
		cVector mg = cVector.dirVector(270, m * g);
		
		for (int i = 0; i < 360/inc; i ++){
			/**
			 * force of gravity (mg) + force of tension = fnet,
			 * ∆ tension = fnet - mg
			 * 
			 * Using direction and chain length (l), get hand pos
			 */
			ballPos = cVector.dirVector(i*inc, r);
			
			fnet = cVector.dirVector(ballPos.getAngle() + 180 , m * v * v / r);
			tension = cVector.add(fnet, mg);
						
			handPos = cVector.add(ballPos, cVector.dirVector(tension.getAngle(), l));
			
			ballPoints[i] = ballPos.getComponents();
			handPoints[i] = handPos.getComponents();
		}		
	}
	
	private void scaledLists(int width, int height){
		double bigx = 0, bigy = 0;
        double smallx = 0, smally = 0;
        
		for (int i = 0; i < getBallPoints().length; i++){
        	if (getBallPoints()[i][0] > bigx){
        		bigx = getBallPoints()[i][0];
        	}else if (getBallPoints()[i][0] < smallx){
        		smallx = getBallPoints()[i][0];
        	}
        	if (getHandPoints()[i][0] > bigx){
        		bigx = getHandPoints()[i][0];
        	}else if (getHandPoints()[i][0] < smallx){
        		smallx = getHandPoints()[i][0];
        	}
        	if (getBallPoints()[i][1] > bigy){
        		bigy = getBallPoints()[i][1];
        	}else if (getBallPoints()[i][1] < smally){
        		smally = getBallPoints()[i][1];
        	}
        	if (getHandPoints()[i][1] > bigy){
        		bigy = getHandPoints()[i][1];
        	}else if (getHandPoints()[i][1] < smally){
        		smally = getHandPoints()[i][1];
        	}
        }
        
        double big;
        double small;
        if (bigx - smallx > bigy - smally){
        	big = bigx;
        	small = smallx;
        }else{
        	big = bigy;
        	small = smally;
        } 
        
        for (int i = 0; i < getBallPoints().length; i++){
        	int x = (int) ( (getBallPoints()[i][0] - smallx) / (big - small) * width);
        	int y = (int) ( (getBallPoints()[i][1] - smally) / (big - small) * height);
        	// translate points (center align)
        	x += (width - ((bigx - smallx) / (big - small) * width))/2;
        	y += (width - ((bigy - smally) / (big - small) * width))/2;
        	scaledBallPoints[i] = new int[]{x,y};
        }
        
        for (int i = 0; i < getHandPoints().length; i++){
        	int x = (int) ( (getHandPoints()[i][0] - smallx) / (big - small) * width);
        	int y = (int) ( (getHandPoints()[i][1] - smally) / (big - small) * height);
        	// translate points (center align)
        	x += (width - ((bigx - smallx) / (big - small) * width))/2;
        	y += (width - ((bigy - smally) / (big - small) * width))/2;
        	scaledHandPoints[i] = new int[]{x,y};
        }
        
        
	}
	
	public BufferedImage generateBufferedImage(int width, int height){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		
		scaledLists(width, height);
		
		 g2.setColor(Color.BLACK);        
        for (int i = 0; i < getBallPoints().length; i++){
        	int x = scaledBallPoints[i][0];
        	int y = scaledBallPoints[i][1];;
        	g2.drawLine(x, y, x, y);
        }
        
        g2.setColor(Color.RED);
        for (int i = 0; i < getHandPoints().length; i++){
        	int x = scaledHandPoints[i][0];;
        	int y = scaledHandPoints[i][1];;
        	g2.drawLine(x, y, x, y);            
        }
        
        return image;	
	}
	
	public void drawRope(Graphics g){
		g.setColor(Color.BLACK);
		int ac = animationCounter % getHandPoints().length;
		
		int x1 = (int) scaledHandPoints[ac][0];
		int y1 = (int) scaledHandPoints[ac][1];
		int x2 = (int) scaledBallPoints[ac][0];
		int y2 = (int) scaledBallPoints[ac][1];
		g.drawLine(x1, y1, x2, y2);		
	}
	
	public void stepSimulation(){
		animationCounter++;
	}
	
	public double[][] getHandPoints(){
		return handPoints;
	}
	
	public double[][] getBallPoints(){
		return ballPoints;
	}
	

}

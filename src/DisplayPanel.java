import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel implements Runnable {
	
	private Thread animator;
	private BufferedImage image;
	
	private long afterTime;
	private long timeDiff;
	private long sleepTime;
	private long period;
	private long beforeTime;
	
	private double periodMultiplier;
	
	private boolean running;
	
	private constantVerticalMotion cvm;
	
	final private int WIDTH = 500;
	final private int HEIGHT = 500;
	
	private double g;
	private double r, l, m, v;
	private double inc;
	
	private BufferedImage backImage; //The paths for hand and ball
	
	public DisplayPanel(){
		this.setBackground(Color.white);
		this.setPreferredSize( new Dimension(WIDTH, HEIGHT));
		
		g = 9.8;
		r = 0.31;
		l = 0.31;
		m = 1;
		v = 2.2;
		inc = 0.1;
		
		periodMultiplier = 1;
		
		period = (long) (( 2*r*Math.PI / v ) * (inc / 360) * 1000000000L * (periodMultiplier)); //nanoseconds
		
		cvm = new constantVerticalMotion(g, r, l, m, v, inc);
		cvm.simulate();
		backImage = cvm.generateBufferedImage(WIDTH, HEIGHT);		
	}
	
	public void changeState(double _g, double _r, double _l, double _m, double _v){
		g = _g;
		r = _r;
		l = _l;
		m = _m;
		v = _v;
		
		int count = cvm.getAnimationCounter();
		cvm = new constantVerticalMotion(g, r, l, m, v, inc);
		cvm.setAnimationCounter(count);
		cvm.simulate();
		period = (long) (( 2*r*Math.PI / v ) * (inc / 360) * 1000000000L * (periodMultiplier)); //nanoseconds
		backImage = cvm.generateBufferedImage(WIDTH, HEIGHT);
	}
	
	private void stepSimulation(){
		cvm.stepSimulation();
	}
	
	private void render(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		
		g2.setColor(Color.white);
 	    g2.fillRect (0, 0, WIDTH, HEIGHT); 
 		g2.drawImage(backImage, 0, 0, WIDTH, HEIGHT, null);
 	    cvm.drawRope(g2);
	}
	
	private void paintScreen() {
		Graphics g;
	    try {
	    	g = this.getGraphics();
	    	if (g != null){
	    		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
	    	   
	    	}
	    	
	    	g.dispose();
	    }
	    catch (Exception e)
	    { 
	    	System.out.println("Graphics context error: " + e);  
	    }
	}
	
	public void addNotify()
	// wait for the JPanel to be added to the JFrame before starting
	{ 
		super.addNotify();   // creates the peer
	    startGame();         // start the thread
	} // end of addNotify
	
	
	private void startGame()
	// initialise and start the thread {
	{
		if (animator == null || !running) 
		{ 
			animator = new Thread(this); 
			animator.start( );
		}
	} // end of startGame()
	
	public void stopGame() 
	// called when the JFrame is closing
	{  running = false;   }
	
	public void run(){
		
		cvm.simulate();
		backImage = cvm.generateBufferedImage(WIDTH, HEIGHT);
		
		running = true;
		
		beforeTime = System.nanoTime();
		while (running){
			stepSimulation();
			render();
			paintScreen();
			
			//timer stuff
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = period - timeDiff;  
			
			if (sleepTime > 0) {   // some time left in this cycle
				try {
					Thread.sleep(sleepTime/1000000L); // nano -> ms
				} catch (InterruptedException e) { }  
			}
			if (sleepTime < 0) {
				while (sleepTime < 0){
					stepSimulation();
					sleepTime += period;
				}
				
			}
			
			beforeTime = System.nanoTime();	
		}
		
		System.exit(0);
	}
}

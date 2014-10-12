import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class CVMJFrame extends JFrame implements WindowListener {
	
	private DisplayPanel panel;
	private SpinnerPanel spinnerPanel;
	
	public CVMJFrame(){
		super("Constant Vertical Circular Motion");
		
		makeGUI();
		
		addWindowListener(this);
		pack();
		
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void makeGUI(){
		Container c = getContentPane();
		panel = new DisplayPanel();
		spinnerPanel = new SpinnerPanel(9.8d, 1d, 1d, 1d, 1d, panel);
		
		c.add(panel,"Center");
		c.add(spinnerPanel,"West");
	}
	
	// ----------------- window listener methods -------------

	  public void windowActivated(WindowEvent e){}

	  public void windowDeactivated(WindowEvent e){}

	  public void windowDeiconified(WindowEvent e){}

	  public void windowIconified(WindowEvent e){}


	  public void windowClosing(WindowEvent e)
	  {  panel.stopGame();}
	  
	  public void windowClosed(WindowEvent e) {}
	  public void windowOpened(WindowEvent e) {}
	  
	//--------------------------------------------------------
	
	public static void main(String[] args) {
		
		new CVMJFrame();

	}
}

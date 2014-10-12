import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerPanel extends JPanel{

	private JLabel title;
	private JLabel description;
	
	private JLabel gLabel;
	private JLabel rLabel;
	private JLabel lLabel;
	private JLabel wLabel;
	private JLabel vLabel;
	
	private ChangeListener gListen;
	private ChangeListener rListen;
	private ChangeListener lListen;
	private ChangeListener wListen;
	private ChangeListener vListen;
	
	private JSpinner gSpinner;
	private JSpinner rSpinner;
	private JSpinner lSpinner;
	private JSpinner wSpinner;
	private JSpinner vSpinner;
	
	private DisplayPanel simulator;
	
	private double g, r, l, w, v;

	private final double STEP = 0.01;
	private final double MIN = -10;
	private final double MAX = 10;
	
	public SpinnerPanel(double _g, double _r, double _l, double _w, double _v, DisplayPanel  _simulator){
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(300,500));
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		g = _g;
		r = _r;
		l = _l;
		w = _w;
		v = _v;
		
		simulator = _simulator;
		simulator.changeState(g, r, l, w, v);
		
		makeSpinners();
		makeListeners();
		makeLabels();
		makeGUI();
	}
	
	private void makeSpinners(){
		gSpinner = new JSpinner(new SpinnerNumberModel(g, MIN*10, MAX*10, STEP*10));
		rSpinner = new JSpinner(new SpinnerNumberModel(r, 0 + STEP, MAX, STEP));
		lSpinner = new JSpinner(new SpinnerNumberModel(l, MIN, MAX, STEP));
		wSpinner = new JSpinner(new SpinnerNumberModel(w, MIN, MAX, STEP));
		vSpinner = new JSpinner(new SpinnerNumberModel(v, 0 + STEP, MAX, STEP));
	}
	
	private void makeListeners(){
		 gListen = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				g = Double.parseDouble(gSpinner.getValue().toString());
				simulator.changeState(g, r, l, w, v);
			}
		};
		rListen = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				r = Double.parseDouble(rSpinner.getValue().toString());
				simulator.changeState(g, r, l, w, v);
			}
		};
		lListen = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				l = Double.parseDouble(lSpinner.getValue().toString());
				simulator.changeState(g, r, l, w, v);
			}
		};
		wListen = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				w = Double.parseDouble(wSpinner.getValue().toString());
				simulator.changeState(g, r, l, w, v);
			}
		};
		vListen = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				v = Double.parseDouble(vSpinner.getValue().toString());
				if (v != 0){
					simulator.changeState(g, r, l, w, v);
				}
			}
		};
		
		gSpinner.addChangeListener(gListen);
		rSpinner.addChangeListener(rListen);
		lSpinner.addChangeListener(lListen);
		wSpinner.addChangeListener(wListen);
		vSpinner.addChangeListener(vListen);

	}
	
	private void makeLabels(){
		title = new JLabel ("Constant Vertical Circular Motion");
		title.setFont(new Font("SansSerif", Font.BOLD, 14));
		description = new JLabel ("<html><br><p>Sally is spinning her grandfather's watch "
				+ "in a circle by the chain. The watch spins in a vertical circle "
				+ "at a constant speed.</p><ol><li>The <b>black</b> curve shows the path of the "
				+ "watch</li><li>The <font color='red'><b> red </b></font> curve shows "
				+ "the path of Sally's hand</li></ol></html>");
		
		gLabel = new JLabel ("<html>Gravity (m/s<sup>2</sup>):</html>");
		rLabel = new JLabel ("Radius (m):");
		lLabel = new JLabel ("Chain Length (m):");
		wLabel = new JLabel ("Ball Mass (kg)");
		vLabel = new JLabel ("Ball Speed (m/s)");
	}
	
	private void makeGUI(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		this.add(title, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(description,c);
		
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(gLabel, c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(gSpinner, c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(rLabel, c);
		
		c.gridx = 1;
		c.gridy = 3;
		this.add(rSpinner, c);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(lLabel, c);
		
		c.gridx = 1;
		c.gridy = 4;
		this.add(lSpinner, c);
		
		c.gridx = 0;
		c.gridy = 5;
		this.add(wLabel, c);
		
		c.gridx = 1;
		c.gridy = 5;
		this.add(wSpinner, c);
		
		c.gridx = 0;
		c.gridy = 6;
		this.add(vLabel, c);
		
		c.gridx = 1;
		c.gridy = 6;
		this.add(vSpinner, c);
	}
}

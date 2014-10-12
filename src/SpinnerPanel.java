import java.awt.Color;
import java.awt.Dimension;
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
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(250,500));
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
		gLabel = new JLabel ("Gravity (m/s^2):");
		rLabel = new JLabel ("Radius (m):");
		lLabel = new JLabel ("String Length (m):");
		wLabel = new JLabel ("Ball Mass (kg)");
		vLabel = new JLabel ("Ball Speed (m/s)");
	}
	
	private void makeGUI(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		this.add(gLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		this.add(gSpinner, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(rLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		this.add(rSpinner, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(lLabel, c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(lSpinner, c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(wLabel, c);
		
		c.gridx = 1;
		c.gridy = 3;
		this.add(wSpinner, c);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(vLabel, c);
		
		c.gridx = 1;
		c.gridy = 4;
		this.add(vSpinner, c);
	}
}

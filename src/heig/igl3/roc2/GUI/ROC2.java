package heig.igl3.roc2.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ROC2 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private ROC2StatusBar statusBar;

	public ROC2() {
		super();
		
		setJMenuBar(new ROC2MenuBar());
		statusBar = new ROC2StatusBar();
		
		
		setTitle("ROC2");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setResizable(true);
		
		add(statusBar, BorderLayout.SOUTH);
		statusBar.setMessage("Statut");
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		ROC2 gui = new ROC2();

	}

	@Override
	public void actionPerformed(ActionEvent e) {}

}

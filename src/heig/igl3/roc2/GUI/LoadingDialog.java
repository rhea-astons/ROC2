package heig.igl3.roc2.GUI;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class LoadingDialog extends JDialog {
	
	JProgressBar progressBar;
    JLabel lblLoading;
    
    public LoadingDialog(JFrame frame, boolean modal, String message) {
    	super(frame, modal);
    	
    	this.progressBar = new JProgressBar();
    	this.lblLoading = new JLabel();
    	this.lblLoading.setText(message);
    	this.progressBar.setIndeterminate(true);
        add(lblLoading, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
        setTitle("ROC2");
    }
}

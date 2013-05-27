package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Utilisateur;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ROC2 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private ROC2StatusBar statusBar;
	private ROC2MenuBar menuBar;

	private Utilisateur connectedUser;
	
	public ROC2() {
		super();
		
		connectedUser = Login.user;
		
		
		
		menuBar  = new ROC2MenuBar();
		setJMenuBar(menuBar);
		menuBar.menuItemQuitter.addActionListener(this);
		menuBar.menuItemCategories.addActionListener(this);
		menuBar.menuItemMouvements.addActionListener(this);
		
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
		try {
			Utilisateur user;
			Login frame=new Login(null, true, false);
            frame.setSize(300,100);
            frame.setLocationRelativeTo(null);
            frame.setUndecorated(true);
            frame.setVisible(true);
            user = Login.user;
            frame.dispose();
            
            while (user == null) {
                Login framebis=new Login(null, true, true);
                framebis.setSize(300,133);
                framebis.setLocationRelativeTo(null);
                framebis.setUndecorated(true);       
                framebis.setVisible(true);   
                user = Login.user;
                framebis.dispose();        
            }
            ROC2 gui = new ROC2();
    		gui.setVisible(true);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Le programme a rencontrée une erreur importante. Veuillez contacter le support. "
	                + "Message éventuel : " + e.getMessage() + " " + e.getLocalizedMessage());
	            System.exit(-1) ;
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println ("Selected: " + actionCommand);
		
		switch(actionCommand){
			case "Quitter":
				
				System.exit(0);
				break;
			case "Mouvements":
				//TODO
			case "Catégories":
				//TODO
			case "A propos":
				//TODO
		}
	}
	
	
		
}

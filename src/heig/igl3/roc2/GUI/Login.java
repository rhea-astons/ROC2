package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Utilisateur;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JDialog implements ActionListener {
	JButton btSubmit, btCancel;
    JPanel panel;
    JLabel lblLogin, lblPassword, lblErreur;
    static public Utilisateur user;
    final JTextField  userLogin, userPassword;
    
    public Login(JFrame frame, boolean modal, boolean error) {
    	super(frame, modal);
    	
    	lblErreur = new JLabel();
    	lblErreur.setText(" Identifiants incorrects");
    	
    	lblLogin = new JLabel();
    	lblLogin.setText(" Nom d'utilisateur : ");
    	userLogin = new JTextField(15);
    	
    	lblPassword = new JLabel();
    	lblPassword.setText(" Mot de passe :");
    	userPassword = new JPasswordField(15);
    	
    	btSubmit=new JButton("Me connecter");
    	btCancel=new JButton ("Quitter");
    	
    	KeyAdapter actionClavier = new KeyAdapter(){
    		@Override
            public void keyPressed(KeyEvent e){
    			int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER)
                    btSubmit.doClick();
                else if (key == KeyEvent.VK_ESCAPE)
                    btCancel.doClick();
    		}
    	};
    	
    	userPassword.addKeyListener(actionClavier);
    	userLogin.addKeyListener(actionClavier);
    	
    	if(error) {
    		panel=new JPanel(new GridLayout(4,1));
    		panel.add(lblErreur);
    	} else {
    		panel=new JPanel(new GridLayout(3,1));
        }
    	
    	panel.add(lblLogin);
        panel.add(userLogin);
        panel.add(lblPassword);
        panel.add(userPassword);
        panel.add(btCancel);
        panel.add(btSubmit);
        if(error)
            panel.add(lblErreur);
    	
        add(panel,BorderLayout.CENTER);
        btSubmit.addActionListener(this);
        btCancel.addActionListener(this);
        setTitle("ROC2");
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btCancel)
			System.exit(0);
		String user=userLogin.getText();
        String pass=userPassword.getText();
        Login.user = Utilisateur.connect(user, pass);
        setVisible(false);
	}

}

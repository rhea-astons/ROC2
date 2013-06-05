package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Data.Roc2DB;

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
import javax.swing.JTextField;

public class CategoryEditor extends JDialog implements ActionListener {
	
	private JPanel panel;
	private JTextField catName;
	private JLabel lblName;
	private JButton btSubmit, btCancel;
	public Categorie categorie, catToEdit;
	private Budget budget;
	private boolean edit;
	
	public CategoryEditor(JFrame frame, boolean modal, Budget budget) {
		super(frame, modal);
		
		this.budget = budget;
    	
		lblName = new JLabel();
		lblName.setText("Nom:");
    	catName = new JTextField(25);
    	
    	btSubmit=new JButton("Valider");
    	btCancel=new JButton ("Annuler");
    	
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
    	
    	catName.addKeyListener(actionClavier);
    	
    	panel = new JPanel(new GridLayout(2,1));
    	panel.add(lblName);
        panel.add(catName);
        panel.add(btCancel);
        panel.add(btSubmit);
        add(panel,BorderLayout.CENTER);
        btSubmit.addActionListener(this);
        btCancel.addActionListener(this);
        setTitle("ROC2");
	}
	
	public CategoryEditor(JFrame frame, boolean modal, Budget budget, Categorie categorie){
		this(frame, modal, budget);
		this.catToEdit = categorie;
		this.edit = true;
		this.catName.setText(categorie.libelle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btCancel) {
			this.setVisible(false);
			categorie = null;
		}
		else if(catName.getText().length() > 0 && edit)
			categorie = Roc2DB.editCategorie(catName.getText(), catToEdit, budget.idBudget);
		else if(catName.getText().length() > 0)
			categorie = Roc2DB.addCategorie(catName.getText(), budget.idBudget);
		
        setVisible(false);
	}

}

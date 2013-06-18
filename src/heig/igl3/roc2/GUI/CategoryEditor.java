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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CategoryEditor extends JDialog implements ActionListener {
	
	private JPanel panel;
	private JTextField catName;
	private JLabel lblName;
	private JButton btSubmit, btCancel;
	public Categorie categorie, catToEdit = null;
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
		boolean exist;
		if (e.getSource()==btCancel) {
			this.setVisible(false);
			categorie = null;
		}
		if (e.getSource()==btSubmit){
			exist = exist(catName.getText());
			if(catName.getText().length() > 3 && edit && !exist){
				categorie = Roc2DB.editCategorie(catName.getText(), catToEdit, budget.idBudget);
				setVisible(false);
			}
			else if(catName.getText().length() > 3 && !exist){
				categorie = Roc2DB.addCategorie(catName.getText(), budget.idBudget);
				setVisible(false);
			}
			else{
				if(exist){
					JOptionPane.showMessageDialog(this, "Sous catégorie existante");
				}else{
					JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de plus de 3 caractères");
				}
				
			}
		}
	}
	
	private boolean exist(String name){
		boolean exist = false;
		if(catToEdit == null){
			for (Categorie cat : budget.categories){
				if ( cat.libelle.equalsIgnoreCase(name)){
					exist = true;
				}
			}
		}else{
			for (Categorie cat : budget.categories){
				if ( cat.libelle.equalsIgnoreCase(name) && cat.id != catToEdit.id){
					exist = true;
				}
			}
		}
		return exist;
	}

}

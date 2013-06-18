package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.SousCategorie;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SousCategoryEditor extends JDialog implements ActionListener {
	private JPanel panel;
	private JTextField sousCatName;
	private JLabel lblName;
	private JButton btSubmit, btCancel;
	public SousCategorie sousCategorie, sousCatToEdit;
	private Categorie categorie;
	private boolean edit;
	
	public SousCategoryEditor(JFrame frame, boolean modal, Categorie categorie) {
		super(frame, modal);
		
		this.categorie = categorie;
    	
		lblName = new JLabel();
		lblName.setText("Nom:");
		sousCatName = new JTextField(25);
    	
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
    	
    	sousCatName.addKeyListener(actionClavier);
    	
    	panel = new JPanel(new GridLayout(2,1));
    	panel.add(lblName);
        panel.add(sousCatName);
        panel.add(btCancel);
        panel.add(btSubmit);
        add(panel,BorderLayout.CENTER);
        btSubmit.addActionListener(this);
        btCancel.addActionListener(this);
        setTitle("ROC2");
	}
	
	public SousCategoryEditor(JFrame frame, boolean modal, Categorie categorie, SousCategorie sousCategorie){
		this(frame, modal, categorie);
		this.sousCatToEdit = sousCategorie;
		this.edit = true;
		this.sousCatName.setText(sousCategorie.libelle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean exist;
		if (e.getSource()==btCancel) {
			this.setVisible(false);
			sousCategorie = null;
		}
		if (e.getSource()==btSubmit){
			exist = exist(sousCatName.getText());
			if(sousCatName.getText().length() > 3 && edit && !exist){
				sousCategorie = Roc2DB.editSousCategorie(sousCatName.getText(), sousCatToEdit);
				setVisible(false);
			}
			else if(sousCatName.getText().length() > 3 && !exist){
				sousCategorie = Roc2DB.addSousCategorie(sousCatName.getText(),categorie.id );
				setVisible(false);
			} else{
				if(exist){
					JOptionPane.showMessageDialog(this, "Catégorie existante");
				}else{
					JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de plus de 3 caractères");
				}
			}
		}
	}
	private boolean exist(String name){
		boolean exist = false;
		if(sousCatToEdit == null){
			for (SousCategorie sousCat : categorie.sousCategories){
				if ( sousCat.libelle.equalsIgnoreCase(name)){
					exist = true;
				}
			}
		}else{
			for (SousCategorie sousCat : categorie.sousCategories){
				if ( sousCat.libelle.equalsIgnoreCase(name) && sousCat.id != sousCatToEdit.id){
					exist = true;
				}
			}
		}
		return exist;
	}

}

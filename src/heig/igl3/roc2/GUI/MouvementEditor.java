package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Mouvement;
import heig.igl3.roc2.Business.SousCategorie;
import heig.igl3.roc2.Data.Roc2DB;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MouvementEditor extends JDialog implements ActionListener, ItemListener {
	
	private JPanel panel;
	private JTextField libelle, montant, date;
	private JComboBox<Categorie> CBcategorie;
	private JComboBox<SousCategorie> CBsousCategorie;
	private JComboBox<String> CBtype, CBtypeES;
	private JComboBox<Integer> CBperiodicite;
	private JLabel lblLibelle, lblMontant, lblDate, lblCategorie, lblSousCategorie, lblType, lblTypeES, lblPeriodicite;
	private JButton btSubmit, btCancel;
	public Mouvement mouvement, mouvToEdit;
	private Budget budget;
	private boolean edit;
	
	public MouvementEditor(JFrame frame, boolean modal, Budget budget) {
		super(frame, modal);
		this.budget = budget;
		
		lblLibelle = new JLabel("Libellé:");
		lblMontant = new JLabel("Montant:");
		lblDate = new JLabel("Date:");
		lblCategorie = new JLabel("Catégorie:");
		lblSousCategorie = new JLabel("Sous-catégorie:");
		lblType = new JLabel("Type:");
		lblTypeES = new JLabel("Entrée/Sortie:");
		lblPeriodicite = new JLabel("Périodicité:");
		
		libelle = new JTextField(25);
		montant = new JTextField(10);
		date = new JTextField(10);
		CBtype = new JComboBox<String>();
		CBtype.addItem("Ponctuel");
		CBtype.addItem("Récurrent");
		CBtypeES = new JComboBox<String>();
		CBtypeES.addItem("Entrée");
		CBtypeES.addItem("Sortie");
		CBperiodicite = new JComboBox<Integer>();
		for (int i = 1; i<12;i++) {
			CBperiodicite.addItem(i);
		}

		CBcategorie = new JComboBox<Categorie>();
		for (Categorie cat : budget.categories) {
			CBcategorie.addItem(cat);
		}
		CBcategorie.addItemListener(this);
		
		CBsousCategorie = new JComboBox<SousCategorie>();
		Categorie cat = (Categorie) CBcategorie.getSelectedItem();
		for (SousCategorie sousCat : cat.sousCategories)
			CBsousCategorie.addItem(sousCat);
		
		btSubmit=new JButton("Valider");
    	btCancel=new JButton ("Annuler");
    	btSubmit.addActionListener(this);
        btCancel.addActionListener(this);
    	
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
    	
		panel = new JPanel(new GridLayout(8,1));
		panel.add(lblLibelle);
		panel.add(libelle);
		panel.add(lblMontant);
		panel.add(montant);
		panel.add(lblDate);
		panel.add(date);
		panel.add(lblCategorie);
		panel.add(CBcategorie);
		panel.add(lblSousCategorie);
		panel.add(CBsousCategorie);
		//panel.add(lblType);
		//panel.add(CBtype);
		panel.add(lblTypeES);
		panel.add(CBtypeES);
		panel.add(lblPeriodicite);
		panel.add(CBperiodicite);
		panel.add(btCancel);
		panel.add(btSubmit);
		add(panel, BorderLayout.CENTER);
		for (Component c : panel.getComponents()){
			c.addKeyListener(actionClavier);
		}
		
		
	}
	
	public MouvementEditor(JFrame frame, boolean modal, Budget budget, Mouvement mouv) {
		this(frame, modal, budget);
		mouvToEdit = mouv;
		this.edit = true;
		libelle.setText(mouv.libelle);
		montant.setText(Float.toString(mouv.montant));
		GregorianCalendar dateGreg = mouv.date;
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setCalendar(dateGreg);
		date.setText(dateFormat.format(dateGreg.getTime()));
		Categorie categorie = null;
		for (Categorie cat : budget.categories) {
			if (cat.id == mouv.idCategorie) {
				categorie = cat;
			}
		}
		CBcategorie.setSelectedItem(categorie);
		SousCategorie sousCategorie = null;
		for (SousCategorie sousCat : categorie.sousCategories) {
			if (sousCat.id == mouv.idSousCategorie) {
				sousCategorie = sousCat;
			}
		}
		CBsousCategorie.setSelectedItem(sousCategorie);
		CBtypeES.setSelectedIndex(mouv.ESType);
		CBperiodicite.setSelectedIndex(mouv.periodicite-1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btCancel) {
			this.setVisible(false);
			mouvement = null;
		}
		else if(libelle.getText().length() > 0 &&
				 montant.getText().length() > 0 &&
				 date.getText().length() > 0 &&
				 edit) {
			Categorie cat = (Categorie) CBcategorie.getSelectedItem();
			SousCategorie sousCat = (SousCategorie) CBsousCategorie.getSelectedItem();
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			Date dateDate = null;
			try {
				dateDate = df.parse(date.getText());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dateDate);
			mouvement = Roc2DB.editMouvement(mouvToEdit.id, libelle.getText(), Float.parseFloat(montant.getText()), 1, CBtypeES.getSelectedIndex(), cal, CBperiodicite.getSelectedIndex()+1, cat, sousCat, budget.idBudget);
			
		} else if(libelle.getText().length() > 0 &&
				 montant.getText().length() > 0 &&
				 date.getText().length() > 0) {
			Categorie cat = (Categorie) CBcategorie.getSelectedItem();
			SousCategorie sousCat = (SousCategorie) CBsousCategorie.getSelectedItem();
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			Date dateDate = null;
			try {
				dateDate = df.parse(date.getText());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dateDate);
			
			mouvement = Roc2DB.addMouvement(libelle.getText(), Float.parseFloat(montant.getText()), 1, CBtypeES.getSelectedIndex(), cal, CBperiodicite.getSelectedIndex()+1, cat, sousCat, budget.idBudget);
		}
		
        setVisible(false);
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CBsousCategorie.removeAllItems();
		Categorie cat = (Categorie) CBcategorie.getSelectedItem();
		for (SousCategorie sousCat : cat.sousCategories)
			CBsousCategorie.addItem(sousCat);
		
	}

}

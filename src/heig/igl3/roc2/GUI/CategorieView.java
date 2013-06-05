package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.SousCategorie;
import heig.igl3.roc2.Data.Roc2DB;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CategorieView extends JPanel implements ActionListener, ListSelectionListener{
	
	DefaultListModel<Categorie> catModel;
	DefaultListModel<SousCategorie> sousCatModel;
	JList<Categorie> catList;
	JList<SousCategorie> sousCatList;
	JScrollPane catScroll, sousCatScroll;
	Categorie selectedCat;
	Budget budget;
	
	public CategorieView(Budget budget) {
		this.budget = budget;
		this.setLayout(new BorderLayout(0,0));
		
		selectedCat = null;
		
		catModel = new DefaultListModel<Categorie>();
		catList = new JList<Categorie>(catModel);
		catList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		catList.addListSelectionListener(this);
		catScroll = new JScrollPane(catList);
		
		sousCatModel = new DefaultListModel<SousCategorie>();
		sousCatList = new JList<SousCategorie>(sousCatModel);
		sousCatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sousCatScroll = new JScrollPane(sousCatList);
		
		for(Categorie categorie : budget.categories) {
			this.catModel.addElement(categorie);
		}

		// Structure
		JPanel splitPanel = new JPanel(new GridLayout(1,2));
		JPanel catPanel = new JPanel(new BorderLayout(0,0));
		JPanel sousCatPanel = new JPanel(new BorderLayout(0,0));
		JPanel catButtons = new JPanel();
		JPanel sousCatButtons = new JPanel();
		
		// Buttons
		JButton btAddCat = new JButton("Ajouter");
		btAddCat.setName("addCat");
		btAddCat.addActionListener(this);
		
		JButton btEditCat = new JButton("Editer");
		btEditCat.setName("editCat");
		btEditCat.addActionListener(this);
		
		JButton btDelCat = new JButton("Supprimer");
		btDelCat.setName("delCat");
		btDelCat.addActionListener(this);
		
		JButton btAddSousCat = new JButton("Ajouter");
		btAddSousCat.setName("addSousCat");
		btAddSousCat.addActionListener(this);
		
		JButton btEditSousCat = new JButton("Editer");
		btEditSousCat.setName("editSousCat");
		btEditSousCat.addActionListener(this);
		
		JButton btDelSousCat = new JButton("Supprimer");
		btDelSousCat.setName("delSousCat");
		btDelSousCat.addActionListener(this);
		
		splitPanel.add(catPanel);
		splitPanel.add(sousCatPanel);
		
		catPanel.add(catScroll, BorderLayout.CENTER);
		catPanel.add(catButtons, BorderLayout.SOUTH);
		
		sousCatPanel.add(sousCatScroll, BorderLayout.CENTER);
		sousCatPanel.add(sousCatButtons, BorderLayout.SOUTH);
		
		catButtons.add(btAddCat);
		catButtons.add(btEditCat);
		catButtons.add(btDelCat);
		
		sousCatButtons.add(btAddSousCat);
		sousCatButtons.add(btEditSousCat);
		sousCatButtons.add(btDelSousCat);
		
		add(splitPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		String actionCommand = source.getName();
		Categorie selectedCat = null;
		SousCategorie selectedSousCat = null;
		if (!catList.isSelectionEmpty()) {
			selectedCat = catModel.get(catList.getSelectedIndex());
		}
		if (!sousCatList.isSelectionEmpty()) {
			selectedSousCat = sousCatModel.get(sousCatList.getSelectedIndex());
		}
		
		switch(actionCommand) {
		case "addCat":
			break;
		case "editCat":
			break;
		case "delCat":
			if(selectedCat != null)
				System.out.println("delete cat:" +selectedCat);
			budget.categories.remove(selectedCat);
			break;
		case "addSousCat":
			break;
		case "editSousCat":
			break;
		case "delSousCat":
			//if(selectedSousCat != null && Roc2DB.delSousCategorie(selectedSousCat.id))
			
				
			break;
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<Categorie> source = (JList<Categorie>) e.getSource();
		
		Categorie newSelectedCat = catModel.get(source.getSelectedIndex());
		
		if(selectedCat == null || selectedCat != newSelectedCat) {
			selectedCat = newSelectedCat;
			sousCatModel.removeAllElements();
			for(SousCategorie sousCat : selectedCat.sousCategories)
				sousCatModel.addElement(sousCat);
		}
	}

}

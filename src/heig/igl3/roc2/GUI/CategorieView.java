package heig.igl3.roc2.GUI;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.SousCategorie;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
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
	
	public CategorieView(Budget budget) {
		this.setLayout(new BorderLayout(0,0));
		
		JPanel splitPanel = new JPanel(new GridLayout(1,2));
		
		selectedCat = null;
		
		this.catModel = new DefaultListModel<Categorie>();
		this.sousCatModel = new DefaultListModel<SousCategorie>();
		
		
		for(Categorie categorie : budget.categories) {
			this.catModel.addElement(categorie);
		}
		this.catList = new JList<Categorie>(catModel);
		catList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.sousCatList = new JList<SousCategorie>(sousCatModel);
		sousCatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		catScroll = new JScrollPane(catList);
		sousCatScroll = new JScrollPane(sousCatList);
		
		splitPanel.add(catScroll);
		splitPanel.add(sousCatScroll);
		
		add(splitPanel);
		
		catList.addListSelectionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		System.out.println(actionCommand);
		
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

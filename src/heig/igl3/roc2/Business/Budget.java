package heig.igl3.roc2.Business;

import java.util.ArrayList;

public class Budget {
	
	private ArrayList<Categorie> categories;
	private ArrayList<Mouvement> mouvements;
	private int idBudget;
	public Budget(int idBudget, ArrayList<Categorie> categories,ArrayList<Mouvement> mouvements) {
		this.idBudget = idBudget;
		this.categories = categories;
		this.mouvements = mouvements;
		
	}
	

}

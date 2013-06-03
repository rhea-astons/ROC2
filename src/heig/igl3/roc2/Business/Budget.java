package heig.igl3.roc2.Business;

import java.util.ArrayList;
import java.util.Iterator;

public class Budget {
	
	private ArrayList<Categorie> categories;
	private ArrayList<Mouvement> mouvements;
	private int idBudget;
	
	public Budget(int idBudget, ArrayList<Categorie> categories,ArrayList<Mouvement> mouvements) {
		this.idBudget = idBudget;
		this.categories = categories;
		this.mouvements = mouvements;
		
	}
	
	public boolean existCategorie(String cat){
			boolean is = false;
			Categorie c;
			Iterator i = categories.iterator();
			while(i.hasNext() || !is){
				c = (Categorie) i.next();
				if ( c.libelle.toLowerCase() == cat.toLowerCase()){
					is = true;
				}
			}
			return is;
	}
	
}

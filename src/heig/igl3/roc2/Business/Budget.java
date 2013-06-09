package heig.igl3.roc2.Business;

import java.util.ArrayList;
import java.util.Iterator;

public class Budget {
	
	public ArrayList<Categorie> categories;
	public ArrayList<Mouvement> mouvements;
	public int idBudget;
	
	
	
	
	public Budget(int idBudget, ArrayList<Categorie> categories,ArrayList<Mouvement> mouvements) {
		this.idBudget = idBudget;
		this.categories = categories;
		this.mouvements = mouvements;
		
	}
	
	public boolean existCategorie(String cat){
			boolean is = false;
			Categorie c;
			Iterator<Categorie> i = categories.iterator();
			while(i.hasNext() || !is){
				c = (Categorie) i.next();
				if ( c.libelle.toLowerCase() == cat.toLowerCase()){
					is = true;
				}
			}
			return is;
	}
	
	public Double sommeCategorie(int idCat){
		Double somme = 0.0;	
			for (Mouvement mouv : this.mouvements){
				if(mouv.idCategorie == idCat){		
					somme += mouv.montant;
					
				}
			}
		return somme;
	}
	
	public Categorie getCategorie(int idCat) {
		Categorie categorie = null;
		for (Categorie cat : this.categories)
			if (cat.id == idCat) {
				categorie = cat;
				break;
			}
		return categorie;
	}
	
	public SousCategorie getSousCategorie(int idCat, int idSousCat) {
		Categorie categorie = this.getCategorie(idCat);
		return categorie.getSousCategorie(idSousCat);
	}
	
	
	
}

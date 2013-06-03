package heig.igl3.roc2.Business;

import java.util.ArrayList;
import java.util.Iterator;

public class Categorie {
	
	public String libelle;
	public int id;
	public int idBudget;
	public ArrayList<SousCategorie> sousCategories;
	
	
	public Categorie(int id, String libelle,int idBudget, ArrayList<SousCategorie> sousCategories) {
		this.id = id;
		this.libelle = libelle;
		this.idBudget = idBudget;
		this.sousCategories = sousCategories;
	}

	public boolean existSousCategorie(String cat){
		boolean is = false;
		SousCategorie c;
		Iterator i = sousCategories.iterator();
		while(i.hasNext() || !is){
			c = (SousCategorie) i.next();
			if ( c.libelle.toLowerCase() == cat.toLowerCase()){
				is = true;
			}
		}
		return is;
	}
	
}

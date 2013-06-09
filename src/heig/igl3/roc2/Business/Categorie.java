package heig.igl3.roc2.Business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Categorie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		Iterator<SousCategorie> i = sousCategories.iterator();
		while(i.hasNext() || !is){
			c = (SousCategorie) i.next();
			if ( c.libelle.toLowerCase() == cat.toLowerCase()){
				is = true;
			}
		}
		return is;
	}
	
	public String toString() {
		return this.libelle;
	}
	
	public boolean equals(Categorie otherCat) {
		if(this.id == otherCat.id)
			return true;
		else
			return false;
	}
	
	public SousCategorie getSousCategorie(int idSousCat) {
		SousCategorie sousCategorie = null;
		for (SousCategorie sousCat : this.sousCategories)
			if(sousCat.id == idSousCat) {
				sousCategorie = sousCat;
				break;
			}
		return sousCategorie;
	}
}

package heig.igl3.roc2.Business;

import java.util.ArrayList;

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

}

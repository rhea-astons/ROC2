package heig.igl3.roc2.Business;

import java.util.ArrayList;

public class Categorie {
	
	public String libelle;
	public int id;
	public ArrayList<SousCategorie> sousCategories;
	
	public Categorie(int id, String libelle, ArrayList<SousCategorie> sousCategories) {
		this.id = id;
		this.libelle = libelle;
		this.sousCategories = sousCategories;
	}

}

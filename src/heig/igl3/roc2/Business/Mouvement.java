package heig.igl3.roc2.Business;

import java.util.Date;

public class Mouvement {
	
	public enum TypeMouvement {PONCTUEL, RECURENT}
	
	public int id;
	public String libelle;
	public float montant;
	public int type;
	public Date date;
	public int periodicite;
	public Categorie categorie;
	public SousCategorie sousCategorie;
	public int idBudget;
	
	public Mouvement(int id, String libelle, float montant, int type, Date date, int periodicite, Categorie categorie, SousCategorie sousCategorie, int idBudget) {
		this.id = id;
		this.libelle = libelle;
		this.montant = montant;
		this.type = type;
		this.date = date;
		this.periodicite = periodicite;
		this.categorie = categorie;
		this.idBudget = idBudget;
	}

}

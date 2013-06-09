package heig.igl3.roc2.Business;

import java.util.Date;

@SuppressWarnings("serial")
public class Mouvement {
	
	public enum TypeMouvement {PONCTUEL, RECURENT}
	
	public int id;
	public String libelle;
	public float montant;
	public int type;
	public Date date;
	public int periodicite;
	public int idCategorie;
	public int idSousCategorie;
	public int idBudget;
	
	public Mouvement(int id, String libelle, float montant, int type, Date date, int periodicite, int idCategorie, int idSousCategorie, int idBudget) {
		this.id = id;
		this.libelle = libelle;
		this.montant = montant;
		this.type = type;
		this.date = date;
		this.periodicite = periodicite;
		this.idCategorie = idCategorie;
		this.idSousCategorie = idSousCategorie;
		this.idBudget = idBudget;
	}
	
	

}

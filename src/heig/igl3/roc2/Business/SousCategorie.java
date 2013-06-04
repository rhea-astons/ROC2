package heig.igl3.roc2.Business;

public class SousCategorie {
	
	public int id;
	public String libelle;
	public int idCategorie;
	
	public SousCategorie(int id, String libelle, int idCategorie) {
		this.id = id;
		this.libelle = libelle;
		this.idCategorie = idCategorie;
	}
	
	public String toString() {
		return this.libelle;
	}

}

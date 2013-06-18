package heig.igl3.roc2.Business;

public class SousCategorie {
	
	public int id;
	public String libelle;
	public int idCategorie;
	
	public SousCategorie(){
		
	}
	
	public SousCategorie(int id, String libelle, int idCategorie) {
		this.id = id;
		this.libelle = libelle;
		this.idCategorie = idCategorie;
	}
	
	public String toString() {
		return this.libelle;
	}
	
	public boolean equals(SousCategorie otherSousCat) {
		if(this.id == otherSousCat.id)
			return true;
		else
			return false;
	}

}

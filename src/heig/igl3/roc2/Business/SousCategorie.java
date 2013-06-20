package heig.igl3.roc2.Business;

/**
 * Classe sous catégorie
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
public class SousCategorie {

	public int id;
	public String libelle;
	public int idCategorie;

	/**
	 * Constructeur
	 * 
	 * @param id
	 *            id de la sous catégorie
	 * @param libelle
	 *            nom de la sous catégorie
	 * @param idCategorie
	 *            id de la catégorie référente
	 */
	public SousCategorie(int id, String libelle, int idCategorie) {
		this.id = id;
		this.libelle = libelle;
		this.idCategorie = idCategorie;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.libelle;
	}

	/**
	 * Vérifie si deux sous catégories sont égales
	 * 
	 * @param otherSousCat
	 *            la catégorie à vérifier
	 * @return true si égales, false sinon
	 */
	public boolean equals(SousCategorie otherSousCat) {
		if (this.id == otherSousCat.id)
			return true;
		else
			return false;
	}

}

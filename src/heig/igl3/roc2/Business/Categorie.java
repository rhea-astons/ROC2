package heig.igl3.roc2.Business;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe de Catégorie
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
public class Categorie implements Serializable {

	private static final long serialVersionUID = 1L;
	public String libelle;
	public int id;
	public int idBudget;
	public ArrayList<SousCategorie> sousCategories;

	/**
	 * Constructeur
	 * 
	 * @param id
	 *            id de la catégorie
	 * @param libelle
	 *            nom de la catégorie
	 * @param idBudget
	 *            id du budget référent
	 * @param sousCategories
	 *            liste de sous catégories appartenant à la catégorie
	 */
	public Categorie(int id, String libelle, int idBudget,
			ArrayList<SousCategorie> sousCategories) {
		this.id = id;
		this.libelle = libelle;
		this.idBudget = idBudget;
		this.sousCategories = sousCategories;
	}

	/**
	 * Vérifie si la sous catégorie existe
	 * 
	 * @param sousCatLibelle
	 *            nom de la catégorie à vérifier
	 * @return true si existante, false sinon.
	 */
	public boolean existSousCategorie(String sousCatLibelle) {
		boolean is = false;
		for (SousCategorie sousCat : sousCategories) {
			if (sousCat.libelle.toLowerCase() == sousCatLibelle.toLowerCase()) {
				is = true;
			}
		}
		return is;
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
	 * Vérifie si une catégorie est égale à une autre
	 * 
	 * @param otherCat
	 *            la catégorie à vérifier
	 * @return true si égale, false sinon.
	 */
	public boolean equals(Categorie otherCat) {
		if (this.id == otherCat.id)
			return true;
		else
			return false;
	}

	/**
	 * getter de sous catégorie
	 * 
	 * @param idSousCat
	 *            l'id de la sous catégorie à récupérer
	 * @return la sous catégorie demandée
	 */
	public SousCategorie getSousCategorie(int idSousCat) {
		SousCategorie sousCategorie = null;
		for (SousCategorie sousCat : this.sousCategories)
			if (sousCat.id == idSousCat) {
				sousCategorie = sousCat;
				break;
			}
		return sousCategorie;
	}

	/**
	 * Ajoute une sous catégorie
	 * 
	 * @param sousCategorie
	 *            la sous catégorie à ajouter
	 */
	public void addSousCategorie(SousCategorie sousCategorie) {
		sousCategories.add(sousCategorie);
	}
}

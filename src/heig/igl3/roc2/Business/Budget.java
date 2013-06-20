package heig.igl3.roc2.Business;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe Budget Structure de stockage des budgets
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */

public class Budget {

	public ArrayList<Categorie> categories;
	public ArrayList<Mouvement> mouvements;
	public int idBudget;

	/**
	 * Constructeur
	 * 
	 * @param idBudget
	 * @param categories
	 * @param mouvements
	 */
	public Budget(int idBudget, ArrayList<Categorie> categories,
			ArrayList<Mouvement> mouvements) {
		this.idBudget = idBudget;
		this.categories = categories;
		this.mouvements = mouvements;

	}

	/**
	 * Vérifie si une catégorie est existante
	 * 
	 * @param cat
	 * @return true si elle existe, false sinon
	 */
	public boolean existCategorie(String cat) {
		boolean is = false;
		Categorie c;
		Iterator<Categorie> i = categories.iterator();
		while (i.hasNext() || !is) {
			c = (Categorie) i.next();
			if (c.libelle.toLowerCase() == cat.toLowerCase()) {
				is = true;
			}
		}
		return is;
	}

	/**
	 * Effectue la somme des montants en sortie par catégorie
	 * 
	 * @param idCat
	 * @return la somme
	 */
	public Double sommeCategorieSorties(int idCat) {
		Double somme = 0.0;
		for (Mouvement mouv : this.mouvements) {
			if (mouv.idCategorie == idCat && mouv.ESType == 1) {
				somme += mouv.montant;

			}
		}
		return somme;
	}

	/**
	 * Effectue la somme des montants en entrée par catégorie
	 * 
	 * @param idCat
	 * @return la somme
	 */
	public Double sommeCategorieEntrees(int idCat) {
		Double somme = 0.0;
		for (Mouvement mouv : this.mouvements) {
			if (mouv.idCategorie == idCat && mouv.ESType == 0) {
				somme += mouv.montant;

			}
		}
		return somme;
	}

	/**
	 * getter de catégorie
	 * 
	 * @param idCat
	 * @return la catégorie demandée
	 */
	public Categorie getCategorie(int idCat) {
		Categorie categorie = null;
		for (Categorie cat : this.categories)
			if (cat.id == idCat) {
				categorie = cat;
				break;
			}
		return categorie;
	}

	/**
	 * Getter de sous catégorie
	 * 
	 * @param idCat
	 * @param idSousCat
	 * @return la sous catégorie demandée
	 */
	public SousCategorie getSousCategorie(int idCat, int idSousCat) {
		Categorie categorie = this.getCategorie(idCat);
		return categorie.getSousCategorie(idSousCat);
	}

}

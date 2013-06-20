package heig.igl3.roc2.Business;

import java.util.GregorianCalendar;

/**
 * Classe mouvement
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class Mouvement implements Comparable {

	public enum TypeMouvement {
		PONCTUEL, RECURENT
	}

	public enum TypeES {
		ENTREE, SORTIE
	}

	public int id;
	public String libelle;
	public float montant;
	public int type;
	public int ESType;
	public GregorianCalendar date;
	public int periodicite;
	public int idCategorie;
	public int idSousCategorie;
	public int idBudget;

	/**
	 * Constructeur
	 * 
	 * @param id
	 *            id du mouvement
	 * @param libelle
	 *            nom du mouvement
	 * @param montant
	 *            montant du mouvement
	 * @param type
	 *            type du mouvement 0=Ponctuel, 1=Récurent
	 * @param ESType
	 *            0=entrée, 1=sortie
	 * @param date
	 *            date du mouvement
	 * @param periodicite
	 *            périodicité
	 * @param idCategorie
	 *            id de la catégorie
	 * @param idSousCategorie
	 *            id de la sous catégorie
	 * @param idBudget
	 *            id du budget
	 */
	public Mouvement(int id, String libelle, float montant, int type,
			int ESType, GregorianCalendar date, int periodicite,
			int idCategorie, int idSousCategorie, int idBudget) {
		this.id = id;
		this.libelle = libelle;
		this.montant = montant;
		this.type = type;
		this.ESType = ESType;
		this.date = date;
		this.periodicite = periodicite;
		this.idCategorie = idCategorie;
		this.idSousCategorie = idSousCategorie;
		this.idBudget = idBudget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		Mouvement m = (Mouvement) o;
		int retour = 0;
		if (m.date == this.date) {
			retour = 1;
		} else {
			retour = 0;
		}
		return retour;
	}

}

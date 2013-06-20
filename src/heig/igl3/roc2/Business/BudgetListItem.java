package heig.igl3.roc2.Business;

/**
 * Classe listant les budgets
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
public class BudgetListItem {
	public int id;
	public String libelle;

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param libelle
	 */
	public BudgetListItem(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.libelle;
	}

}

package heig.igl3.roc2.Business;

public class BudgetListItem {
	public int id;
	public String libelle;
	
	public BudgetListItem(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	public String toString() {
		return this.libelle;
	}

}

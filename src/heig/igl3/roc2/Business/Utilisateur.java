package heig.igl3.roc2.Business;

import heig.igl3.roc2.Data.Roc2DB;

public class Utilisateur {
	public int id;
	public String nom;
	public String prenom;
	public String login;
	public String pwd;
	
	public Utilisateur() {}
	
	public static Utilisateur connect(String user, String pwd) {
		return Roc2DB.getUtilisateur(user, pwd);
	}

}
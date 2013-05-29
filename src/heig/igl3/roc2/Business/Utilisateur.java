package heig.igl3.roc2.Business;

import heig.igl3.roc2.Data.Roc2DB;

public class Utilisateur {
	public int id;
	public String nom;
	public String prenom;
	public String login;
	public String pwd;
	
	public Utilisateur() {}
	
	public Utilisateur(int id, String nom, String prenom, String login, String pwd){
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.login = login;
		this.pwd = pwd;
	}
	
	public static Utilisateur connect(String user, String pwd) {
		return Roc2DB.getUtilisateur(user, pwd);
	}

}

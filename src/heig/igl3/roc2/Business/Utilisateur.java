package heig.igl3.roc2.Business;

import heig.igl3.roc2.Data.Roc2DB;

/**
 * Classe Utilisateur
 * 
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 * 
 */
public class Utilisateur {
	public int id;
	public String nom;
	public String prenom;
	public String login;
	public String pwd;

	public Utilisateur() {
	}

	/**
	 * Constructeur
	 * 
	 * @param id
	 *            id de l'utilisateur
	 * @param nom
	 *            nom de l'utilisateur
	 * @param prenom
	 *            prénom de l'utilisateur
	 * @param login
	 *            login de l'utilisateur
	 * @param pwd
	 *            mot de passe de l'utilisateur
	 */
	public Utilisateur(int id, String nom, String prenom, String login,
			String pwd) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.login = login;
		this.pwd = pwd;
	}

	/**
	 * Connexion de l'utilisateur
	 * 
	 * @param user
	 *            login de l'utilisateur
	 * @param pwd
	 *            mot de passe de l'utilisateur
	 * @return l'utilisateur
	 */
	public static Utilisateur connect(String user, String pwd) {
		return Roc2DB.getUtilisateur(user, pwd);
	}

}

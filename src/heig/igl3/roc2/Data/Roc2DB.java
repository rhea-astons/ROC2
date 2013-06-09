package heig.igl3.roc2.Data;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.BudgetListItem;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Mouvement;
import heig.igl3.roc2.Business.SousCategorie;
import heig.igl3.roc2.Business.Utilisateur;
import java.sql.CallableStatement;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/*import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.ResultSet;
*/

/**
 * @author Raphael Santos, Olivier Francillon, Chris Paccaud, Cédric Bugnon
 *
 */
public final class Roc2DB {
	

	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_ADDRESS = "jdbc:mysql://do.rsdev.ch/";
	private static final String DB_NAME = "rocc";
	private static final String DB_USER = "rocc_app";
	private static final String DB_PASSWORD = "4nv9GuxJ";
	
	@SuppressWarnings("unused")
	private static Driver driver;
	private static java.sql.Connection con;
	private static ResultSet rs, rs2, rs3;
	
	/**
	 * Connection à la base de donnée
	 * @return un booleen: Vrai -> connecté, Faux -> erreur
	 */
	private static Boolean connect() {
		Boolean success = false;
		driver = null;
		con = null;
	
		try {
			driver = (Driver) Class.forName(DB_DRIVER).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println(DB_DRIVER + " est introuvable: " + e.getMessage());
		}

		try {
			con = DriverManager.getConnection(DB_ADDRESS+DB_NAME, DB_USER, DB_PASSWORD);
			success = true;
		} catch (SQLException e) {
			System.out.println("Impossible d'établir la connexion: " + e.getMessage());
		}
		
		return success;
	}
	
	/**
	 * Déconnecte la base de donnée
	 */
	private static void disconnect() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Erreur à la déconnexion: "+ e.getMessage());
			}
		}
	}
	
	/**
	 * Charge toutes les catégories
	 * @param idBudget
	 * @return La liste complète des catégories
	 */
	public static ArrayList<Categorie> getCategories(int idBudget) {
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		Categorie categorie;
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Categorie WHERE idBudget = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, idBudget);
				rs = stmt.executeQuery();
				
				while(rs.next()) {
					categorie = new Categorie(rs.getInt(1), rs.getString(2),rs.getInt(3), getSousCategories(rs.getInt(1)));
					categories.add(categorie);
				}
				rs.close();
				stmt.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return categories;
	}
	
	/**
	 * Selection d'une catégorie
	 * @param id
	 * @return La catégorie demandée
	 * 
	 */
	public static Categorie getCategorie(int id){
		Categorie c = null;
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Categorie WHERE id = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				rs = stmt.executeQuery();
				
				c = new Categorie(rs.getInt(1), rs.getString(2),rs.getInt(3), getSousCategories(rs.getInt(1)));
				
				rs.close();
				stmt.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return c;
	}
	
	
	/**
	 * Ajoute une catégorie
	 * @param nomCategorie
	 * @param idBudget
	 * @return La catégorie ajoutée
	 */
	public static Categorie addCategorie(String nomCategorie, int idBudget){
		Categorie categorie = null;
		
		Boolean connected = connect();
		
		if(connected){
			String query = "INSERT INTO Categorie VALUES (0,?,?)";
			
			try{
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, nomCategorie);
				stmt.setInt(2, idBudget);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				
				if(rs.next())
					categorie = new Categorie(rs.getInt(1), nomCategorie, idBudget,null);
				else
					categorie = null;

				rs.close();
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return categorie;
	}
	
	/**
	 * Modifie une catégorie
	 * @param nomCategorie
	 * @param idCategorie
	 * @param idBudget
	 * @return La catégorie modifiée
	 */
	public static Categorie editCategorie(String nomCategorie, Categorie categorie, int idBudget){
		
		Boolean connected = connect();
		
		if(connected){
			String query = "UPDATE Categorie SET libCategorie = ? WHERE id = ? AND idBudget = ?";
			
			try{
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, nomCategorie);
				stmt.setInt(2, categorie.id);
				stmt.setInt(3, idBudget);
				if(stmt.executeUpdate() > 0)
					categorie.libelle = nomCategorie;
				
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return categorie;
	}
	
	/**
	 * Efface la catégorie
	 * @param id
	 * @return boolean success
	 */
	public static boolean delCategorie(int id){
		String query = "DELETE FROM Categorie WHERE id = ?";
		
		Boolean result = false;
		Boolean connected = connect();
		
		if(connected){
			try{
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				stmt.executeUpdate();
				
				stmt.close();
				disconnect();
				result = true;
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * Cherge toute les sous-catégories
	 * @param idCategorie
	 * @return Liste de toutes les sous-catégorie
	 */
	public static ArrayList<SousCategorie> getSousCategories(int idCategorie) {
		ArrayList<SousCategorie> sousCategories = new ArrayList<SousCategorie>();
		SousCategorie sousCategorie;
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM SousCategorie WHERE idCategorie = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, idCategorie);
				rs2 = stmt.executeQuery();
				
				while(rs2.next()) {
					sousCategorie = new SousCategorie(rs2.getInt(1), rs2.getString(2), rs2.getInt(3));
					sousCategories.add(sousCategorie);
				}
				rs2.close();
				stmt.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return sousCategories;
	}
	
	/**
	 * Charge une sous catégorie
	 * @param id
	 * @return la sous catégorie demandée
	 */
	public static SousCategorie getSousCategorie(int id){
		SousCategorie s = null;
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM SousCategorie WHERE id = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				rs = stmt.executeQuery();
				

				s = new SousCategorie(rs.getInt(1), rs.getString(2), rs.getInt(3));

				rs.close();
				stmt.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return s;
		
	}

	
	/**
	 * Ajoute une sous-catégorie
	 * @param nomSousCategorie
	 * @param idCategorie
	 * @return La sous-catégorie ajoutée
	 */
	public static SousCategorie addSousCategorie(String nomSousCategorie, int idCategorie){
		SousCategorie sousCategorie = null;
		
		Boolean connected = connect();
		
		if(connected){
			String query = "INSERT INTO SousCategorie VALUES (0,?,?)";
			
			try{
				CallableStatement stmt = con.prepareCall(query);
				stmt.setString(1, nomSousCategorie);
				stmt.setInt(2, idCategorie);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				
				/*DEBUG 
				if(rs.next()){
				    System.out.println("La première clef auto-générée vaut ");
				    System.out.println(rs.getObject(1)); 
				}
				*/
				sousCategorie = new SousCategorie(rs.getInt(1), nomSousCategorie, idCategorie);
				
				rs.close();
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		
		return sousCategorie;
	}
	
	/**
	 * Efface une sous-catégorie
	 * @param id
	 */
	public static boolean delSousCategorie(int id){
		String query = "DELETE FROM SousCategorie WHERE id = ?";
		
		Boolean result = false;
		Boolean connected = connect();
		
		if(connected){
			try{
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				stmt.executeUpdate();
				
				stmt.close();
				disconnect();
				result = true;
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * Charge un utilisateur
	 * @param user
	 * @param pwd
	 * @return L'utilisateur demandé
	 */
	public static Utilisateur getUtilisateur(String user, String pwd) {
		Utilisateur u = null;
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Utilisateur WHERE login = ? AND password = MD5(?)";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, user);
				stmt.setString(2, pwd);
				
				rs = stmt.executeQuery();
				if(rs.next()) {
					u = new Utilisateur(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
				}				
				
				rs.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: +" + e.getMessage());
			}	
		}
		return u; 
	}
	
	/**
	 * Ajoute un utilisateur
	 * @param nom
	 * @param prenom
	 * @param login
	 * @param pwd
	 * @return l'utilisateur ajouté
	 */
	public static Utilisateur addUtilisateur(String nom, String prenom, String login, String pwd){
		Utilisateur u = null;
		
		Boolean connected = connect();
		
		if(connected){
			String query = "INSERT INTO Utilisateur VALUES (0,?,?,?,?)";
			
			try{
				CallableStatement stmt = con.prepareCall(query);
				stmt.setString(1, nom);
				stmt.setString(2, prenom);
				stmt.setString(3, login);
				stmt.setString(4, pwd);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				
				/*DEBUG 
				if(rs.next()){
				    System.out.println("La première clef auto-générée vaut ");
				    System.out.println(rs.getObject(1)); 
				}
				*/
				u = new Utilisateur(rs.getInt(1),nom,prenom,login,pwd);
				
				rs.close();
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return u;
	}
	
	/**
	 * Efface un utilisateur
	 * @param id
	 */
	public static void delUtilisateur(int id){
		delEntry(id,"Utilisateur");
	}
	
	/**
	 * Charge tous les mouvement
	 * @param idBudget
	 * @return la liste des mouvements
	 */
	public static ArrayList<Mouvement> getMouvements(int idBudget){
		Mouvement m = null;
		ArrayList<Mouvement> mouvements = new ArrayList<Mouvement>();
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Mouvement WHERE idBudget = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, idBudget);
				
				rs3 = stmt.executeQuery();
				while(rs3.next()) {
					
					//FIXME: Simplifier getCategorie et getSousCatégorie (ne pas refaire de connections, taper dans les arrays...)
					m = new Mouvement(rs3.getInt(1), rs3.getString(2), rs3.getFloat(3), rs3.getInt(4), rs3.getInt(5), new GregorianCalendar(rs3.getDate(6).getYear(), rs3.getDate(6).getMonth(), rs3.getDate(6).getDay()), rs3.getInt(7), rs3.getInt(9), rs3.getInt(10), rs3.getInt(8));
					mouvements.add(m);
				}				
				
				rs3.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: +" + e.getMessage());
			}	
		}
		
		return mouvements;
	}
	
	/**
	 * Ajoute un mouvement
	 * @param libelle
	 * @param montant
	 * @param type
	 * @param date
	 * @param periodicite
	 * @param categorie
	 * @param sousCategorie
	 * @param idBudget
	 * @return le mouvement ajouté
	 */
	public static Mouvement addMouvement(String libelle, float montant, int type, int ESType, GregorianCalendar date, int periodicite, Categorie categorie, SousCategorie sousCategorie, int idBudget){
		Mouvement m = null;
		Boolean connected = connect();
		
		if(connected){
			String query = "INSERT INTO Mouvement VALUES (0,?,?,?,?,?,?,?,?)";
			
			try{
				CallableStatement stmt = con.prepareCall(query);
				stmt.setString(1, libelle);
				stmt.setFloat(2, montant);
				stmt.setInt(3, type);
				stmt.setInt(4, ESType);
				stmt.setDate(5, (java.sql.Date) date.getTime());
				stmt.setInt(6, categorie.id);
				stmt.setInt(7, sousCategorie.id);
				stmt.setInt(8,categorie.idBudget);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				
				/*DEBUG 
				if(rs.next()){
				    System.out.println("La première clef auto-générée vaut ");
				    System.out.println(rs.getObject(1)); 
				}
				*/
				m = new Mouvement(rs.getInt(1),libelle, montant,  type, ESType, date, periodicite, categorie.id, sousCategorie.id, idBudget);
				
				rs.close();
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return m;
	}
	/**
	 * Efface un mouvement
	 * @param id
	 */
	public static void delMouvement(int id){
		delEntry(id,"Mouvement");
	}
	
	/**
	 * Charge la liste des budgets d'un utilisateur
	 * @param idUtilisateur
	 * @return la liste des budgets de l'utilisateur
	 */
	public static ArrayList<BudgetListItem> getBudgetList(int idUtilisateur){
		ArrayList<BudgetListItem> budgetList = new ArrayList<BudgetListItem>();
		BudgetListItem budgetListItem = null;
		
		boolean connected = connect();
		
		if(connected) {
			String query = "SELECT t1.id, t1.libBudget FROM Budget t1, UtilisateurBudget t2 WHERE t1.id = t2.idBudget AND t2.idUtilisateur = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, idUtilisateur);
				rs = stmt.executeQuery();
				
				
				while(rs.next()) {
					budgetListItem = new BudgetListItem(rs.getInt(1), rs.getString(2));
					budgetList.add(budgetListItem);
				}
				
				rs.close();
				stmt.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return budgetList;
	}
	
	/**
	 * Charge un budget
	 * @param idBudget
	 * @return le budget demandé
	 */
	@SuppressWarnings("unused")
	public static Budget getBudget(int idBudget){
		Budget budget = null;
		ArrayList<Mouvement> mouvements = new ArrayList<Mouvement>();
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		
		boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Budget WHERE id = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, idBudget);
				rs = stmt.executeQuery();
				
				if(rs.next()) {
					budget = new Budget(rs.getInt(1),getCategories(idBudget),getMouvements(idBudget));
					
				}
				rs.close();
				stmt.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return budget;
	}
	
	/**
	 * Ajoute un budget
	 * @param nomBudget
	 * @return le budget ajouté
	 */
	public static Budget addBudget(String nomBudget){
		Budget budget = null;
		Boolean connected = connect();
		
		if(connected){
			String query = "INSERT INTO Budget VALUES (0,?)";
			
			try{
				CallableStatement stmt = con.prepareCall(query);
				stmt.setString(1, nomBudget);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				
				/*DEBUG 
				if(rs.next()){
				    System.out.println("La première clef auto-générée vaut ");
				    System.out.println(rs.getObject(1)); 
				}
				*/
				budget = new Budget(rs.getInt(1),null,null);
				
				rs.close();
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		
		return budget;
		
	}
	
	/**
	 * Efface un budget
	 * @param id
	 */
	public static void delBudget(int id){
		delEntry(id, "Budget");	
	}
	
	
	/**
	 * Méthode d'effacement d'élément dans la base de donnée
	 * @param id
	 * @param table
	 */
	private static void delEntry(int id, String table){
		String query = "DELETE FROM ? WHERE id=?;";
		
		Boolean connected = connect();
		
		if(connected){
			try{
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, table);
				stmt.setInt(2, id);
				stmt.executeUpdate();
				
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
	}
}

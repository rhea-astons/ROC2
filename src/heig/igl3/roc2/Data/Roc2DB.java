package heig.igl3.roc2.Data;

import heig.igl3.roc2.Business.Budget;
import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.Mouvement;
import heig.igl3.roc2.Business.SousCategorie;
import heig.igl3.roc2.Business.Utilisateur;
import heig.igl3.roc2.Business.Mouvement.TypeMouvement;

import java.sql.CallableStatement;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

/*import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.ResultSet;
*/

public final class Roc2DB {
	

	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_ADDRESS = "jdbc:mysql://do.rsdev.ch/";
	private static final String DB_NAME = "rocc";
	private static final String DB_USER = "rocc_app";
	private static final String DB_PASSWORD = "4nv9GuxJ";
	
	private static Driver driver;
	private static java.sql.Connection con;
	private static ResultSet rs;
	
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
	
	private static void disconnect() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Erreur à la déconnexion: "+ e.getMessage());
			}
		}
	}
	
	public static ArrayList<Categorie> getCategories(int idBudget) {
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		Categorie categorie;
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Categorie WHERE Categorie.idBudget = ?";
			
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
	
	public static Categorie getCategorie(int id){
		Categorie c = null;
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Categorie WHERE id = ?";
			
			try {
				CallableStatement stmt = con.prepareCall(query);
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
				
				/*DEBUG 
				if(rs.next()){
				    System.out.println("La première clef auto-générée vaut ");
				    System.out.println(rs.getObject(1)); 
				}
				*/
				categorie = new Categorie(rs.getInt(1), nomCategorie, idBudget,null);

				rs.close();
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return categorie;
	}
	
	public static void delCategorie(int id){
		delEntry(id,"Categorie");
	}
	
	public static ArrayList<SousCategorie> getSousCategories(int idCategorie) {
		ArrayList<SousCategorie> sousCategories = new ArrayList<SousCategorie>();
		SousCategorie sousCategorie;
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM SousCategorie WHERE idCategorie = ?";
			
			try {
				CallableStatement stmt = con.prepareCall(query);
				stmt.setInt(1, idCategorie);
				rs = stmt.executeQuery();
				
				while(rs.next()) {
					sousCategorie = new SousCategorie(rs.getInt(1), rs.getString(2), rs.getInt(3));
					sousCategories.add(sousCategorie);
				}
				rs.close();
				stmt.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return sousCategories;
	}
	
	public static SousCategorie getSousCategorie(int id){
		SousCategorie s = null;
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM SousCategorie WHERE id = ?";
			
			try {
				CallableStatement stmt = con.prepareCall(query);
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
	
	public static void delSousCategorie(int id){
		delEntry(id,"SousCategorie");
	}
	
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
	
	public static void delUtilisateur(int id){
		delEntry(id,"Utilisateur");
	}
	
	public static ArrayList<Mouvement> getMouvements(int idBudget){
		Mouvement m = null;
		ArrayList<Mouvement> mouvements = new ArrayList<Mouvement>();
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Mouvement WHERE idBudget = ?";
			
			try {
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, idBudget);
				
				rs = stmt.executeQuery();
				while(rs.next()) {
					//FIXME: Simplifier getCategorie et getSousCatégorie (ne pas refaire de connections, taper dans les arrays...)
					m = new Mouvement(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getDate(5), rs.getInt(6), getCategorie(rs.getInt(7)), getSousCategorie(rs.getInt(8)), rs.getInt(9));
					mouvements.add(m);
				}				
				
				rs.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: +" + e.getMessage());
			}	
		}
		
		return mouvements;
	}
	
	public static Mouvement addMouvement(String libelle, float montant, int type, Date date, int periodicite, Categorie categorie, SousCategorie sousCategorie, int idBudget){
		Mouvement m = null;
		Boolean connected = connect();
		
		if(connected){
			String query = "INSERT INTO Mouvement VALUES (0,?,?,?,?,?,?,?)";
			
			try{
				CallableStatement stmt = con.prepareCall(query);
				stmt.setString(1, libelle);
				stmt.setObject(2, type);
				stmt.setDate(3, (java.sql.Date) date);
				stmt.setInt(4, categorie.id);
				stmt.setInt(5, sousCategorie.id);
				stmt.setInt(6,categorie.idBudget);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				
				/*DEBUG 
				if(rs.next()){
				    System.out.println("La première clef auto-générée vaut ");
				    System.out.println(rs.getObject(1)); 
				}
				*/
				m = new Mouvement(rs.getInt(1),libelle, montant,  type, date, periodicite, categorie,sousCategorie,idBudget);
				
				rs.close();
				stmt.close();
				disconnect();
				
			}catch (SQLException e){
				System.out.println("Erreur lors de la requête: "+ e.getMessage());
			}
		}
		return m;
	}
	public static void delMouvement(int id){
		delEntry(id,"Mouvement");
	}
	
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
	
	public static void delBudget(int id){
		delEntry(id, "Budget");	
	}
	
	
	private static void delEntry(int id, String table){
		String query = "DELETE FROM ? WHERE id = ?";
		
		Boolean connected = connect();
		
		if(connected){
			try{
				CallableStatement stmt = con.prepareCall(query);
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

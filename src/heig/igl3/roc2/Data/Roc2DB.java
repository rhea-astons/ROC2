package heig.igl3.roc2.Data;

import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.SousCategorie;
import heig.igl3.roc2.Business.Utilisateur;

import java.sql.CallableStatement;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
				CallableStatement stmt = con.prepareCall(query);
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
	
	
	public static Categorie addCategorie(String nomCategorie, int idBudget){
		Categorie categorie = null;
		
		Boolean connected = connect();
		
		if(connected){
			String query = "INSERT INTO Categorie VALUES (0,?,?)";
			
			try{
				CallableStatement stmt = con.prepareCall(query);
				stmt.setString(1, nomCategorie);
				stmt.setInt(2, idBudget);
				stmt.executeUpdate();
				
				//Récupération de l'ID
				query = "SELECT * FROM Categorie WHERE libCategorie = ? AND idBudget = ?";
				stmt = con.prepareCall(query);
				stmt.setString(1, nomCategorie);
				stmt.setInt(2, idBudget);
				rs = stmt.executeQuery();
				categorie = new Categorie(rs.getInt(1), rs.getString(2), rs.getInt(3),null);
				
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
		String query = "DELETE FROM Categorie WHERE id = ?";
		
		try{
			CallableStatement stmt = con.prepareCall(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
			stmt.close();
			disconnect();
			
		}catch (SQLException e){
			System.out.println("Erreur lors de la requête: "+ e.getMessage());
		}
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
					sousCategorie = new SousCategorie(rs.getInt(1), rs.getString(2));
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
					u = new Utilisateur();
					u.id = rs.getInt(1);
					u.nom = rs.getString(2);
					u.prenom = rs.getString(3);
					u.login = rs.getString(4);
					u.pwd = rs.getString(5);
				}				
				
				rs.close();
				disconnect();
			} catch (SQLException e) {
				System.out.println("Erreur lors de la requête: +" + e.getMessage());
			}	
		}
		return u; 
	}
	
	//TODO: getMouvement
	//TODO: add + edit + remove pour chaque methode
	

}

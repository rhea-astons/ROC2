package heig.igl3.roc2.Data;

import heig.igl3.roc2.Business.Categorie;
import heig.igl3.roc2.Business.SousCategorie;

import java.sql.CallableStatement;
import java.sql.Driver;
import java.sql.DriverManager;
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
	
	public static ArrayList<Categorie> getCategories() {
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		Categorie categorie;
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM Categorie, SousCategorie";
			
			try {
				CallableStatement stmt = con.prepareCall(query);
				rs = stmt.executeQuery();
				
				while(rs.next()) {
					categorie = new Categorie(rs.getInt(1), rs.getString(2), getSousCategories(rs.getInt(1)));
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
	
	public static ArrayList<SousCategorie> getSousCategories(int idCategorie) {
		ArrayList<SousCategorie> sousCategories = new ArrayList<SousCategorie>();
		SousCategorie sousCategorie;
		
		Boolean connected = connect();
		
		if(connected) {
			String query = "SELECT * FROM SousCategorie WHERE idCategorie = ?";
			
			try {
				CallableStatement stmt = con.prepareCall(query);
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
	
	

}

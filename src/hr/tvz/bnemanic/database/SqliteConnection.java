package hr.tvz.bnemanic.database;

import java.sql.*;

public class SqliteConnection {
	
	public static final String GET_ALL_PICTURES = "select count(*) from picture";
	
	private Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:naps.sqlite");
			return connection;
		} catch(Exception e) {
			System.out.println("Došlo je do greške prilikom spajanja na bazu!");
			e.printStackTrace();
			return null;
		}
	}
	
	public int getPictures() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -100;
		
		try {
			connection = connect();
			ps = connection.prepareStatement(GET_ALL_PICTURES);
			rs = ps.executeQuery();	
			
			result = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("Došlo je do greške kod èitanja iz baze");
			e.printStackTrace();
		}
		
		return result;
	}

}

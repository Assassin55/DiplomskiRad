package hr.tvz.bnemanic.database;

import java.sql.*;

import hr.tvz.bnemanic.model.Picture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SqliteConnection {
	
	public static final String GET_ALL_PICTURES = "select p.name, p.description from picture p";
	
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
	
	public ObservableList<Picture> getPictures() throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		int result = -100;
		ObservableList<Picture> list = FXCollections.observableArrayList();
		
		connection = connect();
		ps = connection.prepareStatement(GET_ALL_PICTURES);
		rs = ps.executeQuery();	
			
		while(rs.next()) {
			String name = rs.getString(1);
			String description = rs.getString(2);
			Picture picture = new Picture(name, description);
			list.add(picture);
		}
			
		
		return list;
	}

}

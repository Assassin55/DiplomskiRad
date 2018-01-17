package hr.tvz.bnemanic.application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import hr.tvz.bnemanic.database.*;

public class MainController {
	
	@FXML
	TextField txtSearch;
	
	@FXML
	Label lblResult;
	
	public void search() {
//		TODO
		
		SqliteConnection sqliteConn = new SqliteConnection();
		int result = sqliteConn.getPictures();
		lblResult.setText(result + "");
	}

}

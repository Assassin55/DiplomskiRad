package hr.tvz.bnemanic.application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.util.ResourceBundle;

import hr.tvz.bnemanic.database.*;
import hr.tvz.bnemanic.model.Picture;

public class MainController implements Initializable {
	
	@FXML
	TextField txtSearch;
	
	@FXML
	Label lblResult;
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML
	TableView<Picture> levensteinTable;
	
	@FXML
	TableColumn<Picture, Number> levensteinIndexColumn;
	
	@FXML
	TableColumn<Picture, String> levensteinNameColumn;
	
	@FXML
	TableColumn<Picture, String> levensteinDescriptionColumn;
	
	@FXML
	TableColumn<Picture, Float> levensteinResultColumn;
	
	
	@FXML
	TableView<Picture> needlemanTable;
	
	@FXML
	TableColumn<Picture, Number> needlemanIndexColumn;
	
	@FXML
	TableColumn<Picture, String> needlemanNameColumn;
	
	@FXML
	TableColumn<Picture, String> needlemanDescriptionColumn;
	
	@FXML
	TableColumn<Picture, Float> needlemanResultColumn;
	
	
	@FXML
	TableView<Picture> jaroTable;
	
	@FXML
	TableColumn<Picture, Number> jaroIndexColumn;
	
	@FXML
	TableColumn<Picture, String> jaroNameColumn;
	
	@FXML
	TableColumn<Picture, String> jaroDescriptionColumn;
	
	@FXML
	TableColumn<Picture, Float> jaroResultColumn;
	
	
	@FXML
	TableView<Picture> cosineTable;
	
	@FXML
	TableColumn<Picture, Number> cosineIndexColumn;
	
	@FXML
	TableColumn<Picture, String> cosineNameColumn;
	
	@FXML
	TableColumn<Picture, String> cosineDescriptionColumn;
	
	@FXML
	TableColumn<Picture, Float> cosineResultColumn;
	
	
	@FXML
	TableView<Picture> jaccardTable;
	
	@FXML
	TableColumn<Picture, Number> jaccardIndexColumn;
	
	@FXML
	TableColumn<Picture, String> jaccardNameColumn;
	
	@FXML
	TableColumn<Picture, String> jaccardDescriptionColumn;
	
	@FXML
	TableColumn<Picture, Float> jaccardResultColumn;
	
	public void search() {		
		SqliteConnection sqliteConn = new SqliteConnection();
		ObservableList<Picture> pictures = sqliteConn.getPictures();
		lblResult.setText(pictures.size() + "");
		
		levensteinTable.setItems(pictures);
		needlemanTable.setItems(pictures);
		jaroTable.setItems(pictures);
		cosineTable.setItems(pictures);
		jaccardTable.setItems(pictures);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		levensteinIndexColumn.setSortable(false);
		levensteinIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(levensteinTable
				.getItems().indexOf(column.getValue()) + 1));
		levensteinNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
		levensteinDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
		levensteinResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
		
		needlemanIndexColumn.setSortable(false);
		needlemanIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(needlemanTable
				.getItems().indexOf(column.getValue()) + 1));
		needlemanNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
		needlemanDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
		needlemanResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
		
		jaroIndexColumn.setSortable(false);
		jaroIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(jaroTable
				.getItems().indexOf(column.getValue()) + 1));
		jaroNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
		jaroDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
		jaroResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
		
		cosineIndexColumn.setSortable(false);
		cosineIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(cosineTable
				.getItems().indexOf(column.getValue()) + 1));
		cosineNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
		cosineDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
		cosineResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
		
		jaccardIndexColumn.setSortable(false);
		jaccardIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(jaccardTable
				.getItems().indexOf(column.getValue()) + 1));
		jaccardNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
		jaccardDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
		jaccardResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
	}

}

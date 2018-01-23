package hr.tvz.bnemanic.application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hr.tvz.bnemanic.database.*;
import hr.tvz.bnemanic.model.Picture;
import hr.tvz.bnemanic.logic.Constants;
import hr.tvz.bnemanic.logic.FormatResults;

public class MainController implements Initializable {
	
	@FXML
	TextField txtSearch;
	
	@FXML
	Button excelBtn;
	
	@FXML
	TableView<Picture> levensteinTable;	
	
	@FXML
	TableView<Picture> needlemanTable;	
	
	@FXML
	TableView<Picture> jaroTable;	
	
	@FXML
	TableView<Picture> cosineTable;	
	
	@FXML
	TableView<Picture> jaccardTable;
	
	FormatResults fr = new FormatResults();
	ObservableList<Picture> pictures;
	
	String searchedText;
	
	public void search() {
		searchedText = txtSearch.getText().trim();
		
		if(!searchedText.equals("")) {
			
			if(pictures == null) {
				try {
					SqliteConnection sqliteConn = new SqliteConnection();
					pictures = sqliteConn.getPictures();
				} catch(SQLException e) {
					System.out.println("Došlo je do greške prilikom dohvaæanja podataka iz baze");
					e.printStackTrace();
					showError("Došlo je do greške prilikom dohvaæanja podataka iz baze!");
				}	
			}
			
			fr.calculateResults(pictures, searchedText);
			
			levensteinTable.setItems(fr.getLevenstheinList());
			needlemanTable.setItems(fr.getNeedlemanList());
			jaroTable.setItems(fr.getJaroList());
			cosineTable.setItems(fr.getCosineList());
			jaccardTable.setItems(fr.getJaccardList());
			
			levensteinTable.scrollTo(0);
			needlemanTable.scrollTo(0);
			jaroTable.scrollTo(0);
			cosineTable.scrollTo(0);
			jaccardTable.scrollTo(0);
			
			excelBtn.setDisable(false);
		} else {
			showError("Niste unjeli pojam za pretraživanje!");
		}
		
	}
	
	public void excelExport() {		
		try {
			fr.excelExport(searchedText);
			showInfo("Uspješno spremljeno",
					"Excel datoteka je uspješno spremljena na lokaciju " + Constants.XLS_PATH + 
					searchedText + Constants.XLS_EXTENSION);
			excelBtn.setDisable(true);
		} catch(IOException e) {
			System.out.println("Došlo je do greške kod kreiranja datoteke");
			e.printStackTrace();
			showError("Došlo je do greške prilikom spremanja datoteke!");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		createColumns(levensteinTable);
		createColumns(needlemanTable);
		createColumns(jaroTable);
		createColumns(cosineTable);
		createColumns(jaccardTable);
		
		excelBtn.setDisable(true);
	}
	
	@SuppressWarnings("unchecked")
	private void createColumns(TableView<Picture> table) {
		TableColumn<Picture, Number> index = new TableColumn<Picture, Number>("#");
		index.setSortable(false);
		index.setMinWidth(50);
		index.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(table
				.getItems().indexOf(column.getValue()) + 1));
		
		TableColumn<Picture, String> name = new TableColumn<Picture, String>("Ime");
		name.setMinWidth(200);
		name.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
		
		TableColumn<Picture, ImageView> image = new TableColumn<Picture, ImageView>("Slika");
		image.setMinWidth(200);
		image.setCellValueFactory(new PropertyValueFactory<Picture, ImageView>("image"));
		
		TableColumn<Picture, String> description = new TableColumn<Picture, String>("Opis");
		description.setMinWidth(200);
		description.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
		
		TableColumn<Picture, Float> result = new TableColumn<Picture, Float>("Rezultat");
		result.setMinWidth(200);
		result.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
		
		@SuppressWarnings("rawtypes")
		TableColumn accuracy = new TableColumn("Toènost");
		accuracy.setMinWidth(150);
		accuracy.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Picture, CheckBox>, ObservableValue<CheckBox>>() {

			@Override
			public ObservableValue<CheckBox> call(CellDataFeatures<Picture, CheckBox> arg0) {
				Picture pic = arg0.getValue();
                CheckBox checkBox = new CheckBox();
                checkBox.selectedProperty().setValue(pic.isAccuracy());

                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, 
                    		Boolean old_val, Boolean new_val) {
                        pic.setAccuracy(new_val);
                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);
			}	
		});
		
		table.getColumns().addAll(index, name, image, description, result, accuracy);

	}
	
	public void showInfo(String header, String info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacija");
		alert.setHeaderText(header);
		alert.setContentText(info);
		alert.show();
	}
	
	public void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Greška");
		alert.setHeaderText("Došlo je do greške!");
		alert.setContentText(message);
		alert.show();
	}

}

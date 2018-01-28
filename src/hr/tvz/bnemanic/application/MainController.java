package hr.tvz.bnemanic.application;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.util.ResourceBundle;

import hr.tvz.bnemanic.database.*;
import hr.tvz.bnemanic.model.Picture;
import hr.tvz.bnemanic.logic.Constants;
import hr.tvz.bnemanic.logic.FormatResults;

public class MainController implements Initializable {
	
	@FXML
	VBox vBox;
	
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
		
//		dohvat podataka iz baze i dohvat i sortiranje slika, nalazi se u 2 zasebne dretve, 
//		dok je update GUI-a u glavnoj dretvi
		
		if(!searchedText.equals("")) {
			vBox.getScene().cursorProperty().set(Cursor.WAIT);
			
			Task<Void> taskCalculate = new Task<Void>() {
				@Override
				protected Void call() throws Exception {					
					fr.calculateResults(pictures, searchedText);				
					return null;
				}	
			};
			taskCalculate.setOnSucceeded(e -> setGUI());
			
//			Prvo se pokre�e dretva za dohvat podataka iz baze
//			Ako ona uspje�no zavr�i, onda se sinkrono pokre�e dretva za dohvat slika i sort
			
			if(pictures == null) {
				Task<Void> taskDatabase = new Task<Void>() {
					@Override
					protected Void call() throws Exception {					
						SqliteConnection sqliteConn = new SqliteConnection();
						pictures = sqliteConn.getPictures();				
						return null;
					}	
				};
				taskDatabase.setOnSucceeded(e -> new Thread(taskCalculate).start());
				taskDatabase.setOnFailed(e -> databaseFailed(taskDatabase.getException()));
				
				new Thread(taskDatabase).start();	
			} else {			
				new Thread(taskCalculate).start();
			}
		} else {
			showError("Niste unjeli pojam za pretra�ivanje!");
		}	
	}
	
	public void excelExport() {
		vBox.getScene().cursorProperty().set(Cursor.WAIT);
		
		Task<Void> taskExcel = new Task<Void>() {
			@Override
			protected Void call() throws Exception {					
				fr.excelExport(searchedText);				
				return null;
			}	
		};
		taskExcel.setOnSucceeded(e -> finalizeExcel());
		taskExcel.setOnFailed(e -> excelFailed(taskExcel.getException()));
		
		new Thread(taskExcel).start();	
	}
	
	public void setGUI() {
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
		vBox.getScene().cursorProperty().set(Cursor.DEFAULT);
	}
	
	public void finalizeExcel() {
		showInfo("Uspje�no spremljeno",
				"Excel datoteka je uspje�no spremljena na lokaciju " + Constants.XLS_PATH + 
				searchedText + Constants.XLS_EXTENSION);
		excelBtn.setDisable(true);
		vBox.getScene().cursorProperty().set(Cursor.DEFAULT);
	}
	
	public void excelFailed(Throwable e) {
		System.out.println("Do�lo je do gre�ke kod kreiranja datoteke");
		e.printStackTrace();
		showError("Do�lo je do gre�ke prilikom spremanja datoteke!");
		vBox.getScene().cursorProperty().set(Cursor.DEFAULT);
	}
	
	public void databaseFailed(Throwable e) {
		System.out.println("Do�lo je do gre�ke prilikom dohva�anja podataka iz baze");
		e.printStackTrace();
		showError("Do�lo je do gre�ke prilikom dohva�anja podataka iz baze!");
		vBox.getScene().cursorProperty().set(Cursor.DEFAULT);
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
		TableColumn accuracy = new TableColumn("To�nost");
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
		alert.setTitle("Gre�ka");
		alert.setHeaderText("Do�lo je do gre�ke!");
		alert.setContentText(message);
		alert.show();
	}

}

package hr.tvz.bnemanic.application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.tvz.bnemanic.database.*;
import hr.tvz.bnemanic.logic.SimilarityMeasurement;
import hr.tvz.bnemanic.model.Picture;
import hr.tvz.bnemanic.logic.FormatResults;

public class MainController implements Initializable {
	
	@FXML
	TextField txtSearch;
	
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
	
	public void search() {		
		SqliteConnection sqliteConn = new SqliteConnection();
		ObservableList<Picture> pictures = sqliteConn.getPictures();
		
		fr.calculateResults(pictures, txtSearch.getText());
		
		levensteinTable.setItems(fr.getLevenstheinList());
		needlemanTable.setItems(fr.getNeedlemanList());
		jaroTable.setItems(fr.getJaroList());
		cosineTable.setItems(fr.getCosineList());
		jaccardTable.setItems(fr.getJaccardList());
	}
	
	public void excelExport() {	
		try {
			fr.excelExport(txtSearch.getText());
		} catch(IOException e) {
			System.out.println("Došlo je do greške kod kreiranja datoteke");
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		createColumns(levensteinTable);
		createColumns(needlemanTable);
		createColumns(jaroTable);
		createColumns(cosineTable);
		createColumns(jaccardTable);
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

}

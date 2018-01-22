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

import java.net.URL;
import java.util.ResourceBundle;

import hr.tvz.bnemanic.database.*;
import hr.tvz.bnemanic.logic.SimilarityMeasurement;
import hr.tvz.bnemanic.model.Picture;
import hr.tvz.bnemanic.logic.FormatResults;

public class MainController implements Initializable {
	
	@FXML
	TextField txtSearch;
	
	@FXML
	Label lblResult;
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML
	TableView<Picture> levensteinTable;
	
//	@FXML
//	TableColumn<Picture, Number> levensteinIndexColumn;
//	
//	@FXML
//	TableColumn<Picture, String> levensteinNameColumn;
//	
//	@FXML
//	TableColumn<Picture, ImageView> levensteinImageColumn;
//	
//	@FXML
//	TableColumn<Picture, String> levensteinDescriptionColumn;
//	
//	@FXML
//	TableColumn<Picture, Float> levensteinResultColumn;
	
	
	@FXML
	TableView<Picture> needlemanTable;
	
//	@FXML
//	TableColumn<Picture, Number> needlemanIndexColumn;
//	
//	@FXML
//	TableColumn<Picture, String> needlemanNameColumn;
//	
//	@FXML
//	TableColumn<Picture, String> needlemanDescriptionColumn;
//	
//	@FXML
//	TableColumn<Picture, Float> needlemanResultColumn;
	
	
	@FXML
	TableView<Picture> jaroTable;
	
//	@FXML
//	TableColumn<Picture, Number> jaroIndexColumn;
//	
//	@FXML
//	TableColumn<Picture, String> jaroNameColumn;
//	
//	@FXML
//	TableColumn<Picture, String> jaroDescriptionColumn;
//	
//	@FXML
//	TableColumn<Picture, Float> jaroResultColumn;
	
	
	@FXML
	TableView<Picture> cosineTable;
	
//	@FXML
//	TableColumn<Picture, Number> cosineIndexColumn;
//	
//	@FXML
//	TableColumn<Picture, String> cosineNameColumn;
//	
//	@FXML
//	TableColumn<Picture, String> cosineDescriptionColumn;
//	
//	@FXML
//	TableColumn<Picture, Float> cosineResultColumn;
	
	
	@FXML
	TableView<Picture> jaccardTable;
	
//	@FXML
//	TableColumn<Picture, Number> jaccardIndexColumn;
//	
//	@FXML
//	TableColumn<Picture, String> jaccardNameColumn;
//	
//	@FXML
//	TableColumn<Picture, String> jaccardDescriptionColumn;
//	
//	@FXML
//	TableColumn<Picture, Float> jaccardResultColumn;
	
	public void search() {		
		SqliteConnection sqliteConn = new SqliteConnection();
		ObservableList<Picture> pictures = sqliteConn.getPictures();
		lblResult.setText(pictures.size() + "");
		
		FormatResults fr = new FormatResults();
		fr.calculateResults(pictures, txtSearch.getText());
		
		levensteinTable.setItems(fr.getLevenstheinList());
		needlemanTable.setItems(fr.getNeedlemanList());
		jaroTable.setItems(fr.getJaroList());
		cosineTable.setItems(fr.getCosineList());
		jaccardTable.setItems(fr.getJaccardList());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		levensteinIndexColumn.setSortable(false);
//		levensteinIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(levensteinTable
//				.getItems().indexOf(column.getValue()) + 1));
//		levensteinNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
//		levensteinImageColumn.setCellValueFactory(new PropertyValueFactory<Picture, ImageView>("image"));
//		levensteinDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
//		levensteinResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
//		
//		needlemanIndexColumn.setSortable(false);
//		needlemanIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(needlemanTable
//				.getItems().indexOf(column.getValue()) + 1));
//		needlemanNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
//		needlemanDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
//		needlemanResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
//		
//		jaroIndexColumn.setSortable(false);
//		jaroIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(jaroTable
//				.getItems().indexOf(column.getValue()) + 1));
//		jaroNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
//		jaroDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
//		jaroResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
//		
//		cosineIndexColumn.setSortable(false);
//		cosineIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(cosineTable
//				.getItems().indexOf(column.getValue()) + 1));
//		cosineNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
//		cosineDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
//		cosineResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
//		
//		jaccardIndexColumn.setSortable(false);
//		jaccardIndexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(jaccardTable
//				.getItems().indexOf(column.getValue()) + 1));
//		jaccardNameColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("name"));
//		jaccardDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Picture, String>("description"));
//		jaccardResultColumn.setCellValueFactory(new PropertyValueFactory<Picture, Float>("result"));
		
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

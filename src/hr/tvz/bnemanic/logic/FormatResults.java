package hr.tvz.bnemanic.logic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.tvz.bnemanic.model.Picture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormatResults {  
	
	private ObservableList<Picture> levenstheinList;
	private ObservableList<Picture> needlemanList;
	private ObservableList<Picture> jaroList;
	private ObservableList<Picture> cosineList;
	private ObservableList<Picture> jaccardList;
	
	public FormatResults() {
		this.levenstheinList = FXCollections.observableArrayList();
		this.needlemanList = FXCollections.observableArrayList();
		this.jaroList = FXCollections.observableArrayList();
		this.cosineList = FXCollections.observableArrayList();
		this.jaccardList = FXCollections.observableArrayList();
	}
	
	public void calculateResults(ObservableList<Picture> pictures, String searchTerm) {
		SimilarityMeasurement sm = new SimilarityMeasurement();
		
/*		Prolazi po cijeloj kolekciji slika iz baze i za svaku raèuna i sprema 
		rezultat po levensthain algoritmu */
		for(Picture pic : pictures) {
			float result = sm.levenstein(pic.getDescription(), searchTerm);
			pic.setResult(result);
		}
//		sortira listu i uzima njenih prvih 50 èlanova za prikaz u tablici
		levenstheinList = listCreation(pictures, levenstheinList);
		
		for(Picture pic : pictures) {
			float result = sm.needleman(pic.getDescription(), searchTerm);
			pic.setResult(result);
		}
		needlemanList = listCreation(pictures, needlemanList);
		
		for(Picture pic : pictures) {
			float result = sm.jaroWinkler(pic.getDescription(), searchTerm);
			pic.setResult(result);
		}
		jaroList = listCreation(pictures, jaroList);
		
		for(Picture pic : pictures) {
			float result = sm.cosine(pic.getDescription(), searchTerm);
			pic.setResult(result);
		}
		 cosineList = listCreation(pictures, cosineList);
		
		for(Picture pic : pictures) {
			float result = sm.jaccard(pic.getDescription(), searchTerm);
			pic.setResult(result);
		}
		jaccardList = listCreation(pictures, jaccardList);	
	}
	
	public ObservableList<Picture> listCreation(ObservableList<Picture> pictures, 
			ObservableList<Picture> newList) {
		Collections.sort(pictures, new PictureNameSorter());
		Collections.sort(pictures, new PictureSorter());
		if(newList.size() > 0) {
			newList.clear();
		}
		
		for(int i = 0; i < Constants.SELECTOR; i++) {
			Picture picture = new Picture(pictures.get(i));
			picture.showImage();
			newList.add(picture);
		}
		
		return newList;
	}
	
	public void excelExport(String txtSearch) throws IOException {
		HSSFWorkbook woorkbook = new HSSFWorkbook();
		
		sheetBuilder(woorkbook, this.levenstheinList, "Levensthein", txtSearch);
		sheetBuilder(woorkbook, this.needlemanList, "Needleman-Wunsch", txtSearch);
		sheetBuilder(woorkbook, this.jaroList, "Jaro-Winkler", txtSearch);
		sheetBuilder(woorkbook, this.cosineList, "Cosine", txtSearch);
		sheetBuilder(woorkbook, this.jaccardList, "Jaccard", txtSearch);
		
		FileOutputStream out = new FileOutputStream(Constants.XLS_PATH + txtSearch + Constants.XLS_EXTENSION);
		woorkbook.write(out);
		woorkbook.close();
		out.close();
	}
	
	private void sheetBuilder(HSSFWorkbook woorkbook, ObservableList<Picture> list, 
			String sheetName, String txtSearch) {
		
		HSSFSheet sheet = woorkbook.createSheet(sheetName);
		HSSFRow row = sheet.createRow(Constants.SEARCH_ROW_INDEX);
		HSSFCell cell = row.createCell(Constants.SEARCH_COLUMN_INDEX);
		cell.setCellValue("Traženi pojam: " + txtSearch);
		
		HSSFRow row2 = sheet.createRow(Constants.TABLE_ROW_INDEX);
		HSSFCell cell2 = row2.createCell(Constants.NAME_COLUMN_INDEX);
		cell2.setCellValue("Ime");
		HSSFCell cell3 = row2.createCell(Constants.ACCURACY_COLUMN_INDEX);
		cell3.setCellValue("Toènost");
		
		HSSFCellStyle style = woorkbook.createCellStyle();
		HSSFFont font = woorkbook.createFont();
		font.setBold(true);
		style.setFont(font);
		cell2.setCellStyle(style);
		cell3.setCellStyle(style);
		
		int count = 0;
				
		for(int i = 0; i < Constants.SELECTOR; i++) {
			Picture picture = list.get(i);
			
			HSSFRow newRow = sheet.createRow(i + 3);
			HSSFCell newCell1 = newRow.createCell(Constants.NAME_COLUMN_INDEX);
			newCell1.setCellValue(picture.getName());
			HSSFCell newCell2 = newRow.createCell(Constants.ACCURACY_COLUMN_INDEX);
			newCell2.setCellValue(picture.isAccuracy());
			
			if(picture.isAccuracy()) {
				count++;
			}
		}
		
//		Poèinjemo u prvom redu tablice + broj redaka tablice + 1 za prvi red ispod
		HSSFRow sumRow = sheet.createRow(Constants.TABLE_ROW_INDEX + Constants.SELECTOR + 1);
		HSSFCell sumCell1 = sumRow.createCell(Constants.NAME_COLUMN_INDEX);
		sumCell1.setCellValue("Rezultat");
		sumCell1.setCellStyle(style);
		HSSFCell sumCell2 = sumRow.createCell(Constants.ACCURACY_COLUMN_INDEX);
		sumCell2.setCellValue(count + " / " + Constants.SELECTOR);
		
		sheet.autoSizeColumn(Constants.SEARCH_COLUMN_INDEX);
		sheet.autoSizeColumn(Constants.NAME_COLUMN_INDEX);
		sheet.autoSizeColumn(Constants.ACCURACY_COLUMN_INDEX);
	}

	public ObservableList<Picture> getLevenstheinList() {
		return levenstheinList;
	}

	public ObservableList<Picture> getNeedlemanList() {
		return needlemanList;
	}

	public ObservableList<Picture> getJaroList() {
		return jaroList;
	}

	public ObservableList<Picture> getCosineList() {
		return cosineList;
	}

	public ObservableList<Picture> getJaccardList() {
		return jaccardList;
	}
}

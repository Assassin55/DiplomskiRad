package hr.tvz.bnemanic.logic;

import java.io.File;
import java.util.Collections;

import hr.tvz.bnemanic.model.Picture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class FormatResults {
	
	public final static int SELECTOR = 50;  
	
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
		Collections.sort(pictures, new PictureSorter());
		
		for(int i = 0; i < SELECTOR; i++) {
			Picture picture = new Picture(pictures.get(i));
			picture.showImage();
			newList.add(picture);
		}
		
		return newList;
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

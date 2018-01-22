package hr.tvz.bnemanic.model;

import java.io.File;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Picture {
	public static final String PATH = "file:C:\\naps\\NAPS_L\\";
	public static final String EXTENSION = ".jpg";
	
	private String name;
	private String description;
	private Float result;
	private Boolean accuracy;
	private ImageView image;
	
	public Picture(String name, String description) {
		this.name = name;
		this.description = description;
		this.image = new ImageView();
	}
	
	public Picture(Picture picture) {
		this.name = picture.getName();
		this.description = picture.getDescription();
		this.result = picture.getResult();
		this.accuracy = picture.isAccuracy();
		this.image = new ImageView();
	}
	
	public void showImage() {
		
//		File file = new File(PATH + this.name + EXTENSION);
		Image pic = new Image(PATH + this.name + EXTENSION, 180, 180, true, false);
		image.setImage(pic);
	}
	
	public void setImageProperties() {
		this.image.setPreserveRatio(true);
		this.image.setFitWidth(180);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getResult() {
		return result;
	}

	public void setResult(Float result) {
		this.result = result;
	}

	public Boolean isAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Boolean accuracy) {
		this.accuracy = accuracy;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image.setImage(image);
	}
	
}

package hr.tvz.bnemanic.model;

import java.io.File;

import hr.tvz.bnemanic.logic.Constants;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Picture {
	
	private String name;
	private String description;
	private Float result;
	private Boolean accuracy;
	private ImageView image;
	
	public Picture(String name, String description) {
		this.name = name;
		this.description = description;
		this.accuracy = false;
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
		Image pic = new Image(Constants.IMG_PATH + this.name + Constants.IMG_EXTENSION, 180, 180, true, false);
		image.setImage(pic);
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

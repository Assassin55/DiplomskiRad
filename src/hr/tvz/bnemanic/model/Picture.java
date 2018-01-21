package hr.tvz.bnemanic.model;

public class Picture {
	
	private String name;
	private String description;
	private Float result;
	private boolean accuracy;
	
	public Picture(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Picture(Picture picture) {
		this.name = picture.getName();
		this.description = picture.getDescription();
		this.result = picture.getResult();
		this.accuracy = picture.isAccuracy();
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

	public boolean isAccuracy() {
		return accuracy;
	}

	public void setAccuracy(boolean accuracy) {
		this.accuracy = accuracy;
	}
}

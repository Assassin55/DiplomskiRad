package hr.tvz.bnemanic.logic;

import java.util.Comparator;

import hr.tvz.bnemanic.model.Picture;

public class PictureNameSorter implements Comparator<Picture> {

	@Override
	public int compare(Picture pic1, Picture pic2) {
		if(pic1.getName().compareTo(pic2.getName()) > 0) {
			return 1;
		} else if (pic1.getName().compareTo(pic2.getName()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}

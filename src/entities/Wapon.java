package entities;

import java.awt.image.BufferedImage;

import util.Id;

public class Wapon extends Entity{

	public Wapon(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		id = Id.ID_WAPON;
		
	}

}

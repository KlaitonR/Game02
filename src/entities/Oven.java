package entities;

import java.awt.image.BufferedImage;
import util.Id;

public class Oven extends Entity{
	
	boolean ligado;
	
	public Oven(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		id = Id.ID_OVEN;
		clear = false;
	}

}

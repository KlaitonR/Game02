package entities;

import java.awt.image.BufferedImage;

import util.Id;

public class Axe extends Entity{

	public Axe(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		id = Id.ID_AXE;
		clear = false;
	}

}

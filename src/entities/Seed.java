package entities;

import java.awt.image.BufferedImage;

public class Seed extends Entity{

	public Seed(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		pack = true;
		qtPack = 63;
		}

}

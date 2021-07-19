package entities;

import java.awt.image.BufferedImage;

public class Firewood extends Entity{
	
	public Firewood(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 1;
		pack = true;
		qtPack = 63;
		
	}

}

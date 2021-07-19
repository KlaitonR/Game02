package entities;

import java.awt.image.BufferedImage;

import util.Id;

public class LifePack extends Entity{

	public LifePack(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		pack = true;
		qtPack = 4;
		id = Id.ID_LIFEPACK;
		
	}

}

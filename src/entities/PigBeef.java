package entities;

import java.awt.image.BufferedImage;

import util.Id;

public class PigBeef extends Beef{

	public PigBeef(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		pack = true;
		id = Id.ID_PIG_BEEF;
		qtPack = 63;
		
	}

}

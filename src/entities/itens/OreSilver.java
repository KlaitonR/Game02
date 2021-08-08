package entities.itens;

import java.awt.image.BufferedImage;
import util.Id;

public class OreSilver extends Ore{
	
	public OreSilver(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		mwidth = width;
		mheigth = height;
		maskx = + 0;
		masky = + 0;
		
		pack = true;
		qtPack = 63;
		id = Id.ID_ORE_SILVER;

	}

}

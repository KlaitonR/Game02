package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Id;

public class ProcessedWood extends Entity {
	
	public ProcessedWood(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		id = Id.ID_PROCESSED_WOOD;
		pack = true;
		qtPack = 63;
	}

}

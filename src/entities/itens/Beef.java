package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;

public class Beef extends Entity{
	
	public Beef(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 1;
		pack = true;
		qtPack = 63;
		
	}

}

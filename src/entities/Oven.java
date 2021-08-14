package entities;

import java.awt.image.BufferedImage;

import util.Id;
import util.Mapa;
import util.Regiao;

public class Oven extends Entity{
	
	public Oven(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	
		mapa.addAll(Mapa.addAll());
		regiao.addAll(Regiao.allRegioes);
		id = Id.ID_OVEN;
	}

}

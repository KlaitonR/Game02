package world;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class FloorCal extends Tile{

	public FloorCal(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		mapa.addAll(Mapa.addAll());
		regiao.addAll(Regiao.addAll());
	}

	
}

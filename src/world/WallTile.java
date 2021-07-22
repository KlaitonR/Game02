package world;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class WallTile extends Tile{

	public WallTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}

}

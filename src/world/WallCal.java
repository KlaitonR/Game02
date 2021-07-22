package world;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class WallCal extends WallTile{

	public WallCal(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		mapa.add(Mapa.MAPA_CALABOUÇO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
	}

}

package world;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class FloorCalSolid extends WallTile{

	public FloorCalSolid(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		mapa.add(Mapa.MAPA_CALABOUÇO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
	}

}

package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Id;
import util.Mapa;
import util.Regiao;

public class GoldBar extends Entity {
	
	public GoldBar(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		id = Id.ID_BAR_GOLD;
		pack = true;
		qtPack = 63;
		mapa.add(Mapa.MAPA_CALABOUÇO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
	}

}

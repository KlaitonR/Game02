package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Id;
import util.Mapa;
import util.Regiao;

public class SilverBar extends Entity{
	
	public SilverBar(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		id = Id.ID_BAR_SILVER;
		pack = true;
		qtPack = 63;
		mapa.add(Mapa.MAPA_CALABOUCO);
		regiao.add(Regiao.REGIAO_CALABOU�O);
	}

}

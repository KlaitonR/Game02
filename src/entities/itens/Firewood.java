package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Mapa;
import util.Regiao;

public class Firewood extends Entity{
	
	public Firewood(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 1;
		pack = true;
		qtPack = 63;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}

}

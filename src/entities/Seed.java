package entities;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class Seed extends Entity{

	public Seed(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		pack = true;
		qtPack = 63;
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
		}

}

package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Mapa;
import util.Regiao;

public class Bullet extends Entity{

	public Bullet(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}

}

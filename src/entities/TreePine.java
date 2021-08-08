package entities;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class TreePine extends Tree{
	
	public TreePine(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
}

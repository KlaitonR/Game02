package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Id;
import util.Mapa;
import util.Regiao;

public class Hoe extends Entity{

	public Hoe(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		id = Id.ID_HOE;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}

}

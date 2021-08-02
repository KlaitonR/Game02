package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Id;
import util.Mapa;
import util.Regiao;

public class Potion extends Entity{

	public Potion(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		pack = true;
		qtPack = 63;
		id = Id.ID_POTION;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
		}
	
}

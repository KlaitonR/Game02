package entities;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class DoorHouse extends Entity{
	
	public DoorHouse(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		mapa.add(Mapa.MAPA_ROOM_HOUSE_01);
		regiao.add(Regiao.REGIAO_ROOM_HOUSE_01);
		
		maskx = 0;
		masky = 0;
		mwidth = 20;
		mheigth = 10;
	}

}

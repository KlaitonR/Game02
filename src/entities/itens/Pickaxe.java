package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import util.Id;
import util.Mapa;
import util.Regiao;

public class Pickaxe extends Entity{
	
	public Pickaxe (double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheigth = height;
		
		depth = 1;
		
		id = Id.ID_PICKAXE;
		mapa.add(Mapa.MAPA_CALABOUÇO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
		clear = false;
	}
}

package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import main.Game;
import util.Mapa;
import util.Regiao;

public class Firewood extends Entity{
	
	public static BufferedImage FIREWOOD_CARVALHO_EN = Game.spritesheet.getSprite(48, 16, 16, 16);
	public static BufferedImage FIREWOOD_PINHEIRO_EN = Game.spritesheet.getSprite(64, 16, 16, 16);
	public static BufferedImage FIREWOOD_SALGUEIRO_EN = Game.spritesheet.getSprite(80, 16, 16, 16);
	
	public Firewood(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		pack = true;
		qtPack = 63;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}

}

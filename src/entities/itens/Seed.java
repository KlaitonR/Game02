package entities.itens;

import java.awt.image.BufferedImage;

import entities.Entity;
import main.Game;
import util.Mapa;
import util.Regiao;

public class Seed extends Entity{
	
	static public BufferedImage SEED_CARVALHO_EN = Game.spritesheet.getSprite(48, 32, 16, 16);
	static public BufferedImage SEED_PINHEIRO_EN = Game.spritesheet.getSprite(64, 32, 16, 16);
	static public BufferedImage SEED_SALGUEIRO_EN = Game.spritesheet.getSprite(80, 32, 16, 16);

	public Seed(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		pack = true;
		qtPack = 63;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
}

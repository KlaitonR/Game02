package entities.spots;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.Entity;
import main.Game;
import util.Mapa;
import util.Regiao;

public class MiningSite extends Entity{

	public static int miningTime, maxMiningTime = Game.rand.nextInt(600) + 400;
	
	static public BufferedImage MINING_SITE_SILVER_EN = Game.spritesheet.getSprite(48, 48, 16, 16);
	static public BufferedImage MINING_SITE_GOLD_EN = Game.spritesheet.getSprite(64, 48, 16, 16);
	static public BufferedImage MINING_SITE_DIAMOND_EN = Game.spritesheet.getSprite(80, 48, 16, 16);
	static public BufferedImage MINING_SITE_COPPER_EN = Game.spritesheet.getSprite(48, 64, 16, 16);
	static public BufferedImage MINING_SITE_COAL_EN = Game.spritesheet.getSprite(64, 64, 16, 16);
	static public BufferedImage MINING_SITE_EMERALD_EN = Game.spritesheet.getSprite(80, 64, 16, 16);
	
	public MiningSite(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		mapa.add(Mapa.MAPA_CALABOUÇO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
	}
	
	public void render(Graphics g) {
		super.render(g);
//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheigth);
	}
	
}

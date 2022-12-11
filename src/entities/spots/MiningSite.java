package entities.spots;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.Entity;
import main.Game;
import util.Mapa;
import util.Regiao;

public class MiningSite extends Entity{

	public static int miningTime, maxMiningTime = Game.rand.nextInt(50) + 50;
	
	static public BufferedImage MINING_SITE_SILVER_EN = Game.spritesheet.getSprite(48, 48, 16, 16);
	static public BufferedImage MINING_SITE_GOLD_EN = Game.spritesheet.getSprite(64, 48, 16, 16);
	static public BufferedImage MINING_SITE_DIAMOND_EN = Game.spritesheet.getSprite(80, 48, 16, 16);
	static public BufferedImage MINING_SITE_COPPER_EN = Game.spritesheet.getSprite(48, 64, 16, 16);
	static public BufferedImage MINING_SITE_COAL_EN = Game.spritesheet.getSprite(64, 64, 16, 16);
	static public BufferedImage MINING_SITE_EMERALD_EN = Game.spritesheet.getSprite(80, 64, 16, 16);
	static public BufferedImage MINING_SITE_SULFOR_EN = Game.spritesheet.getSprite(96, 48, 16, 16);
	static public BufferedImage MINING_SITE_POTASSIUM_NITARTE_EN = Game.spritesheet.getSprite(96, 64, 16, 16);
	static public BufferedImage MINING_SITE_PHOSPHOR_EN = Game.spritesheet.getSprite(112, 48, 16, 16);
	
	public MiningSite(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		mapa.add(Mapa.MAPA_CALABOUCO);
		regiao.add(Regiao.REGIAO_CALABOU�O);
	}
	
	public void render(Graphics g) {
		super.render(g);
//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheigth);
	}
	
}

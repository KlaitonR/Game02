package entities.itens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import entities.Entity;
import main.Game;
import util.Mapa;
import util.Regiao;

public class Ore extends Entity{
	
	static public BufferedImage ORE_SILVER_EN = Game.spritesheet.getSprite(32, 64, 16, 16);
	static public BufferedImage ORE_GOLD_EN = Game.spritesheet.getSprite(32, 80, 16, 16);
	static public BufferedImage DIAMOND_EN = Game.spritesheet.getSprite(32, 96, 16, 16);
	static public BufferedImage ORE_COPPER_EN = Game.spritesheet.getSprite(32, 112, 16, 16);
	static public BufferedImage ORE_COAL_EN = Game.spritesheet.getSprite(32, 128, 16, 16);
	static public BufferedImage EMERALD_EN = Game.spritesheet.getSprite(32, 144, 16, 16);
	static public BufferedImage STONE_EN = Game.spritesheet.getSprite(32, 48, 16, 16);
	static public BufferedImage SULFOR_EN = Game.spritesheet.getSprite(128, 288, 16, 16);
	static public BufferedImage POTASSIUM_NITRATE_EN = Game.spritesheet.getSprite(80, 288, 16, 16);
	static public BufferedImage PHOSPHOR_EN = Game.spritesheet.getSprite(112, 64, 16, 16);
	
	public Ore(double x, double y, int width, int height, BufferedImage sprite) {
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

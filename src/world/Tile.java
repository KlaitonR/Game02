package world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Entity;
import main.Game;
import util.Mapa;
import util.Regiao;

public class Tile {
	
	public Entity en;
	public ArrayList<Mapa> mapa = new ArrayList<>();
	public ArrayList<Regiao> regiao = new ArrayList<>();
	
	static public BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	static public BufferedImage TILE_FLOOR2 = Game.spritesheet.getSprite(0, 176, 16, 16);
	static public BufferedImage TILE_FLOOR3 = Game.spritesheet.getSprite(16, 176, 16, 16);
	static public BufferedImage TILE_FLOOR4 = Game.spritesheet.getSprite(32, 176, 16, 16);
	static public BufferedImage TILE_FLOOR5 = Game.spritesheet.getSprite(48, 176, 16, 16);
	static public BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	static public BufferedImage TILE_EARTH = Game.spritesheet.getSprite(0, 16, 16, 16);
	static public BufferedImage TILE_FLOOR_CAL_SOLID = Game.spritesheet.getSprite(16, 48, 16, 16);
	static public BufferedImage TILE_WALL_CAL = Game.spritesheet.getSprite(0, 304, 16, 16);
	
	public boolean show;
	public boolean open;
	
	protected BufferedImage sprite;
	protected int x,y,z;
	public int psTiles;
	public int maskx, masky, mwidth, mheigth;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public static boolean isColidding(Tile e1, Entity player) {
		
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheigth);
		Rectangle e2Mask = new Rectangle(player.getX() + player.maskx, player.getY() + player.masky, player.mwidth, player.mheigth);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
//		if(show) 
		
			g.drawImage(sprite, x - Camera.x ,y - Camera.y ,null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}

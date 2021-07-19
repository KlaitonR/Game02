package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import main.Game;

public class Tile {
	
	public Entity en;
	
	static public BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	static public BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	static public BufferedImage TILE_EARTH = Game.spritesheet.getSprite(0, 16, 16, 16);
	static public BufferedImage TILE_WATER;
	
	public boolean show;
	public boolean open;
	
	protected BufferedImage sprite;
	protected int x,y,z;
	public int psTiles;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
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

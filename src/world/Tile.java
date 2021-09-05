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
	static public BufferedImage TILE_WALL_TREE1 = Game.spritesheet.getSprite(32, 304, 16, 16);
	static public BufferedImage TILE_WALL_TREE2 = Game.spritesheet.getSprite(48, 304, 16, 16);
	static public BufferedImage TILE_WALL_CERCA_HORIZONTAL = Game.spritesheet.getSprite(64, 304, 16, 16);
	static public BufferedImage TILE_WALL_CERCA_VERTICAL = Game.spritesheet.getSprite(80, 304, 16, 16);
	static public BufferedImage TILE_WALL_CERCA_DIREITA = Game.spritesheet.getSprite(96, 304, 16, 16);
	static public BufferedImage TILE_WALL_CERCA_ESQUERDA = Game.spritesheet.getSprite(112, 304, 16, 16);
	static public BufferedImage TILE_EARTH = Game.spritesheet.getSprite(0, 16, 16, 16);
	static public BufferedImage TILE_FLOOR_CAL_SOLID = Game.spritesheet.getSprite(16, 48, 16, 16);
	static public BufferedImage TILE_WALL_CAL = Game.spritesheet.getSprite(0, 304, 16, 16);
	static public BufferedImage TILE_FLOOR_ROOM_HOUSE_01 = Game.spritesheet.getSprite(80, 80, 16, 16);
	//Floor room house 01
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_01 = Game.spritesheet.getSprite(48, 80, 16, 16);
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_02 = Game.spritesheet.getSprite(48, 96, 16, 16);
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_03 = Game.spritesheet.getSprite(48, 112, 16, 16);
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_04 = Game.spritesheet.getSprite(48, 128, 16, 16);
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_05 = Game.spritesheet.getSprite(64, 80, 16, 16);
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_06 = Game.spritesheet.getSprite(64, 96, 16, 16);
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_07 = Game.spritesheet.getSprite(64, 112, 16, 16);
	static public BufferedImage TILE_WALL_ROOM_HOUSE_01_08 = Game.spritesheet.getSprite(64, 128, 16, 16);
	
	
	public boolean right, left, up, down;
	public int xTile, yTile;
	
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

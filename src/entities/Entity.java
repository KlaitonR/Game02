package entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entities.mobs.Mob;
import main.Game;
import util.Id;
import util.Mapa;
import util.Regiao;
import world.Camera;
import world.Node;
import world.Tile;
import world.Vector2i;

public class Entity {
	
	//último id = 13
	
	public double x;
	public double y;
	protected int z;
	protected int width;
	protected int height;
	
	public Id id;
	public ArrayList<Mapa> mapa = new ArrayList<>();
	public ArrayList<Regiao> regiao = new ArrayList<>();
	public boolean clear;
	public int timeClear;
	public int maxTimeClear = 3600*4;
	
	public String tipo;
	public boolean pack;
	public int qtPack;
	public ArrayList<Entity> itensPack = new ArrayList<>();
	
	public int psTiles, xTile, yTile;
	public boolean show;
	
	public int maskx, masky, mwidth, mheigth;
	public int maskx2, masky2, mwidth2, mheigth2;
	
	private BufferedImage sprite;
	public static BufferedImage LIFE_PACK_EN = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(0, 64, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(0, 32, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	static public BufferedImage STUMP_EN = Game.spritesheet.getSprite(32, 0, 16, 16);
	public static BufferedImage RAIZ_EN = Game.spritesheet.getSprite(32, 16, 16, 16);
	public static BufferedImage AXE_EN = Game.spritesheet.getSprite(0, 96, 16, 16);
	public static BufferedImage LIGHTER_EN = Game.spritesheet.getSprite(0, 128, 16, 16);
	static public BufferedImage DOOR_EN = Game.spritesheet.getSprite(16, 16, 16, 16);
	static public BufferedImage DOOR_LEFT_HOUSE_EN = Game.spritesheet.getSprite(16, 80, 16, 16); 
	static public BufferedImage PLAT_EN = Game.spritesheet.getSprite(16, 176, 16, 16);
	static public BufferedImage FISHING_ROD_EN = Game.spritesheet.getSprite(0, 208, 16, 16);
	static public BufferedImage FISH_EN = Game.spritesheet.getSprite(16, 192, 16, 16);
	static public BufferedImage HOE_EN = Game.spritesheet.getSprite(16, 96, 16, 16);
	static public BufferedImage GROUND_EN = Game.spritesheet.getSprite(0, 160, 16, 16);
	static public BufferedImage GROUND_F1_EN = Game.spritesheet.getSprite(16, 208, 16, 16);
	static public BufferedImage GROUND_F2_EN = Game.spritesheet.getSprite(16, 160, 16, 16);
	static public BufferedImage GROUND_F3_EN = Game.spritesheet.getSprite(0, 192, 16, 16);
	public static BufferedImage FISHING_EN;
	static public BufferedImage PIG_BEEF_EN = Game.spritesheet.getSprite(32, 192, 16, 16);
	static public BufferedImage CREATION_TABLE_EN = Game.spritesheet.getSprite(16, 304, 16, 16);
	static public BufferedImage POTION_EN = Game.spritesheet.getSprite(0, 272, 16, 16);
	static public BufferedImage ESCADA_EN = Game.spritesheet.getSprite(0, 240, 16, 16);
	static public BufferedImage PICARETA_EN = Game.spritesheet.getSprite(0, 80, 16, 16);
	static public BufferedImage FORNO_EN = Game.spritesheet.getSprite(16, 288, 16, 16);
	
	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	protected List<Node> path;
	
	public int depth;
	
	public Entity () {}
	
	public Entity (double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheigth = height;
		
		depth = 1;
		
	}
	
	public void setMask(int maskx, int masky, int maskw, int maskh) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = maskw;
		this.mheigth = maskh;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>(){
		
		@Override
		public int compare(Entity n0, Entity n1) {
				if(n1.depth < n0.depth)
					return +1;
				if(n1.depth > n0.depth)
					return -1;
				return 0;
		}
		
	};
	
	public static boolean isColidding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheigth);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheigth);
		
		return e1Mask.intersects(e2Mask);
		
//		if(e1Mask.intersects(e2Mask) && e1.z == e2.z) {
//			return true;
//		}else {
//			return false;	
//		}
		
	}
	
	public static boolean isColiddingTile(Entity e, Tile t) {
		
		Rectangle eMask = new Rectangle(e.getX() + e.getMaskx(), e.getY() + e.getMasky(), e.getMaskw(), e.getMaskh());
		Rectangle tMask = new Rectangle(t.getX(), t.getY(), Game.world.TILE_SIZE, Game.world.TILE_SIZE);
		
		return eMask.intersects(tMask);
		
	}
	
	public boolean isColiddingEnemy(int xNext, int yNext) {
		
		Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky, mwidth, mheigth);
		
		for(int i =0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e==this && (e instanceof Enemy))
				continue;
			
			Rectangle targetEnemy= new Rectangle(e.getX() + maskx,e.getY() + masky, mwidth, mheigth);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isColiddingMob(int xNext, int yNext) {
		
		Rectangle mobCurrent = new Rectangle(xNext + maskx, yNext + masky, mwidth, mheigth);
		
		for(int i =0; i < Game.mobs.size(); i++) {
			Mob e = Game.mobs.get(i);
			if(e==this && (e instanceof Mob))
				continue;
			
			Rectangle targetMob = new Rectangle(e.getX() + maskx,e.getY() + masky, mwidth, mheigth);
			if(mobCurrent.intersects(targetMob)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isColiddingEntity(int xNext, int yNext) {
		
		Rectangle entityCurrent = new Rectangle(xNext + maskx, yNext + masky, mwidth, mheigth);
		
		for(int i =0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e==this && (e instanceof Entity))
				continue;
			
			Rectangle targetEntity = new Rectangle(e.getX() + maskx,e.getY() + masky, mwidth, mheigth);
			if(entityCurrent.intersects(targetEntity)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static double calculateDistance(int x1, int y1, int x2, int y2) {
		
		return Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x * 16) {
					x--;
				}
				
				if(y < target.y * 16) {
					y++;
				}else if(y > target.y * 16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
				
			}
		}
	}
	
	public void tick() {
		
		if(clear) {
			timeClear++;
		}
		
		if(timeClear > maxTimeClear) {
			Game.world.tiles[this.psTiles].en = null;
			Game.entities.remove(this);
		}
	}
	
	public void render(Graphics g) {
		
//			if(show)
		g.drawImage(sprite ,this.getX() - Camera.x, this.getY() - Camera.y , null);
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
		
	}

	public int getX() {
		return (int)this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMaskx() {
		return maskx;
	}

	public void setMaskx(int maskx) {
		this.maskx = maskx;
	}

	public int getMasky() {
		return masky;
	}

	public void setMasky(int masky) {
		this.masky = masky;
	}

	public int getMaskw() {
		return mwidth;
	}

	public void setMaskw(int maskw) {
		this.mwidth = maskw;
	}

	public int getMaskh() {
		return mheigth;
	}

	public void setMaskh(int maskh) {
		this.mheigth = maskh;
	}
	
}
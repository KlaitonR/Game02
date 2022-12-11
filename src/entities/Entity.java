package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entities.construction.Construction;
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
	public static BufferedImage AXE_EN = Game.spritesheet.getSprite(32, 256, 16, 16);
	public static BufferedImage AXE_WOOD_EN = Game.spritesheet.getSprite(0, 256, 16, 16);
	public static BufferedImage AXE_COOPER_EN = Game.spritesheet.getSprite(16, 256, 16, 16);
	public static BufferedImage AXE_SILVER_EN = Game.spritesheet.getSprite(32, 256, 16, 16);
	public static BufferedImage AXE_GOLD_EN = Game.spritesheet.getSprite(48, 256, 16, 16);
	public static BufferedImage AXE_DIAMOND_EN = Game.spritesheet.getSprite(64, 256, 16, 16);
	public static BufferedImage LIGHTER_EN = Game.spritesheet.getSprite(0, 128, 16, 16);
	static public BufferedImage DOOR_EN = Game.spritesheet.getSprite(16, 16, 16, 16);
	static public BufferedImage DOOR_LEFT_HOUSE_EN = Game.spritesheet.getSprite(80, 96, 16, 16); 
	static public BufferedImage PLAT_EN = Game.spritesheet.getSprite(16, 176, 16, 16);
	static public BufferedImage FISHING_ROD_EN = Game.spritesheet.getSprite(0, 96, 16, 16);
	static public BufferedImage FISH_EN = Game.spritesheet.getSprite(16, 144, 16, 16);
	static public BufferedImage HOE_EN = Game.spritesheet.getSprite(16, 96, 16, 16);
	static public BufferedImage HOE_WOOD_EN = Game.spritesheet.getSprite(0, 208, 16, 16);
	static public BufferedImage HOE_COOPER_EN = Game.spritesheet.getSprite(16, 208, 16, 16);
	static public BufferedImage HOE_SILVER_EN = Game.spritesheet.getSprite(32, 208, 16, 16);
	static public BufferedImage HOE_GOLD_EN = Game.spritesheet.getSprite(48, 208, 16, 16);
	static public BufferedImage HOE_DIAMOND_EN = Game.spritesheet.getSprite(64, 208, 16, 16);
	static public BufferedImage GROUND_EN = Game.spritesheet.getSprite(0, 160, 16, 16);
	static public BufferedImage GROUND_F1_EN = Game.spritesheet.getSprite(16, 160, 16, 16);
	static public BufferedImage GROUND_F2_EN = Game.spritesheet.getSprite(48, 160, 16, 16);
	static public BufferedImage GROUND_F3_EN = Game.spritesheet.getSprite(64, 160, 16, 16);
	public static BufferedImage FISHING_EN;
	static public BufferedImage PIG_BEEF_EN = Game.spritesheet.getSprite(16, 128, 16, 16);
	static public BufferedImage CREATION_TABLE_EN = Game.spritesheet.getSprite(16, 304, 16, 16);
	static public BufferedImage POTION_EN = Game.spritesheet.getSprite(0, 272, 16, 16);
	static public BufferedImage ESCADA_EN = Game.spritesheet.getSprite(0, 144, 16, 16);
	static public BufferedImage PICARETA_EN = Game.spritesheet.getSprite(32, 240, 16, 16);
	static public BufferedImage PICARETA_MADEIRA_EN = Game.spritesheet.getSprite(0, 240, 16, 16);
	static public BufferedImage PICARETA_COBRE_EN = Game.spritesheet.getSprite(16, 240, 16, 16);
	static public BufferedImage PICARETA_PRATA_EN = Game.spritesheet.getSprite(32, 240, 16, 16);
	static public BufferedImage PICARETA_OURO_EN = Game.spritesheet.getSprite(48, 240, 16, 16);
	static public BufferedImage PICARETA_DIAMANTE_EN = Game.spritesheet.getSprite(64, 240, 16, 16);
	static public BufferedImage FORNO_LIGADO_EN = Game.spritesheet.getSprite(16, 288, 16, 16);
	static public BufferedImage FORNO_DESLIGADO_EN = Game.spritesheet.getSprite(32, 272, 16, 16);
	static public BufferedImage BARRA_COBRE_EN = Game.spritesheet.getSprite(64, 288, 16, 16);
	static public BufferedImage BARRA_PRATA_EN = Game.spritesheet.getSprite(32, 288, 16, 16);
	static public BufferedImage BARRA_OURO_EN = Game.spritesheet.getSprite(48, 288, 16, 16);
	static public BufferedImage MADEIRA_PROCESSADA_EN = Game.spritesheet.getSprite(16, 272, 16, 16);
	static public BufferedImage BLOCO_DE_MADEIRA_EN = Game.spritesheet.getSprite(16, 288, 16, 16);
	static public BufferedImage ENXOFRE_PROCESSADO_EN = Game.spritesheet.getSprite(144, 288, 16, 16);
	static public BufferedImage NITRATO_DE_POTASIO_PROCESSADO_EN = Game.spritesheet.getSprite(96, 288, 16, 16);
	static public BufferedImage CARVAO_VEGETAL_EN = Game.spritesheet.getSprite(112, 288, 16, 16);
	static public BufferedImage POLVORA_EN = Game.spritesheet.getSprite(160, 288, 16, 16);
	static public BufferedImage FOGUEIRA_EN = Game.spritesheet.getSprite(48, 272, 16, 16);
	static public BufferedImage BOMBA_EN = Game.spritesheet.getSprite(128, 303, 16, 16);
	static public BufferedImage BOMBA_ATIVA_EN = Game.spritesheet.getSprite(144, 303, 16, 16);
	
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
	
public static boolean isColiddingConstruction(Construction e1, Construction e2) {

		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskxNoConstruction, e1.getY() + e1.maskyNoConstruction, e1.maskmNoConstruction, e1.maskhNoConstruction);
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
	
//	public int depth(Entity e1, Entity e2) {
//		
//		double yEntity1 = e1.getY();
//		double yEntity2 = e2.getY();
//		
//		
//		
//		if(yEntity1 > yEntity2 - 2) {
//			return  1;
//		
//		}else {
//			return 3;
//		}
//		
//	}
	
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
		
		g.drawImage(sprite ,this.getX() - Camera.x, this.getY() - Camera.y , null);
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskx, masky);
		
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
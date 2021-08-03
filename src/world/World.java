package world;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import entities.BulletShoot;
import entities.Door;
import entities.Enemy;
import entities.Entity;
import entities.Staircase;
import entities.Oak;
import entities.Particle;
import entities.Pine;
import entities.Player;
import entities.Stump;
import entities.Tree;
import entities.Willow;
import entities.NPC.Npc;
import entities.construction.Construction;
import entities.construction.House;
import entities.construction.Mine;
import entities.construction.Statue;
import entities.itens.Axe;
import entities.itens.Bullet;
import entities.itens.FishingRod;
import entities.itens.Hoe;
import entities.itens.LifePack;
import entities.itens.Lighter;
import entities.itens.Wapon;
import entities.mobs.Mob;
import entities.mobs.Pig;
import entities.spots.FishingSpot;
import graficos.Spritsheet;
import main.Game;
import util.Mapa;
import util.Regiao;

public class World {
	
	public Tile[] tiles;
	public int WIDTH, HEIGHT;
	public int TILE_SIZE = 16;
	public Npc npc;
	public Mapa mapa;
	public Regiao regiao;
	public String path;
	public int [] minimapaPixels;
	public BufferedImage minimapa;
	
	public World(String path) {
		
		this.path = path;
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(this.path));
			int [] pixels = new int [map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels,0,map.getWidth());
			
			minimapa = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			minimapaPixels = ((DataBufferInt)minimapa.getRaster().getDataBuffer()).getData();
			
			//Constru��es que precisam alidar os tiles ao redor
			Mine mine = null;
			House house = null;
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
			
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
					tiles[xx + (yy*WIDTH)].en = null;
					
					if(Game.mapaGame.equals(Mapa.MAPA_CALABOU�O)) {
						tiles[xx + (yy*WIDTH)] = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
					}
					
					if(pixelAtual == 0xFF000000) { //ch�o
						
						int rand = Game.rand.nextInt(100);
						
						if(rand <= 10) {
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR2);
						}else if(rand > 5 && rand <= 10){
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR3);
						}else if(rand > 10 && rand <= 15){
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR4);
						}else if(rand > 15 && rand <= 20){
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR5);
						}else if(rand > 20){
							tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
						}
						
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = null;
						
						
					}else if(pixelAtual == 0xFFFFFFFF) { //Parede
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
					}else if(pixelAtual == 0xFF0000FF) { //Player
						
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						Game.player.psTiles = xx + (yy*WIDTH);
						
					}else if(pixelAtual == 0xFFFF0000) { //Inimigo
						Enemy en = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						en.psTiles =  xx + (yy*WIDTH);
						en.xTile = xx;
						en.yTile = yy;
						
					}else if(pixelAtual == 0xFFFF6A00) { //Arma
						Wapon wapon = new Wapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN);
						wapon.tipo = "gun";
						Game.entities.add(wapon);
						wapon.psTiles = xx + (yy*WIDTH);
						
					}else if(pixelAtual == 0xFF00FF00) { //Cura
						LifePack lifePack = new LifePack(xx*16, yy*16, 16, 16, Entity.LIFE_PACK_EN);
						lifePack.tipo = "lifepack";
						Game.entities.add(lifePack);
						lifePack.psTiles = xx + (yy*WIDTH);
		
					}else if(pixelAtual == 0xFFFFFF00) { //Muni��o
						Bullet bullet = new Bullet(xx*16, yy*16, 16, 16, Entity.BULLET_EN);
						bullet.tipo = "bullet";
						Game.entities.add(bullet);
						bullet.psTiles = xx + (yy*WIDTH);
		
					}else if (pixelAtual == 0xFFB200FF){ //Portas
						Door door = new Door(xx*16, yy*16, 16, 16, Entity.DOOR_EN);
						Game.entities.add(door);
						tiles[xx + (yy*WIDTH)].en = door;
						door.psTiles = xx + (yy*WIDTH);
						door.xTile = xx;
						door.yTile = yy;
						
					}else if(pixelAtual == 0xFFFF00DC) { // Arvores
						
						//m�todo para randomizar a instancia de arvores
						
						Tree tree = null;
						
						if(xx>=50 && yy <= 50) {
							tree = new Willow(xx*16, yy*16, 16, 16, Entity.SALGUEIRO_EN);
						}else {
							if(Game.rand.nextInt(11) <=5) {
								 tree = new Oak(xx*16, yy*16, 16, 16, Entity.CARVALHO_EN);
							}else {
								tree = new Pine(xx*16, yy*16, 16, 16, Entity.PINHEIRO_EN);
							}
						}
						
						Game.entities.add(tree);
						tiles[xx + (yy*WIDTH)].en = tree;
						tree.psTiles = xx + (yy*WIDTH);
						tree.xTile = xx;
						tree.yTile = yy;
					}
					
					else if(pixelAtual == 0xFF7F3300) { // Machado
						Axe axe = new Axe(xx*16, yy*16, 16, 16, Entity.AXE_EN);
						axe.tipo = "machado";
						Game.entities.add(axe);
						axe.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = axe;
					}
					
					else if(pixelAtual == 0xFF0094FF) { // �gua
						
						WaterTile water = new WaterTile(xx*16, yy*16, null);
						tiles[xx + (yy*WIDTH)] = water;
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
						if(xx>=50 && yy <= 50) {
							water.swamp = true;
						}
						
					}
					
					else if(pixelAtual == 0xFF4C1E00) { // Terra
						tiles[xx + (yy*WIDTH)] = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
					}else if(pixelAtual == 0xFF808080) { //Isqueiro
						Lighter lighter = new Lighter(xx*16, yy*16, 16, 16, Entity.LIGHTER_EN);
						lighter.tipo = "isqueiro";
						Game.entities.add(lighter);
						lighter.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = lighter;
					
					}else if (pixelAtual == 0xFF00FFFF) { // Local de pesca
						FishingSpot fs = new FishingSpot(xx*16, yy*16, 16, 16, Entity.FISHING_EN);
						Game.entities.add(fs);
						tiles[xx + (yy*WIDTH)].en = fs;
						fs.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = fs;

						if(xx>=50 && yy <= 50) {
							fs.swamp = true;
						}
						
					}else if (pixelAtual == 0xFF5B7F00) {//Vara de pesca
						FishingRod fr = new FishingRod(xx*16, yy*16, 16, 16, Entity.FISHING_ROD_EN);
						fr.tipo = "vara de pesca";
						Game.entities.add(fr);
						fr.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = fr;
						
						
					}else if (pixelAtual == 0xFF808042) {//Enxada
						Hoe hoe = new Hoe(xx*16, yy*16, 16, 16, Entity.HOE_EN);
						hoe.tipo = "enxada";
						Game.entities.add(hoe);
						hoe.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = hoe;
						
					}else if (pixelAtual == 0xFF15F44E){ //Mina
						mine = new Mine(xx*16, yy*16, 32, 32, Construction.MINE_EN);
						mine.tipo = "mina";
						Game.entities.add(mine);
						mine.psTiles = xx + (yy*WIDTH);
						mine.xTile = xx;
						mine.yTile = yy;
						
					}else if (pixelAtual == 0xFFA84300) { //FloorCAL
						tiles[xx + (yy*WIDTH)] = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
					}else if (pixelAtual == 0xFF260F00) { //wallCAL
						tiles[xx + (yy*WIDTH)] = new WallCal(xx*16, yy*16, Tile.TILE_WALL_CAL);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
					}else if (pixelAtual == 0xFF0A0300) { //FLOOR_CAL_SOLID
						tiles[xx + (yy*WIDTH)] = new FloorCalSolid(xx*16, yy*16, Tile.TILE_FLOOR_CAL_SOLID);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
					}
					else if (pixelAtual == 0xFFFF6D77) { //ESCADA
						Staircase s= new Staircase(xx*16, yy*16, 16, 16, Entity.ESCADA_EN);
						Game.entities.add(s);
						s.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = s;
						
					}else if (pixelAtual == 0xFF870000){ //CASA
						house = new House(xx*16, yy*16, 32, 32, Construction.HOUSE_EN);
						house.tipo = "casa";
						Game.entities.add(house);
						house.psTiles = xx + (yy*WIDTH);
						house.xTile = xx;
						house.yTile = yy;
						
					}else if (pixelAtual == 0xFFC0C0C0){ //ESTATUA
						Statue statue = new Statue(xx*16, yy*16, 64, 64, Construction.STATUE_EN);
						statue.tipo = "estatua";
						Game.entities.add(statue);
						statue.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = statue;
				
					}
				}
			}
			
			if(!Game.mapaGame.equals(Mapa.MAPA_CALABOU�O)) {
				createMobs();
			
				npc = new Npc(144, 80, 16, 16, Game.spritesheet.getSprite(224, 0, 16, 16));
				Game.entities.add(npc);
//				xx + (yy*WIDTH)
//				9 + (5*100) = 509
				tiles[509].en = npc;
				
				confirmTilesConstruction32x32(house);
				confirmTilesConstruction32x32(mine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//M�todo randomico de gerar mapa
//
//		Game.player.setX(0);
//		Game.player.setY(0);
//		
//		WIDTH = 100;
//		HEIGHT = 100;
//		tiles = new Tile[WIDTH*HEIGHT];
//		
//		for(int xx =0; xx < WIDTH; xx++) {
//			for(int yy = 0; yy < HEIGHT; yy++) {
//				tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
//			}
//		}
//		
//		int dir = 0;
//		int xx = 0, yy = 0;
//		
//		for(int i=0; i<3000; i++) {
//			
//			tiles[xx+yy*WIDTH] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
//			
//			if(dir == 0) { //direita
//				if(xx < WIDTH) {
//					xx++;
//				}
//				
//			}else if (dir == 1) { //esquerda
//				if(xx > 0) {
//					xx--;
//				}
//				
//			}else if (dir == 2) { //baixo
//				if(yy < HEIGHT) {
//					yy++;
//				}
//				
//			}else if (dir == 3) { //cima
//				if(yy > 0) {
//					yy--;
//				}
//			}
//			
//			if(Game.rand.nextInt(100) < 30) {
//				dir = Game.rand.nextInt(4);
//			}
//			
//		}
		
	}
	
	public void confirmTilesConstruction32x32(Entity entity) {
//		xx + (yy*WIDTH)
//		13 + (3*100) = posis�o da casa, posi��o do pixel no mapa desenhado
		tiles[entity.psTiles].en = entity;
		tiles[(entity.xTile+1) + (entity.yTile*WIDTH)].en = entity;
		tiles[entity.xTile + ((entity.yTile+1)*WIDTH)].en = entity;
		tiles[(entity.xTile+1) + ((entity.yTile+1)*WIDTH)].en = entity;
	}
	
	public void createMobs() {
	
		for(int i = 0; i<tiles.length; i++) {
			if(tiles[i].en == null && tiles[i] instanceof FloorTile) {
				if(Game.rand.nextInt(1500) <= 5) {
					Pig pig = new Pig(tiles[i].x, tiles[i].y, 16, 16, null);
					pig.show = true;
					Game.mobs.add(pig);
				}
			}
		}
	}
	
	public void clearWorld(Tile tile) {
		if(!Game.entities.contains(tile.en))
			tile.en = null;
	}
	
	public void renderMiniMap() {
		
//		for(int i =0; i < minimapaPixels.length; i++) {
//			minimapaPixels[i] = 0xEFD551;
//		}
		
		for(int xx = 0; xx< WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				
//				if(!tiles[xx + (yy*WIDTH)].show)
//					Game.minimapaPixels[xx + (yy*WIDTH)] = 0x000000;
//				else {
					minimapaPixels[xx + (yy*WIDTH)] = 0xEFD551;
//				}
				
				if(tiles[xx + (yy*WIDTH)] instanceof WallTile) { //&& tiles[xx + (yy*WIDTH)].show) {
					minimapaPixels[xx + (yy*WIDTH)] = 0x87782D;
				}
				
				if(tiles[xx + (yy*WIDTH)] instanceof WaterTile) { //&& tiles[xx + (yy*WIDTH)].show) {
					minimapaPixels[xx + (yy*WIDTH)] = 0x0094FF;
				}
				
				if(tiles[xx + (yy*WIDTH)].en instanceof Tree) {//&& tiles[xx + (yy*WIDTH)].show) {	
					minimapaPixels[xx + (yy*WIDTH)] = 0x00FF21;
				}
				
				if(!(tiles[xx + (yy*WIDTH)].en instanceof Tree) &&
						!(tiles[xx + (yy*WIDTH)].en instanceof Player) &&
						tiles[xx + (yy*WIDTH)].en != null){ //&& 
						//tiles[xx + (yy*WIDTH)].show) {
					minimapaPixels[xx + (yy*WIDTH)] = 0xFF0000;
				}
				
				if((tiles[xx + (yy*WIDTH)] instanceof FloorCalSolid)){
					minimapaPixels[xx + (yy*WIDTH)] = 0xA84300;
				}
				
				for(int i=0; i < Game.enemies.size(); i++) {
					int xEnemy = (int) (Game.enemies.get(i).x/16);
					int yEnemy = (int) (Game.enemies.get(i).y/16);
					
					int ps = (int)(xEnemy+ yEnemy*Game.world.WIDTH);
					
					if(Game.world.tiles[ps].show) {
						minimapaPixels[ps] = 0xFF0000;
					}else {
						minimapaPixels[ps] = 0x000000;
					}
					
				}
				
				if(Game.mapaGame.equals(Mapa.MAPA_FLORESTA)) {
					for(int i=0; i < Game.mobs.size(); i++) {
						Mob m = Game.mobs.get(i);
						int xMob = (int) (Game.mobs.get(i).x/16);
						int yMob = (int) (Game.mobs.get(i).y/16);
						
						int ps = (int)(xMob+ yMob*Game.world.WIDTH);
						
						if(m instanceof Mob) {
//							if(World.tiles[ps].show) {
								minimapaPixels[ps] = 0xFF0000;
//							}else {
//								minimapaPixels[ps] = 0x000000;
//							}
						}
					}
				}
				
				if(yy == 0 || xx == 0 || xx == 99 || yy == 99 ) { //Vizualizar as bordas do mapa
					minimapaPixels[xx + (yy*WIDTH)] = 0x87782D;
				}
				
			}
		}
		
		int xPlayer = Game.player.getX()/16;
		int yPlayer = Game.player.getY()/16;
		minimapaPixels[xPlayer + (yPlayer*WIDTH)] = 0x000000;
//		for(int i = 0; i < Game.enemies.size(); i++) {
//			Game.minimapaPixels[(Game.enemies.get(i).getX()/16) + ((Game.enemies.get(i).getY()/16)*WIDTH)] = 0xFF0000;
//		}
	}
	
	public static void generateParticles(int amount, int x, int y, int ps) {
		
		for(int i=0; i < amount; i++) {
			if(BulletShoot.collidingBullet) {
				Particle particle = new Particle(x + 8, y + 5, 1, 1, null);
				Game.particles.add(particle);
			}else {
				Particle particle = new Particle(x, y, 1, 1, null);
				Game.particles.add(particle);
			}
		}
		
	}
	
	public boolean isFree(int xNext, int yNext, int zPlayer) {
		
		int x1 = (xNext + 5) / TILE_SIZE;
		int y1 = (yNext + 2) / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 6) / TILE_SIZE;
		int y2 = (yNext + 2) / TILE_SIZE;
		
		int x3 = (xNext + 5) / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 6) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE -1) / TILE_SIZE;
		
		if (!((tiles[x1 + (y1*WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*WIDTH)] instanceof WallTile)) &&
				!((tiles[x1 + (y1*WIDTH)] instanceof WaterTile) ||
				(tiles[x2 + (y2*WIDTH)] instanceof WaterTile) ||
				(tiles[x3 + (y3*WIDTH)] instanceof WaterTile) ||
				(tiles[x4 + (y4*WIDTH)] instanceof WaterTile))){
			return true;
		}
		
//		if(zPlayer > 0) { // pular por cima das paredes
//			return true;
//		}
		
		return false;
				
	}
	
	public static boolean isColiddingFloorTileToGround(Entity e, Tile t ) {
		
		Rectangle e1Mask = new Rectangle(e.getX(), e.getY() , 16, 16);
		Rectangle e2Mask = new Rectangle(t.getX() , t.getY() , 16,16);
		
		if(t instanceof FloorTile &&
				!(t.en instanceof Tree) &&
				!(t.en instanceof Stump) &&
				e1Mask.intersects(e2Mask) &&
				e.getX() >= (t.getX()-1) &&
				e.getY() >= (t.getY())-1) {
			return true;
		}else {
			return false;	
		}
		
	}

	public static void restarGame(String level) {
		
		Game.entities =  new ArrayList<Entity>();
		Game.enemies =  new ArrayList<Enemy>();
		Game.spritesheet =  new Spritsheet("/spritesheet.png");
		//Game.player  = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.player.life = 100;
		Game.entities.add(Game.player);
		//Game.world =  new World("/map.png");
		Game.world = new World("/"+ level);
		return;
	}
	
	public void render(Graphics g) {
		
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;
		
		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);
		
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy <= yFinal; yy++) {
				
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) 
					continue;
				
				Tile tile = tiles[xx + (yy*WIDTH)];	
				if(tile.mapa.contains(Game.mapaGame) && tile.regiao.contains(Game.regiaoGame)) 
					tile.render(g);
				
				clearWorld(tile);
			}
		}
		
		if(Game.player.openMap)
			renderMiniMap();
		
//		g.setColor(Color.black);
//		g.fillRect(xStart - Camera.x  , yStart - Camera.y , xFinal, yFinal);
		
	}
	
}

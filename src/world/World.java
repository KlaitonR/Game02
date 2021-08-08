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
import entities.TreeOak;
import entities.Particle;
import entities.TreePine;
import entities.Player;
import entities.Tree;
import entities.TreeWillow;
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
import entities.itens.Pickaxe;
import entities.itens.Wapon;
import entities.mobs.Mob;
import entities.mobs.Pig;
import entities.objectMap.GramaAgua;
import entities.objectMap.VitoriaRegia;
import entities.spots.FishingSpot;
import entities.spots.MiningSite;
import entities.spots.MiningSiteCoal;
import entities.spots.MiningSiteDiamond;
import entities.spots.MiningSiteEmerald;
import entities.spots.MiningSiteGold;
import entities.spots.MiningSiteSilver;
import entities.spots.MiningSiteCopper;
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
	ArrayList<WaterTile> waterList;
	ArrayList<FishingSpot> spotsList;
	ArrayList<EarthTile> earthList;
	ArrayList<WallCal> wallCalList;
	
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
			
			//Construções que precisam alidar os tiles ao redor
			Mine mine = null;
			House house = null;
			
			waterList = new ArrayList<>();
			spotsList = new ArrayList<>();	
			earthList = new ArrayList<>();
			wallCalList = new ArrayList<>();
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
			
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
					tiles[xx + (yy*WIDTH)].en = null;
					
					if(Game.mapaGame.equals(Mapa.MAPA_CALABOUÇO)) {
						tiles[xx + (yy*WIDTH)] = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
					}
					
					if(pixelAtual == 0xFF000000) { //chão
						
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
						
						if(Game.rand.nextInt(11) <=5) {
							tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_TREE1);
							tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						}else {
							tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_TREE2);
							tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						}
						
//						if(xx == 0 && yy == 0) 
//							tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_CERCA_DIREITA);
//						else if (xx == 0 && yy == HEIGHT) 
//								tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_CERCA_DIREITA);
//						else if(xx == WIDTH && yy == 0) 
//							tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_CERCA_ESQUERDA);
//						else if (xx == WIDTH && yy == HEIGHT) 
//								tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_CERCA_ESQUERDA);
//						else if (xx == 0) 
//							tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_CERCA_VERTICAL);
//						else if (yy == 0) 
//							tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_CERCA_HORIZONTAL);
//						
//						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
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
		
					}else if(pixelAtual == 0xFFFFFF00) { //Munição
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
						
						//método para randomizar a instancia de arvores
						
						Tree tree = null;
						
						if(xx>=50 && yy <= 50) {
							tree = new TreeWillow(xx*16, yy*16, 16, 16, Tree.SALGUEIRO_EN);
						}else {
							if(Game.rand.nextInt(11) <=5) {
								 tree = new TreeOak(xx*16, yy*16, 16, 16, Tree.CARVALHO_EN);
							}else {
								tree = new TreePine(xx*16, yy*16, 16, 16, Tree.PINHEIRO_EN);
							}
						}
						
						Game.entities.add(tree);
						tiles[xx + (yy*WIDTH)].en = tree;
						tree.psTiles = xx + (yy*WIDTH);
						tree.xTile = xx;
						tree.yTile = yy;
						
					}else if(pixelAtual == 0xFF7F3300) { // Machado
						Axe axe = new Axe(xx*16, yy*16, 16, 16, Entity.AXE_EN);
						axe.tipo = "machado";
						Game.entities.add(axe);
						axe.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = axe;
						
					}else if(pixelAtual == 0xFF0094FF) { // Água
						WaterTile water = new WaterTile(xx*16, yy*16, null);
						tiles[xx + (yy*WIDTH)] = water;
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						water.xTile = xx;
						water.yTile = yy;
						
						if(xx>=50 && yy <= 50) {
							water.swamp = true;
							if(Game.rand.nextInt(100) <= 30) {
								VitoriaRegia vt = new VitoriaRegia(xx*16, yy*16, 16, 16, Game.spritesheet.getSprite(224, 16, 16, 16));
								Game.entities.add(vt);
							}else if (Game.rand.nextInt(100) <= 30) {
								GramaAgua gat = new GramaAgua(xx*16, yy*16, 16, 16, Game.spritesheet.getSprite(208, 16, 16, 16));
								Game.entities.add(gat);
							}
						}
						
						if(Game.rand.nextInt(100) < 2) {
							VitoriaRegia vt = new VitoriaRegia(xx*16, yy*16, 16, 16, Game.spritesheet.getSprite(224, 16, 16, 16));
							Game.entities.add(vt);
						}else if (Game.rand.nextInt(100) < 2) {
							GramaAgua gat = new GramaAgua(xx*16, yy*16, 16, 16, Game.spritesheet.getSprite(208, 16, 16, 16));
							Game.entities.add(gat);
						}
						
						waterList.add(water);
						
					}
					
					else if(pixelAtual == 0xFF4C1E00) { // Terra
						EarthTile et = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
						tiles[xx + (yy*WIDTH)] = et;
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						et.xTile = xx;
						et.yTile = yy;
						
						earthList.add(et);
						
					}else if(pixelAtual == 0xFF808080) { //Isqueiro
						Lighter lighter = new Lighter(xx*16, yy*16, 16, 16, Entity.LIGHTER_EN);
						lighter.tipo = "isqueiro";
						Game.entities.add(lighter);
						lighter.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = lighter;
					
					}else if (pixelAtual == 0xFF00FFFF) { // Local de pesca
						FishingSpot fs = new FishingSpot(xx*16, yy*16, 16, 16, Entity.FISHING_EN);
						Game.entities.add(fs);
						//Retirar o floor
						WaterTile waterTile = new WaterTile(xx*16, yy*16, null);
						tiles[xx + (yy*WIDTH)] = waterTile;
						tiles[xx + (yy*WIDTH)].en = fs;
						fs.psTiles = xx + (yy*WIDTH);
						fs.xTile = xx;
						fs.yTile = yy;

						if(xx>=50 && yy <= 50) {
							fs.swamp = true;
						}
						
						spotsList.add(fs);
						
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
						
						WallCal wc = new WallCal(xx*16, yy*16, Tile.TILE_WALL_CAL);
						tiles[xx + (yy*WIDTH)] = wc;
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						wc.xTile = xx;
						wc.yTile = yy;
						
						wallCalList.add(wc);
						
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
				
					}else if (pixelAtual == 0xFF7F0037){ //PICARETA
						Pickaxe pa = new Pickaxe(xx*16, yy*16, 16, 16, Construction.PICARETA_EN);
						pa.tipo = "picareta";
						Game.entities.add(pa);
						pa.psTiles = xx + (yy*WIDTH);
						
					}else if (pixelAtual == 0xFF6B6B6B){ //LOCAL MINÉRIO DE PRATA
						MiningSiteSilver mss = new MiningSiteSilver(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_SILVER_EN);
						Game.entities.add(mss);
						mss.tipo = "localMinérioPrata";
						mss.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = mss;
						
					}else if (pixelAtual == 0xFFC1B100){ //LOCAL MINÉRIO DE OURO
						MiningSiteGold msg = new MiningSiteGold(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_GOLD_EN);
						Game.entities.add(msg);
						msg.tipo = "localMinérioOuro";
						msg.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = msg;
						
					}else if (pixelAtual == 0xFF00E5FF){ //LOCAL DE DIAMANTE
						MiningSiteDiamond msd = new MiningSiteDiamond(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_DIAMOND_EN);
						Game.entities.add(msd);
						msd.tipo = "localDiamante";
						msd.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = msd;
						
					}else if (pixelAtual == 0xFF6B4700){ //LOCAL MINÉRIO DE COBRE
						MiningSiteCopper mscp = new MiningSiteCopper(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_COPPER_EN);
						Game.entities.add(mscp);
						mscp.tipo = "localMinérioCobre";
						mscp.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = mscp;
						
					}else if (pixelAtual == 0xFF161616){ //LOCAL MINÉRIO DE CARVÃO
						MiningSiteCoal msco = new MiningSiteCoal(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_COAL_EN);
						Game.entities.add(msco);
						msco.tipo = "localMinérioCarvão";
						msco.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = msco;
						
					}else if (pixelAtual == 0xFF20C400){ //LOCAL DE ESMERALDA
						MiningSiteEmerald mse = new MiningSiteEmerald(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_EMERALD_EN);
						Game.entities.add(mse);
						mse.tipo = "localEsmeralda";
						mse.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = mse;
						
					}
					
				}
			}
			
				npc = new Npc(144, 80, 16, 16, Game.spritesheet.getSprite(224, 0, 16, 16));
				Game.entities.add(npc);
//				xx + (yy*WIDTH)
//				9 + (5*100) = 509
				tiles[509].en = npc;
				
				//Casa haja mais de um objeto, utilizar uma lista
				confirmTilesConstruction32x32(house);
				confirmTilesConstruction32x32(mine);
				
				if(!Game.mapaGame.equals(Mapa.MAPA_CALABOUÇO)) {
					createMobs();
				}
				
				createBorder();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//Método randomico de gerar mapa
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
//		13 + (3*100) = posisão da casa, posição do pixel no mapa desenhado
		if(entity != null) {
			tiles[entity.psTiles].en = entity;
			tiles[(entity.xTile+1) + (entity.yTile*WIDTH)].en = entity;
			tiles[entity.xTile + ((entity.yTile+1)*WIDTH)].en = entity;
			tiles[(entity.xTile+1) + ((entity.yTile+1)*WIDTH)].en = entity;
		}
	}
	
	public void createBorder() {
		
		if(!Game.mapaGame.equals(Mapa.MAPA_CALABOUÇO)) {
			
			WaterTile water = null;
			FishingSpot fs = null;
			EarthTile et = null;
			
//			System.out.println(Game.WIDTH + "      " + Game.HEIGHT);
//			System.out.println(WIDTH + "      " + HEIGHT);
		
			for(int i=0; i<waterList.size();i++) {
				water = waterList.get(i);
				
				if( water.getX() > 0 &&
						water.getX() < WIDTH*16 &&
						water.getY() > 0 &&
						water.getY() <= HEIGHT*16) {
					
					if(!(tiles[water.xTile + ((water.yTile + 1)*WIDTH)] instanceof WaterTile)) //cima
						water.up = true;
					
					if(!(tiles[water.xTile + ((water.yTile - 1)*WIDTH)] instanceof WaterTile)) // baixo
						water.down = true;
					
					if(!(tiles[(water.xTile - 1) + (water.yTile*WIDTH)] instanceof WaterTile)) // esquerda
						water.left = true;
					
					if(!(tiles[(water.xTile + 1) + (water.yTile*WIDTH)] instanceof WaterTile)) // direita
						water.right = true;
				}
			}
			
			for(int i=0; i<spotsList.size();i++) {
				fs = spotsList.get(i);
				
				if( fs.getX() > 0 &&
						fs.getX() < WIDTH*16 &&
						fs.getY() > 0 &&
						fs.getY() <= HEIGHT*16) {
					
					if(!(tiles[fs.xTile + ((fs.yTile + 1)*WIDTH)] instanceof WaterTile)) //cima
						fs.up = true;
					
					if(!(tiles[fs.xTile + ((fs.yTile - 1)*WIDTH)] instanceof WaterTile)) // baixo
						fs.down = true;
					
					if(!(tiles[(fs.xTile - 1) + (fs.yTile*WIDTH)] instanceof WaterTile)) // esquerda
						fs.left = true;
					
					if(!(tiles[(fs.xTile+1) + (fs.yTile*WIDTH)] instanceof WaterTile)) // direita
						fs.right = true;
				}
			}
			
			for(int i=0; i<earthList.size();i++) {
				et = earthList.get(i);
				
				if(et.getX() > 0 &&
				et.getX() < WIDTH*16 &&
				et.getY() > 0 &&
				et.getY() <= HEIGHT*16) {
					
					if(!(tiles[et.xTile + ((et.yTile + 1)*WIDTH)] instanceof EarthTile)) //cima
						et.up = true;
					
					if(!(tiles[et.xTile + ((et.yTile - 1)*WIDTH)] instanceof EarthTile)) // baixo
						et.down = true;
					
					if(!(tiles[(et.xTile - 1) + (et.yTile*WIDTH)] instanceof EarthTile)) // esquerda
						et.left = true;
					
					if(!(tiles[(et.xTile+1) + (et.yTile*WIDTH)] instanceof EarthTile)) // direita
						et.right = true;
				}
			}
		
		}else if (Game.mapaGame.equals(Mapa.MAPA_CALABOUÇO)) {
			WallCal wc = null;
			
			for(int i=0; i<wallCalList.size();i++) {
				wc = wallCalList.get(i);
				
				if( wc.getX() > 0 &&
						wc.getX() < Game.WIDTH * 3 &&
						wc.getY() > 0 &&
						wc.getY() <= Game.HEIGHT * 3) {
					
					if(!(tiles[wc.xTile + ((wc.yTile + 1)*WIDTH)] instanceof WallCal)) //cima
						wc.up = true;
					
					if(!(tiles[wc.xTile + ((wc.yTile - 1)*WIDTH)] instanceof WallCal)) // baixo
						wc.down = true;
					
					if(!(tiles[(wc.xTile - 1) + (wc.yTile*WIDTH)] instanceof WallCal)) // esquerda
						wc.left = true;
					
					if(!(tiles[(wc.xTile+1) + (wc.yTile*WIDTH)] instanceof WallCal)) // direita
						wc.right = true;
				}
			}
		}
		
//		if((water.x) + ((water.y + 1)*WIDTH)) {
//			
//			tiles[(water.x) + ((water.y + 1)*WIDTH)]; //cima
//			tiles[(water.x-1) + (water.y*WIDTH)]; // esquerda
//			tiles[(water.x+1) + (water.y*WIDTH)]; // direita
//			tiles[water.x + ((water.y - 1)*WIDTH)]; //baixo
//			tiles[(water.x + 1) + ((water.y + 1)*WIDTH)]; //cima direita
//			tiles[(water.x - 1) + ((water.y + 1)*WIDTH)]; // cima esquerda
//			tiles[(water.x + 1) + ((water.y - 1)*WIDTH)]; //baixo direita
//			tiles[(water.x - 1) + ((water.y - 1)*WIDTH)]; //baixo esquerda
//			
//		}
	}
	
	public void createMobs() {
	
		for(int i = 0; i<tiles.length; i++) {
			if(tiles[i].en == null &&
					!(tiles[i].en instanceof Mine) &&
					!(tiles[i].en instanceof House) &&
					tiles[i] instanceof FloorTile ) {
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
		
		if(collisionFloorTile(xNext, yNext, zPlayer) &&
				collisionWaterTile(xNext, yNext, zPlayer)) {
			return true;
		}
		return false;
	}
	
	public boolean collisionFloorTile(int xNext, int yNext, int zPlayer) {
		
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
				(tiles[x4 + (y4*WIDTH)] instanceof WallTile))){
			return true;
		}
		
//		if(zPlayer > 0) { // pular por cima das paredes
//		return true;
//	}
		
		return false;
	}
	
	public boolean collisionWaterTile(int xNext, int yNext, int zPlayer) {
		
		int x1 = (xNext + 5) / TILE_SIZE;
		int y1 = (yNext + 14) / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 6) / TILE_SIZE;
		int y2 = (yNext + 14) / TILE_SIZE;
		
		int x3 = (xNext + 5) / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE -2) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 6) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE -2) / TILE_SIZE;
		
		if (!((tiles[x1 + (y1*WIDTH)] instanceof WaterTile) ||
			(tiles[x2 + (y2*WIDTH)] instanceof WaterTile) ||
			(tiles[x3 + (y3*WIDTH)] instanceof WaterTile) ||
			(tiles[x4 + (y4*WIDTH)] instanceof WaterTile))){
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isColiddingFloorTileToGround(Entity player, Tile t ) {
		
		Rectangle e1Mask = new Rectangle(player.getX(), player.getY() , 16, 16);
		Rectangle e2Mask = new Rectangle(t.getX() ,t.getY() ,16, 16);
		
		if(t instanceof FloorTile &&
				!(t.en instanceof Tree) &&
				e1Mask.intersects(e2Mask) &&
				player.getX() < t.getX() + 4 &&
				player.getY() < t.getY() + 4) {
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

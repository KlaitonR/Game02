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
import entities.DoorHouse;
import entities.Enemy;
import entities.Entity;
import entities.Staircase;
import entities.Particle;
import entities.Player;
import entities.NPC.Npc;
import entities.construction.Construction;
import entities.construction.House;
import entities.construction.Mine;
import entities.construction.Statue;
import entities.construction.mobilia.Bed;
import entities.construction.mobilia.BedsideLamp;
import entities.construction.mobilia.Cabinet;
import entities.construction.mobilia.Chair;
import entities.construction.mobilia.Drawer;
import entities.construction.mobilia.FlowerVase;
import entities.construction.mobilia.Mat;
import entities.construction.mobilia.Table;
import entities.construction.mobilia.Watch;
import entities.itens.Axe;
import entities.itens.Bomb;
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
import entities.spots.MiningSitePhosphor;
import entities.spots.MiningSitePotassiumNitrate;
import entities.spots.MiningSiteSilver;
import entities.spots.MiningSiteSulfor;
import entities.spots.Tree;
import entities.spots.TreeOak;
import entities.spots.TreePine;
import entities.spots.TreeWillow;
import entities.spots.MiningSiteCopper;
import graficos.Spritsheet;
import main.Game;
import tiles.FloorRoomHouse;
import tiles.WallRoomHouse;
import util.Mapa;
import util.Regiao;

public class World {
	
	public Tile[] tiles;
	public int WIDTH, HEIGHT;
	public int TILE_SIZE = 16;
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
					
					if(Game.mapaGame.equals(Mapa.MAPA_FLORESTA)) {
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = null;
					
					}else if(Game.mapaGame.equals(Mapa.MAPA_CALABOUCO)) {
						tiles[xx + (yy*WIDTH)] = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = null;
						
					}else if (Game.mapaGame.equals(Mapa.MAPA_ROOM_HOUSE_01)) {
						tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_FLOOR_ROOM_HOUSE_01);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = null;
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
						
						Bomb b = new Bomb(xx*16, yy*16, 16, 16, Entity.BOMBA_EN);
						b.tipo = "bomba";
						Game.entities.add(b);
						
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
						tiles[wapon.psTiles].en = wapon;
						
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
						tiles[bullet.psTiles].en = bullet;
		
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
						
					}else if (pixelAtual == 0xFFFFE500){ //LOCAL DE SULFATO
						MiningSiteSulfor msd = new MiningSiteSulfor(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_SULFOR_EN);
						Game.entities.add(msd);
						msd.tipo = "localEnxofre";
						msd.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = msd;
						
					}else if (pixelAtual == 0xFF9E9E9E){ //LOCAL DE NITRATO DE POTASSIO
						MiningSitePotassiumNitrate msd = new MiningSitePotassiumNitrate(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_POTASSIUM_NITARTE_EN);
						Game.entities.add(msd);
						msd.tipo = "localNitratoDePotasio";
						msd.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = msd;
							
					}else if (pixelAtual == 0xFF707063){ //LOCAL DE FOSFORO
						MiningSitePhosphor msd = new MiningSitePhosphor(xx*16, yy*16, 16, 16, MiningSite.MINING_SITE_PHOSPHOR_EN);
						Game.entities.add(msd);
						msd.tipo = "localPotacio";
						msd.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = msd;
							
					}else if (pixelAtual == 0xFFB54500){ //WALL HOUSE ROOM 01
						
						if(xx == 0 && yy == 0) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_06);
						}else if (xx == 0 && yy == WIDTH-1) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_08);
						}else if (xx == HEIGHT-1 && yy == 0) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_05);		
						}else if (xx == HEIGHT-1 && yy == WIDTH-1) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_07);
						}else if (xx == 0) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_04);
						}else if (yy == WIDTH-1) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_03 );
						}else if (xx == HEIGHT-1) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_02);
						}else if (yy == 0) {
							tiles[xx + (yy*WIDTH)] = new WallRoomHouse(xx*16, yy*16, Tile.TILE_WALL_ROOM_HOUSE_01_01);
						}
						
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = null;
						
					}else if (pixelAtual == 0xFFB58C73){
						tiles[xx + (yy*WIDTH)] = new FloorRoomHouse(xx*16, yy*16, Tile.TILE_FLOOR_ROOM_HOUSE_01);
						
					}else if (pixelAtual == 0xFF876856){
						
						DoorHouse dh = null;
						
						if(xx == 0)
							dh = new DoorHouse(xx*16, yy*16, 16, 16, Entity.DOOR_LEFT_HOUSE_EN);
						
						Game.entities.add(dh);
						dh.tipo = "PortaDaCasa";
						dh.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = dh;
						
					}
					
				}
			}
			
			if(Game.mapaGame.equals(Mapa.MAPA_FLORESTA)) {
				Npc npc = new Npc(144, 80, 16, 16, Game.spritesheet.getSprite(224, 0, 16, 16));
				Game.entities.add(npc);
//				xx + (yy*WIDTH)
//				9 + (5*100) = 509
				tiles[509].en = npc;
				
				//Casa haja mais de um objeto, utilizar uma lista
				confirmTilesConstruction32x32(house);
				confirmTilesConstruction32x32(mine);
				
				createMobs();
			}else if(Game.mapaGame.equals(Mapa.MAPA_ROOM_HOUSE_01)) {
				createMobilia();
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
	
	private void createMobilia() {
		
		BedsideLamp bl = new BedsideLamp(12, 36, 16, 16, Construction.BEDSIDE_LAMP_EN);
		Game.entities.add(bl);
		bl.xTile = (int)(bl.getX()/16);
		bl.yTile = (int)(bl.getY()/16);
		bl.psTiles = bl.xTile + (bl.yTile*WIDTH);
		
		Bed bed = new Bed(11, 96, 32, 16, Construction.BED_EN);
		Game.entities.add(bed);
		bed.xTile = (int)(bed.getX()/16);
		bed.yTile = (int)(bed.getY()/16);
		bed.psTiles = bed.xTile + (bed.yTile*WIDTH);
		
		Drawer dw = new Drawer(32, 14, 16, 16, Construction.DRAWER_EN);
		Game.entities.add(dw);
		dw.xTile = (int)(dw.getX()/16);
		dw.yTile = (int)(dw.getY()/16);
		dw.psTiles = dw.xTile + (dw.yTile*WIDTH);
		
		Cabinet cb = new Cabinet(16, 0, 16, 32, Construction.CABINET_EN);
		Game.entities.add(cb);
		cb.xTile = (int)(cb.getX()/16);
		cb.yTile = (int)(cb.getY()/16);
		cb.psTiles = cb.xTile + (cb.yTile*WIDTH);
		
		Cabinet cb02 = new Cabinet(98, 48, 16, 32, Construction.CABINET_02_EN);
		Game.entities.add(cb02);
		cb02.xTile = (int)(cb02.getX()/16);
		cb02.yTile = (int)(cb02.getY()/16);
		cb02.psTiles = cb02.xTile + (cb02.yTile*WIDTH);
		
		Watch wt = new Watch(80, 5, 16, 16, Construction.WATCH_EN);
		Game.entities.add(wt);
		wt.xTile = (int)(wt.getX()/16);
		wt.yTile = (int)(wt.getY()/16);
		wt.psTiles = wt.xTile + (wt.yTile*WIDTH);
		
		Chair ch = new Chair(58, 96, 16, 16, Construction.CHAIR_EN);
		Game.entities.add(ch);
		ch.xTile = (int)(ch.getX()/16);
		ch.yTile = (int)(ch.getY()/16);
		ch.psTiles = ch.xTile + (ch.yTile*WIDTH);
		
		Chair ch02 = new Chair(75, 93, 16, 16, Construction.CHAIR_EN);
		Game.entities.add(ch02);
		ch02.xTile = (int)(ch02.getX()/16);
		ch02.yTile = (int)(ch02.getY()/16);
		ch02.psTiles = ch02.xTile + (ch02.yTile*WIDTH);
		
		Table tb = new Table(64, 96, 16, 32, Construction.TABLE_EN);
		Game.entities.add(tb);
		tb.xTile = (int)(tb.getX()/16);
		tb.yTile = (int)(tb.getY()/16);
		tb.psTiles = tb.xTile + (tb.yTile*WIDTH);
		
		Mat mt = new Mat(55, 55, 16, 16, Construction.MAT_EN);
		Game.entities.add(mt);
		mt.xTile = (int)(mt.getX()/16);
		mt.yTile = (int)(mt.getY()/16);
		mt.psTiles = mt.xTile + (mt.yTile*WIDTH);
		
		FlowerVase fv = new FlowerVase(12, 64, 16, 16, Construction.FLOWER_VASE_EN);
		Game.entities.add(fv);
		fv.xTile = (int)(fv.getX()/16);
		fv.yTile = (int)(fv.getY()/16);
		fv.psTiles = fv.xTile + (fv.yTile*WIDTH);
		tiles[fv.psTiles].en = fv;
		
		confirmTilesConstruction32x16(bed);
		confirmTilesConstruction32x16(tb);
		confirmTilesConstruction32x16(cb);
		confirmTilesConstruction32x16(cb02);
		
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
	
	public void confirmTilesConstruction16x32(Entity entity) {
		if(entity != null) {
			tiles[entity.psTiles].en = entity;
			tiles[entity.xTile + ((entity.yTile+1)*WIDTH)].en = entity;
		}
	}
	
	public void confirmTilesConstruction32x16(Entity entity) {
		if(entity != null) {
			tiles[entity.psTiles].en = entity;
			tiles[(entity.xTile+1) + (entity.yTile*WIDTH)].en = entity;
		}
	}
	
	public void createBorder() {
		
		if(Game.mapaGame.equals(Mapa.MAPA_FLORESTA)) {
			
			WaterTile water = null;
			FishingSpot fs = null;
			EarthTile et = null;
		
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
		
		}else if (Game.mapaGame.equals(Mapa.MAPA_CALABOUCO)) {
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
		
		for(int xx = 0; xx < WIDTH; xx++) {
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
		
		if(collisionWallTile(xNext, yNext, zPlayer) &&
				collisionWaterTile(xNext, yNext, zPlayer)) {
			return true;
		}
		return false;
	}
	
	public boolean collisionWallTile(int xNext, int yNext, int zPlayer) {
		
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

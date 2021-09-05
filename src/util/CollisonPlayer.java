package util;

import java.awt.Rectangle;
import java.util.ArrayList;
import entities.Door;
import entities.DoorHouse;
import entities.Entity;
import entities.Ground;
import entities.Oven;
import entities.Staircase;
import entities.Stump;
import entities.NPC.Npc;
import entities.construction.Bed;
import entities.construction.BedsideLamp;
import entities.construction.Cabinet;
import entities.construction.Chair;
import entities.construction.Drawer;
import entities.construction.FlowerVase;
import entities.construction.House;
import entities.construction.Mat;
import entities.construction.Mine;
import entities.construction.Statue;
import entities.construction.Table;
import entities.construction.Watch;
import entities.itens.Axe;
import entities.itens.Beef;
import entities.itens.Bullet;
import entities.itens.Firewood;
import entities.itens.Fish;
import entities.itens.FishingRod;
import entities.itens.Hoe;
import entities.itens.LifePack;
import entities.itens.Lighter;
import entities.itens.Ore;
import entities.itens.Pickaxe;
import entities.itens.Potion;
import entities.itens.Root;
import entities.itens.Seed;
import entities.itens.Wapon;
import entities.mobs.Mob;
import entities.mobs.Pig;
import entities.spots.FishingSpot;
import entities.spots.MiningSite;
import entities.spots.Tree;
import main.Game;
import main.Sound;
import world.FloorTile;
import world.Tile;
import world.WaterTile;
import world.World;

public class CollisonPlayer {
	
	public ArrayList<Entity> allCollission = new ArrayList<>();
	
	//Entidades coletaveis
	public void checkCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof Axe || 
					atual instanceof Beef ||
					atual instanceof Firewood ||
					atual instanceof Fish ||
					atual instanceof FishingRod ||
					atual instanceof Hoe ||
					atual instanceof LifePack ||
					atual instanceof Lighter ||
					atual instanceof Potion ||
					atual instanceof Root ||
					atual instanceof Seed ||
					atual instanceof Wapon ||
					atual instanceof Pickaxe ||
					atual instanceof Ore ||
					atual instanceof Oven) {
					confirmCollision(atual, Game.sysInv.checkPositionGetInv());
				}
			}
		}
	}
	
	public void confirmCollision(Entity atual, int index) {
		
		if(Entity.isColidding(Game.player, atual)) {
			
			Game.ui.buttonColletct = true;
			atual.depth = Game.player.depthPlayer(atual);
				
			if(Game.player.getItem) {
				if(!Game.sysInv.checkPackInv(atual)) {
					if (index >= 0 && index <= Game.sysInv.inventario.length) {
//						atual.clear = false;
//						atual.timeClear = 0;
						Game.sysInv.inventario[index] = atual;
						Game.sysInv.inv[index] = atual.getSprite();
						Game.sysInv.handItem = Game.sysInv.inventario[index];
						Game.sysInv.handIndexItem = index;
						Game.entities.remove(atual);
						Sound.Clips.dropAndGetItem.play();
						Game.ui.buttonColletct = false;
					}
				}
			}
		}else {
			Game.ui.buttonColletct = false;
		}
	}
	
	//Colisões com entidades não coletaveis
	
	public void checkCollisionAmmo() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Bullet) {
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(Entity.isColidding(Game.player, atual)) {
						
						atual.depth = Game.player.depthPlayer(atual);
						
						Game.player.ammo += 10;
						Game.entities.remove(atual);
						Sound.Clips.missAmo.play();
					}else {
						allCollission.remove(atual);
					}
				}
			}
		}
	}
	
	//Colisões com objetos que não irão para o inventario (entidades não coletaveis)
		public void checkCollisionNotInterator() {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(atual instanceof Npc ||
							atual instanceof Bed ||
							atual instanceof Cabinet ||
							atual instanceof FlowerVase ||
							atual instanceof Watch ||
							atual instanceof Table ||
							atual instanceof Chair ||
							atual instanceof Drawer ||
							atual instanceof BedsideLamp) {
						if(Entity.isColidding(Game.player, atual)) {
							atual.depth = Game.player.depthPlayer(atual);
						}
						
						if(atual instanceof Mat) {
							Game.player.depth = atual.depth + 1;
							atual.depth = 0;
						}
					}
				}
			}
		}
		
	public void checkCollisionPig() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(Entity.isColidding(Game.player, atual)) {
					if(atual instanceof Pig) {
						atual.depth = Game.player.depthPlayer(atual);
					}
				}
			}
		}
	}
		
	public void checkCollisionMine() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Mine) {
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer(atual);
						Game.player.enterRoom = true;
						Game.ui.buttonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_CALABOUÇO;
						Game.player.backRoom = Mapa.MAPA_FLORESTA;

					}else {
						Game.ui.buttonEnterRoom = false;
					}
				}
			}
		}
	}
	
	public void checkCollisionStaircase() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Staircase) {
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(Entity.isColidding(Game.player, atual)) {
						Game.player.depth = atual.depth + 1;
						atual.depth = 0;
						Game.player.enterRoom = true;
						Game.ui.buttonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_FLORESTA;
						Game.player.backRoom = Mapa.MAPA_CALABOUÇO;
						
					}else {
						Game.ui.buttonEnterRoom = false;
					}
				}
			}
		}
	}
	
	public void checkCollisionHouse() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof House) {
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer64x64(atual);
						Game.player.enterRoom = true;
						Game.ui.buttonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_ROOM_HOUSE_01;
						Game.player.backRoom = Mapa.MAPA_FLORESTA;
						
					}
				}
			}
		}
	}
	
	public void checkCollisionDoorHouse() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof DoorHouse) {
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depth - 1;
						Game.player.enterRoom = true;
						Game.ui.buttonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_FLORESTA;
						Game.player.backRoom = Mapa.MAPA_ROOM_HOUSE_01;
						
					}else {
						Game.ui.buttonEnterRoom = false;
					}
				}
			}
		}
	}
	
	public void checkCollisionStatue() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Statue) {
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer64x64(atual);
					}
				}
			}
		}
	}
		
//	public boolean checkCollisionTree(){
//		
//		for(int i = 0; i < Game.entities.size(); i++) {
//			Entity atual = Game.entities.get(i);
//			
//			if(atual instanceof Tree) {
//				if(Entity.isColidding(Game.player, atual)) {
//					if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
//						atual.depth = Game.player.depthPlayer(atual);
//						if(Game.player.useItem && Game.player.hasAxe) {
//							((Tree) atual).life--;
//							return true;
//						}
//					}
//				}
//			}
//		}
//		
//		return false;
//	}
	
	public boolean checkCollisionFishingSpot() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof FishingSpot) {
					if(isColiddingFishingSpots(Game.player, atual)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isColiddingFishingSpots(Entity player, Entity spot) {
		
		Rectangle ePlayer = new Rectangle(player.getX() + player.maskx, player.getY() + player.masky, player.mwidth, player.mheigth);
		Rectangle eSpot = new Rectangle(spot.getX() + spot.maskx, spot.getY() + spot.masky, spot.mwidth, spot.mheigth);
		
		if(ePlayer.intersects(eSpot)) {
			if (player.getX() < spot.getX()) {
				Game.player.dir = Game.player.rightDir;
			}else if (player.getX() > spot.getX()) {
				Game.player.dir = Game.player.leftDir;
			}
		}
		
		return ePlayer.intersects(eSpot);
		
	}
	
	public boolean checkCollisionFishingSpotMask() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof FishingSpot) {
					if(FishingSpot.isColidding(atual, Game.player)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Tree checkCollisionTree() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof Tree) {
					if(Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer(atual);
						return (Tree)atual;
					}
				}
			}
		}
		return null;
	}
	
	public MiningSite checkCollisionMiningSite() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof MiningSite) {
					if(Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer(atual);
						return (MiningSite)atual;
					}
				}
			}
		}
		return null;
	}
	
	public boolean checkCollisionWaterTile() {
		
		for(int i = 0; i < Game.world.tiles.length; i++) {
			Tile atual = Game.world.tiles[i];
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
			if(atual instanceof WaterTile) {
					if(Tile.isColidding(atual, Game.player)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void checkCollisionDoor() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof Door) {
					if(Entity.isColidding(Game.player, atual)) {
						if(Game.player.useItem) {
							Game.player.openDoor =  true;
							Game.player.doorCollision = (Door) atual;
						}
					}
				}
			}
		}
		
	}
	
	public void openDoor() {
		Game.player.openDoor = false;
		double menor = 99999999;
		double dist;
		int xTile = 0;
		int yTile = 0; 
		 
		for(int i=0; i<Game.entities.size(); i++) {
			if(Game.entities.get(i) instanceof Door && Game.entities.get(i) != Game.player.doorCollision ) {
					dist = Entity.calculateDistance((int)Game.player.getX(), (int)Game.player.getY(), (int)Game.entities.get(i).getX(), (int)Game.entities.get(i).getY());
					if(dist < menor) {
					 menor = dist;
						xTile = (int)Game.entities.get(i).getX();
						yTile = (int)Game.entities.get(i).getY();
						 
					}
				}
			}
	 
		Game.player.setX(xTile);
		Game.player.setY(yTile);
		Game.player.updateCamera();
	}
	
	public boolean createGround() {
		
		for(int i = 0; i <Game.world.tiles.length; i++) {
				
			Tile atual = Game.world.tiles[i];
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(World.isColiddingFloorTileToGround(Game.player, atual) 
						&& Game.player.hasHoe 
						&& Game.player.useItem 
						&& atual instanceof FloorTile
						&& (atual.en == null)){
					Ground gd = new Ground(atual.getX(), atual.getY(), 16, 16, Entity.GROUND_EN, atual.psTiles);
					gd.tipo = "terreno";
//					gd.show = true;
					gd.psTiles = atual.psTiles;
					Game.world.tiles[atual.psTiles].en = gd;
					Game.entities.add(gd);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean createGroundOnStump(int x, int y, int ps) {
		
		Ground gd = new Ground(x, y, 16, 16, Entity.GROUND_EN, ps);
		gd.tipo = "terreno";
		gd.psTiles = ps;
		Game.world.tiles[ps].en = gd;
		Game.entities.add(gd);
		return true;
	
	}
	
	public void checkColisionGround() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof Ground) {
					if(Entity.isColidding(Game.player, atual)) {
						
						if(Game.sysInv.inventario[Game.sysInv.handIndexItem] != null) {
							if(Game.player.useItem && Game.sysInv.inventario[Game.sysInv.handIndexItem] instanceof Seed) {
								if(Game.sysInv.inventario[Game.sysInv.handIndexItem].tipo.equals("semente de carvalho")) {
									((Ground) atual).tipo = "terreno de carvalho";
								}else if (Game.sysInv.inventario[Game.sysInv.handIndexItem].tipo.equals("semente de pinheiro")) {
									((Ground) atual).tipo = "terreno de pinheiro";
								}
								
								((Ground) atual).plant = true;
								
								if(!Game.sysInv.inventario[Game.sysInv.handIndexItem].itensPack.isEmpty()) {
									Game.sysInv.inventario[Game.sysInv.handIndexItem].itensPack.remove(0);
									return;
								}else {
									Game.sysInv.inventario[Game.sysInv.handIndexItem] = null;
									Game.sysInv.inv[Game.sysInv.handIndexItem] = null;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public boolean checkColisionGroundToTree(Ground gd) {
		
		if(Entity.isColidding(Game.player, gd)) {
			return true;
		}
		
		return false;

	}
	
	public void checkCollisionStump() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			
			Entity atual = Game.entities.get(i);
			if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if(atual instanceof Stump) {
					
					if(Entity.isColidding(Game.player, atual)) {
		
						atual.depth = Game.player.depthPlayer(atual);
						
						if(Game.player.useItem && Game.player.hasHoe) {
							((Stump) atual).destroySelf();
						}
					}
				}
			}
		}
	}
	
	public boolean isTargetPlayer() {
		for(int i=0; i<Game.mobs.size();i++) {
			Mob atual = Game.mobs.get(i);
			if(atual instanceof Pig) {
				if(atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if(Mob.isColiddingTarget(atual, Game.player)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}

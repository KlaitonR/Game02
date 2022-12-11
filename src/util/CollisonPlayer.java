package util;

import java.awt.Rectangle;
import java.util.ArrayList;

import entities.Bonfire;
import entities.Door;
import entities.DoorHouse;
import entities.Entity;
import entities.Ground;
import entities.Oven;
import entities.Player;
import entities.Staircase;
import entities.Stump;
import entities.NPC.Npc;
import entities.construction.House;
import entities.construction.Mine;
import entities.construction.Statue;
import entities.construction.mobilia.Bed;
import entities.construction.mobilia.BedsideLamp;
import entities.construction.mobilia.Cabinet;
import entities.construction.mobilia.Chair;
import entities.construction.mobilia.Drawer;
import entities.construction.mobilia.FlowerVase;
import entities.construction.mobilia.Table;
import entities.construction.mobilia.Watch;
import entities.itens.Axe;
import entities.itens.Beef;
import entities.itens.Bomb;
import entities.itens.Bullet;
import entities.itens.Charcoal;
import entities.itens.CooperBar;
import entities.itens.Firewood;
import entities.itens.Fish;
import entities.itens.FishingRod;
import entities.itens.GoldBar;
import entities.itens.Gunpowder;
import entities.itens.Hoe;
import entities.itens.LifePack;
import entities.itens.Lighter;
import entities.itens.Ore;
import entities.itens.Pickaxe;
import entities.itens.PotassiumNitrate;
import entities.itens.Potion;
import entities.itens.ProcessedPotassiumNitrate;
import entities.itens.ProcessedSulfor;
import entities.itens.ProcessedWood;
import entities.itens.Root;
import entities.itens.Seed;
import entities.itens.SilverBar;
import entities.itens.Sulfor;
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
	public boolean controllButtonGetItem, controllButtonEnterRoom, controllButtonUseItem;

	// Entidades coletaveis
	public void checkCollision() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof Axe || 
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
						atual instanceof Oven || 
						atual instanceof ProcessedWood || 
						atual instanceof GoldBar || 
						atual instanceof SilverBar || 
						atual instanceof CooperBar ||
						atual instanceof Charcoal ||
						atual instanceof Sulfor ||
						atual instanceof PotassiumNitrate ||
						atual instanceof ProcessedSulfor ||
						atual instanceof ProcessedPotassiumNitrate ||
						atual instanceof Gunpowder ||
						atual instanceof Bonfire ||
						atual instanceof Bomb) {
					confirmCollision(atual, Game.sysInv.checkPositionGetInv());
				}
			}
		}
	}

	public void confirmCollision(Entity atual, int index) {

		if (Entity.isColidding(Game.player, atual)) {

			controllButtonGetItem = true;
			atual.depth = Game.player.depthPlayer(atual);

			if (Game.player.getItem) {
				if (!Game.sysInv.checkPackInv(atual)) {
					if (index >= 0 && index <= Game.sysInv.inventario.length) {
						Game.sysInv.inventario[index] = atual;
						Game.sysInv.inv[index] = atual.getSprite();
						Game.sysInv.handItem = Game.sysInv.inventario[index];
						Game.sysInv.handIndexItem = index;
						Game.entities.remove(atual);
						Sound.Clips.dropAndGetItem.play();
					}
				}
			}
		}
	}

	// Colisões com entidades não coletaveis

	public void checkCollisionAmmo() {

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);

			if (atual instanceof Bullet) {
				if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if (Entity.isColidding(Game.player, atual)) {

						atual.depth = Game.player.depthPlayer(atual);

						Game.player.ammo += 10;
						Game.entities.remove(atual);
						Sound.Clips.missAmo.play();
					} else {
						allCollission.remove(atual);
					}
				}
			}
		}
	}

	// Colisões com objetos que não irão para o inventario (entidades não
	// coletaveis)
	public void checkCollisionNotInterator() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (Entity.isColidding(Game.player, atual)) {
					if (atual instanceof Npc || atual instanceof Bed || atual instanceof Cabinet
							|| atual instanceof FlowerVase || atual instanceof Watch || atual instanceof Table
							|| atual instanceof Chair || atual instanceof Drawer || atual instanceof BedsideLamp) {
						atual.depth = Game.player.depthPlayer(atual);
					}

				}
			}
		}
	}

	public void checkCollisionPig() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (Entity.isColidding(Game.player, atual)) {
					if (atual instanceof Pig) {
						atual.depth = Game.player.depthPlayer(atual);
					}
				}
			}
		}
	}

	public void checkCollisionMine() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Mine) {
				if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if (Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer(atual);
						Game.player.enterRoom = true;
						controllButtonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_CALABOUCO;
						Game.player.backRoom = Mapa.MAPA_FLORESTA;

					} else {
						Game.ui.buttonEnterRoom = false;
					}
				}
			}
		}
	}

	public void checkCollisionStaircase() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Staircase) {
				if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if (Entity.isColidding(Game.player, atual)) {
						Game.player.enterRoom = true;
						controllButtonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_FLORESTA;
						Game.player.backRoom = Mapa.MAPA_CALABOUCO;

					} else {
						Game.ui.buttonEnterRoom = false;
					}
				}
			}
		}
	}

	public void checkCollisionHouse() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof House) {
				if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if (Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer64x64(atual);
						Game.player.enterRoom = true;
						controllButtonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_ROOM_HOUSE_01;
						Game.player.backRoom = Mapa.MAPA_FLORESTA;

					} else {
						Game.ui.buttonEnterRoom = false;
					}
				}
			}
		}
	}

	public void checkCollisionDoorHouse() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof DoorHouse) {
				if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if (Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depth - 1;
						Game.player.enterRoom = true;
						controllButtonEnterRoom = true;
						Game.player.nextRoom = Mapa.MAPA_FLORESTA;
						Game.player.backRoom = Mapa.MAPA_ROOM_HOUSE_01;

					} else {
						Game.ui.buttonEnterRoom = false;
					}
				}
			}
		}
	}

	public void checkCollisionStatue() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Statue) {
				if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if (Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer64x64(atual);
					}
				}
			}
		}
	}

	public boolean checkCollisionFishingSpot() {

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof FishingSpot) {
					if (isColiddingFishingSpots(Game.player, atual)) {
						if(Game.player.hasFishingRod)
							controllButtonUseItem = true;
						return true;
					}else {
						controllButtonUseItem = false;
					}
				}
			}
		}
		return false;
	}

	public static boolean isColiddingFishingSpots(Entity player, Entity spot) {

		Rectangle ePlayer = new Rectangle(player.getX() + player.maskx, player.getY() + player.masky, player.mwidth,
				player.mheigth);
		Rectangle eSpot = new Rectangle(spot.getX() + spot.maskx, spot.getY() + spot.masky, spot.mwidth, spot.mheigth);

		if (ePlayer.intersects(eSpot)) {
			if (player.getX() < spot.getX()) {
				Game.player.dir = Game.player.rightDir;
			} else if (player.getX() > spot.getX()) {
				Game.player.dir = Game.player.leftDir;
			}
		}

		return ePlayer.intersects(eSpot);

	}

	public boolean checkCollisionFishingSpotMask() {

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof FishingSpot) {
					if (FishingSpot.isColidding(atual, Game.player)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Entity checkCollisionTree() {

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof Tree) {
					if (Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer(atual);
						if(Game.player.hasAxe)
							controllButtonUseItem = true;
						return atual;
					}else {
						controllButtonUseItem = false;
					}
				}
			}
		}
		return null;
	}

	public MiningSite checkCollisionMiningSite() {

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof MiningSite) {
					if (Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer(atual);
						if(Game.player.hasPickaxe)
							controllButtonUseItem = true;
						return (MiningSite) atual;
					}else {
						controllButtonUseItem = false;
					}
				}
			}
		}
		return null;
	}

	public boolean checkCollisionWaterTile() {

		for (int i = 0; i < Game.world.tiles.length; i++) {
			Tile atual = Game.world.tiles[i];
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof WaterTile) {
					if (Tile.isColidding(atual, Game.player)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void checkCollisionDoor() {

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);

			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof Door) {
					if (Entity.isColidding(Game.player, atual)) {
						if (Game.player.useItem) {
							Game.player.openDoor = true;
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

		for (int i = 0; i < Game.entities.size(); i++) {
			if (Game.entities.get(i) instanceof Door && Game.entities.get(i) != Game.player.doorCollision) {
				dist = Entity.calculateDistance((int) Game.player.getX(), (int) Game.player.getY(),
						(int) Game.entities.get(i).getX(), (int) Game.entities.get(i).getY());
				if (dist < menor) {
					menor = dist;
					xTile = (int) Game.entities.get(i).getX();
					yTile = (int) Game.entities.get(i).getY();

				}
			}
		}

		Game.player.setX(xTile);
		Game.player.setY(yTile);
		Game.player.updateCamera();
	}

	public boolean createGround() {

		for (int i = 0; i < Game.world.tiles.length; i++) {
			Tile atual = Game.world.tiles[i];
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (World.isColiddingFloorTileToGround(Game.player, atual) && Game.player.hasHoe && Game.player.useItem
						&& atual instanceof FloorTile && (atual.en == null)) {
					System.out.println("Criando terreno");
					Ground gd = new Ground(atual.getX(), atual.getY(), 16, 16, Entity.GROUND_EN, atual.psTiles);
					if(!checkCollisionGround(gd)) {
						gd.tipo = "terreno";
						gd.psTiles = atual.psTiles;
						Game.world.tiles[atual.psTiles].en = gd;
						Game.entities.add(gd);
						return true;
					}else {
						gd = null;
					}
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
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof Ground) {
					if (Entity.isColidding(Game.player, atual)) {
						if (Game.sysInv.inventario[Game.sysInv.handIndexItem] != null
								&& Game.sysInv.inventario[Game.sysInv.handIndexItem] instanceof Seed) {
							controllButtonUseItem = true;
							if (Game.player.useItem && !((Ground) atual).plant) {
								if (Game.sysInv.inventario[Game.sysInv.handIndexItem].tipo
										.equals("semente de carvalho")) {
									((Ground) atual).tipo = "terreno de carvalho";
								} else if (Game.sysInv.inventario[Game.sysInv.handIndexItem].tipo
										.equals("semente de pinheiro")) {
									((Ground) atual).tipo = "terreno de pinheiro";
								} else if (Game.sysInv.inventario[Game.sysInv.handIndexItem].tipo
										.equals("semente de salgueiro")) {
									((Ground) atual).tipo = "terreno de salgueiro";
								}

								((Ground) atual).plant = true;

								if (!Game.sysInv.inventario[Game.sysInv.handIndexItem].itensPack.isEmpty()) {
									Game.sysInv.inventario[Game.sysInv.handIndexItem].itensPack.remove(0);
									return;
								} else {
									Game.sysInv.inventario[Game.sysInv.handIndexItem] = null;
									Game.sysInv.inv[Game.sysInv.handIndexItem] = null;
								}
							}
						}
					} else {
						controllButtonUseItem = false;
					}
				}
			}
		}
	}

	public boolean checkColisionGroundToTree(Ground gd) {
		if (Entity.isColidding(Game.player, gd)) {
			return true;
		}
		return false;
	}

	public void checkCollisionStump() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
				if (atual instanceof Stump) {

					if (Entity.isColidding(Game.player, atual)) {
						atual.depth = Game.player.depthPlayer(atual);

						if (Game.player.hasHoe) {
							controllButtonUseItem = true;
							if(Game.player.useItem)
								((Stump) atual).destroySelf();
						}
					}else {
						controllButtonUseItem = false;
					}
				}
			}
		}
	}

	public boolean isTargetPlayer() {
		for (int i = 0; i < Game.mobs.size(); i++) {
			Mob atual = Game.mobs.get(i);
			if (atual instanceof Pig) {
				if (atual.mapa.contains(Game.mapaGame) && atual.regiao.contains(Game.regiaoGame)) {
					if (Mob.isColiddingTarget(atual, Game.player)) {
						return true;
					}
				}
			}
		}

		return false;
	}
	
	public boolean checkCollisionGround(Ground groud) {
		for(int i=0; i<Game.entities.size(); i++) {
			if(Entity.isColidding(groud, Game.entities.get(i)) 
					&& !(Game.entities.get(i) instanceof Player)
					&& !(Game.entities.get(i) instanceof Ground)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkAbaOvenAndBonfire() {
		
		if(Game.player.openOven) {
			Game.player.openOven = false;
		}else {
			
			for (int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Oven ||
						atual instanceof Bonfire) {
					if (Entity.isColidding(Game.player, atual)) { 
						Game.player.openOven = true;
					}
				}
			}
		}
	
		return false;
	}
	
	public void checkButtonGetItem() {
		
		if (controllButtonGetItem) {
			Game.ui.buttonColletct = true;
		} else {
			Game.ui.buttonColletct = false;
		}

		controllButtonGetItem = false;
	}

	public void checkButtonEnter() {

		if (controllButtonEnterRoom) {
			Game.ui.buttonEnterRoom = true;
		} else {
			Game.ui.buttonEnterRoom = false;
		}

		controllButtonEnterRoom = false;
	}
	
	public void checkButtonUseItem() {

		if (controllButtonUseItem) {
			Game.ui.buttonUseItem = true;
		} else {
			Game.ui.buttonUseItem = false;
		}

		controllButtonUseItem = false;
	}
	
}
